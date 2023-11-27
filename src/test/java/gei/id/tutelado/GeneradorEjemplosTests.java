package gei.id.tutelado;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Maquina;
import gei.id.tutelado.model.Empleado;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GeneradorEjemplosTests {

    // Crea un conjunto de objectos para utilizar en los casos de prueba

    private EntityManagerFactory emf=null;

    public Cliente c0, c1;
    public List<Cliente> listaC;

    public Maquina m0, m1;
    public List<Maquina> listaM;

    public Empleado e0, e1;
    public List<Empleado> listaE;


    public void Setup (Configuracion config) {
        this.emf=(EntityManagerFactory) config.get("EMF");
    }

    public void crearClientesSueltos() {

        // Crea dos clientes EN MEMORIA: c0, c1
        // Sin maquinas

        this.c0 = new Cliente();
        this.c0.setNif("000A");
        this.c0.setNombrePila("PrimerC");
        this.c0.setApellidos("Cliente Uno");
        this.c0.setTipoCliente("PERSONA FISICA");

        this.c1 = new Cliente();
        this.c1.setNif("111B");
        this.c1.setNombrePila("SegundoC");
        this.c1.setApellidos("Cliente Dos");
        this.c1.setTipoCliente("EMPRESA");

        this.listaC = new ArrayList<Cliente>();
        this.listaC.add(0,c0);
        this.listaC.add(1,c1);

    }

    public void crearMaquinasSueltas() {

        // Crea dos clientes EN MEMORIA: m0, m1
        // Sin propietario asignado (momentaneamente)

        this.m0 = new Maquina();
        this.m0.setCodMaquina("M001");
        this.m0.setModelo("Modelo1");

        this.m1 = new Maquina();
        this.m1.setCodMaquina("M002");
        this.m1.setModelo("Modelo2");

        this.listaM = new ArrayList<Maquina>();
        this.listaM.add(0,m0);
        this.listaM.add(1,m1);

    }

    public void crearEmpleadosSueltos() {

        // Crea dos empleados EN MEMORIA: e0, e1
        // Sin maquinas

        this.e0 = new Empleado();
        this.e0.setNif("222C");
        this.e0.setNombrePila("PrimerE");
        this.e0.setApellidos("Empleado Uno");
        this.e0.setFechaContratacion(LocalDateTime.of(2000,1,1,0,0));
        this.e0.setSalario(1800.0);

        this.e1 = new Empleado();
        this.e1.setNif("333D");
        this.e1.setNombrePila("SegundoE");
        this.e1.setApellidos("Empleado Dos");
        this.e1.setFechaContratacion(LocalDateTime.of(2001,1,1,0,0));
        this.e1.setSalario(1200.0);


        this.listaE = new ArrayList<Empleado>();
        this.listaE.add(0,e0);
        this.listaE.add(1,e1);

    }

    public void crearClientesConMaquinas () {

        this.crearClientesSueltos();
        this.crearMaquinasSueltas();

        this.c0.agregarMaquina(this.m0);
        this.c0.agregarMaquina(this.m1);

    }

    public void asignarMaquinasAEmpleados () {

        this.crearClientesConMaquinas();
        this.crearEmpleadosSueltos();

        this.e0.asignarMaquina(this.m0);
        this.e1.asignarMaquina(this.m0);
        this.e1.asignarMaquina(this.m1);

    }

    public void grabarClientes() {
        EntityManager em=null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Cliente> itC = this.listaC.iterator();
            while (itC.hasNext()) {
                Cliente c = itC.next();
                em.persist(c);
                // DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itEL.hasNext()) {
					em.persist(itEL.next());
				}
				*/
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }

    public void grabarEmpleados() {
        EntityManager em=null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator<Empleado> itE = this.listaE.iterator();
            while (itE.hasNext()) {
                Empleado e = itE.next();
                em.persist(e);
                // DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itEL.hasNext()) {
					em.persist(itEL.next());
				}
				*/
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception ex) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
    }

    public void limpiarBD () {
        EntityManager em=null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            Iterator <Cliente> itC = em.createNamedQuery("Cliente.recuperaTodos", Cliente.class).getResultList().iterator();
            while (itC.hasNext()) em.remove(itC.next());
            Iterator <Maquina> itM = em.createNamedQuery("Maquina.recuperaTodas", Maquina.class).getResultList().iterator();
            while (itM.hasNext()) em.remove(itM.next());
            Iterator <Empleado> itE = em.createNamedQuery("Cliente.recuperaTodos", Empleado.class).getResultList().iterator();
            while (itE.hasNext()) em.remove(itE.next());

            em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idCliente'" ).executeUpdate();
            em.createNativeQuery("UPDATE tabla_ids SET ultimo_valor_id=0 WHERE nombre_id='idMaquina'" ).executeUpdate();

            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (e);
            }
        }
    }


}
