package gei.id.tutelado.dao.cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.dao.cliente.ClienteDao;
import gei.id.tutelado.dao.persona.PersonaDaoJPA;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Empleado;

import java.util.List;


public class ClienteDaoJPA extends PersonaDaoJPA implements ClienteDao{

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup(Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
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
}