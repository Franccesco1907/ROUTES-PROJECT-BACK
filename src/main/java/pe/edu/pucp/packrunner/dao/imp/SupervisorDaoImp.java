package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.SupervisorDao;
import pe.edu.pucp.packrunner.models.Supervisor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class SupervisorDaoImp implements SupervisorDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Supervisor> getAll() {
        List<Supervisor> resultado = null;
        try {
            String hql = "FROM Supervisor";
            resultado = (List<Supervisor>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Supervisor get(long id) {
        Supervisor resultado = null;
        try {
            resultado = entityManager.find(Supervisor.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public Supervisor register(Supervisor obj) {
        Supervisor resultado = null;
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
    public Supervisor update(Supervisor obj) {
        Supervisor resultado = null;
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
            Supervisor obj = get(id);
            entityManager.remove(obj);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }
}
