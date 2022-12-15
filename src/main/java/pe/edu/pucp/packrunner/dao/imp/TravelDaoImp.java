package pe.edu.pucp.packrunner.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.TravelDao;
import pe.edu.pucp.packrunner.models.Travel;

import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class TravelDaoImp implements TravelDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Travel> getAll() {
        List<Travel> resultado = null;
        try {
            String hql = "FROM Travel";
            resultado = (List<Travel>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Travel get(long id) {
        Travel resultado = null;
        try {
            resultado = entityManager.find(Travel.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public Travel register(Travel obj) {
        Travel resultado = null;
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
    public Travel update(Travel obj) {
        Travel resultado = null;
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
            Travel obj = get(id);
            entityManager.remove(obj);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

}
