package gei.id.tutelado.dao.empleado;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.dao.empleado.EmpleadoDao;
import gei.id.tutelado.dao.persona.PersonaDaoJPA;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.EmpleadoDTO;
import org.hibernate.LazyInitializationException;

import gei.id.tutelado.configuracion.Configuracion;


public class EmpleadoDaoJPA extends PersonaDaoJPA implements EmpleadoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
        super.setup(config);
    }

    @Override
    public Empleado restauraMaquinas(Empleado empleado) {
        // Devuelve el objeto empleado con la coleccion de maquinas cargada (si no lo estaba ya)

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                empleado.getMaquinas().size();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    /* Vuelve a ligar el objeto empleado a un nuevo CP,
                     * y accede a la propiedad en ese momento, para que Hibernate la cargue.*/
                    empleado = em.merge(empleado);
                    empleado.getMaquinas().size();

                } else {
                    throw ex2;
                }
            }
            em.getTransaction().commit();
            em.close();
        }
        catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }

        return (empleado);

    }

    @Override
    public Long numeroCobraMasDe1500() {
       Long resultado = null;

        // Tengo que hacer una List de empleados en vez de Set porque el getResultList() devuelve una lista
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            resultado = em.createNamedQuery("Empleado.numeroCobraMasDe1500", Long.class).getSingleResult();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }
        }
        return resultado;
    }

    public List<EmpleadoDTO> recuperaMaquinasAsignadas() {
        List<EmpleadoDTO> resultado = null;

        // Tengo que hacer una List de Objects en vez de Set porque el getResultList() devuelve una lista
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            resultado = em.createNamedQuery("Empleado.recuperaMaquinasAsignadas", EmpleadoDTO.class).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }

        }
        return (resultado.size() != 0 ? resultado : null);
    }



    @Override
    public Empleado recuperaPorNif(String nif) {
        List<Empleado> empleados=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            empleados = em.createNamedQuery("Empleado.recuperaPorNif", Empleado.class).setParameter("nif", nif).getResultList();

            em.getTransaction().commit();
            em.close();

        }
        catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }

        return (empleados.size()!=0?empleados.get(0):null);
    }

    @Override
    public List recuperaTodos() {
        List <Empleado> empleados=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            empleados = em.createNamedQuery("Empleado.recuperaTodos", Empleado.class).getResultList();

            em.getTransaction().commit();
            em.close();

        }
        catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }

        return empleados;
    }
}

