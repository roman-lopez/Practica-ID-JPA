package gei.id.tutelado.dao.persona;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PersonaDaoJPA implements PersonaDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Persona almacena(Persona persona) {

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(persona);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return persona;
    }

    @Override
    public Persona modifica(Persona persona) {

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            persona= em.merge (persona);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (persona);
    }

    @Override
    public void elimina(Persona persona) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Persona personaTemp = em.find (Persona.class, persona.getIdPersona());
            em.remove (personaTemp);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
    }


    @Override
    public Persona recuperaPorNif(String nif) {
        List<Persona> personas=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            personas = em.createNamedQuery("Persona.recuperaPorNif", Persona.class).setParameter("nif", nif).getResultList();

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

        return (personas.size()!=0?personas.get(0):null);
    }


    @Override
    public List<Persona> recuperaTodos() {
        List <Persona> personas=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            personas = em.createNamedQuery("Persona.recuperaTodos", Persona.class).getResultList();

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

        return personas;
    }

}
