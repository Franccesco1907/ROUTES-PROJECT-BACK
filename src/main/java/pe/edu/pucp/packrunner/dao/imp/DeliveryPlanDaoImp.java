package pe.edu.pucp.packrunner.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.DeliveryDao;
import pe.edu.pucp.packrunner.dao.DeliveryPlanDao;
import pe.edu.pucp.packrunner.models.DeliveryPlan;

import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class DeliveryPlanDaoImp implements DeliveryPlanDao {
    @PersistenceContext
    EntityManager entityManager;

    // Get All
    @Transactional
    @Override
    public List<DeliveryPlan> getAll() {
        List<DeliveryPlan> result = null;
        try {
            String hql = "FROM DeliveryPlan";
            result = (List<DeliveryPlan>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Get One
    @Transactional
    @Override
    public DeliveryPlan get(long id) {
        DeliveryPlan result = null;
        try {
            result = entityManager.find(DeliveryPlan.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Register
    @Transactional
    @Override
    public DeliveryPlan register(DeliveryPlan obj) {
        DeliveryPlan result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Update
    @Transactional
    @Override
    public DeliveryPlan update(DeliveryPlan obj) {
        DeliveryPlan result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Delete
    @Transactional
    @Override
    public int delete(long id) {
        int result = 0;
        try {
            DeliveryPlan obj = get(id);
            entityManager.remove(obj);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

}
