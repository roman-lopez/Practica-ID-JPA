package gei.id.tutelado.dao;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Cliente;
import gei.id.tutelado.model.Maquina;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class MaquinaDaoJPA implements MaquinaDao{

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Maquina almacena(Maquina maquina) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(maquina);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return maquina;
    }

    @Override
    public Maquina modifica(Maquina maquina) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            maquina = em.merge (maquina);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return maquina;
    }

    @Override
    public void elimina(Maquina maquina) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Maquina maquinaTemp = em.find (Maquina.class, maquina.getIdMaquina());
            em.remove (maquinaTemp);

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
    public Maquina recuperaPorCodigo(String codMaquina) {

        List<Maquina> maquinas=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            maquinas = em.createNamedQuery("Maquina.recuperaPorCodigo", Maquina.class)
                    .setParameter("codMaquina", codMaquina).getResultList();

            em.getTransaction().commit();
            em.close();
        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (maquinas.size()==0?null:maquinas.get(0));
    }

    @Override
    public List<Maquina> recuperaTodasPropietario(Cliente c) {
        List <Maquina> maquinas=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            maquinas = em.createNamedQuery("Maquina.recuperaTodasPropietario", Maquina.class).setParameter("c", c).getResultList();

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

        return maquinas;
    }
}
