package gei.id.tutelado.dao.cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.dao.persona.PersonaDaoJPA;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.Persona;
import org.hibernate.LazyInitializationException;

import java.util.List;


public class ClienteDaoJPA extends PersonaDaoJPA implements ClienteDao{

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
        super.setup(config);
    }

    @Override
    public Cliente recuperaPorNif(String nif) {
        List<Cliente> clientes=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            clientes = em.createNamedQuery("Cliente.recuperaPorNif", Cliente.class).setParameter("nif", nif).getResultList();

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

        return (clientes.size()!=0?clientes.get(0):null);
    }

    @Override
    public List recuperaTodos() {
        List <Cliente> clientes=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            clientes = em.createNamedQuery("Cliente.recuperaTodos", Cliente.class).getResultList();

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

        return clientes;
    }

    // OPERACIONES POR ATRIBUTOS LAZY
    @Override
    public Cliente restauraMaquinas(Cliente cliente){
        // Devuelve el objeto cliente con la coleccion de maquinas cargada (si no lo estaba ya)

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            try {
                cliente.getMaquinas().size();
            } catch (Exception ex2) {
                if (ex2 instanceof LazyInitializationException)

                {

                    /* Vuelve a ligar el objeto cliente a un nuevo CP,
                     * y accede a la propiedad en ese momento, para que Hibernate la cargue.*/
                    cliente = em.merge(cliente);
                    cliente.getMaquinas().size();

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

        return (cliente);
    }
}