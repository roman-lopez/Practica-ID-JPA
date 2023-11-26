package gei.id.tutelado.dao;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.Persona;
import gei.id.tutelado.model.Usuario;
import org.hibernate.LazyInitializationException;

import gei.id.tutelado.configuracion.Configuracion;


public class EmpleadoDaoJPA extends PersonaDaoJPA implements EmpleadoDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Empleado restauraMaquinas(Empleado empleado) {
        // Devolve o obxecto user coa coleccion de entradas cargada (se non o estaba xa)

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                empleado.getMaquinas().size();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    /* Volve a ligar o obxecto usuario a un novo CP,
                     * e accede á propiedade nese momento, para que Hibernate a cargue.*/
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
    public Integer numeroCobraMasDe1500() {
        List<Empleado> empleados = null;

        // Tengo que hacer una List de empleados en vez de Set porque el getResultList() devuelve una lista
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            empleados = em.createNamedQuery("Empleado.numeroCobraMasDe1500", Empleado.class).getResultList();

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex) {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw (ex);
            }

            return (empleados.size() != 0 ? empleados.get(0) : null);
        }
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
}
