package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.ClientDao;
import pe.edu.pucp.packrunner.models.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ClientDaoImp implements ClientDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Client> getAll() {
        List<Client> resultado = null;
        try {
            String hql = "FROM Client";
            resultado = (List<Client>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Client get(long id) {
        Client resultado = null;
        try {
            resultado = entityManager.find(Client.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public Client register(Client obj) {
        Client resultado = null;
        try {
            resultado = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public Client update(Client obj) {
        Client resultado = null;
        try {
            resultado = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Eliminar
    @Transactional
    @Override
    public int delete(long id) {
        int resultado = 0;
        try {
            Client obj = get(id);
            entityManager.remove(obj);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Client getByUser(long id) {
        Client resultado = null;
        try {
            String hql = "FROM Client WHERE user.id = :id";
            resultado = (Client) entityManager.createQuery(hql).setParameter("id", id).getSingleResult();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }
}
