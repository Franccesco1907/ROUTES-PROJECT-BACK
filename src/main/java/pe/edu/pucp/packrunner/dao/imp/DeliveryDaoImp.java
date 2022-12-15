package pe.edu.pucp.packrunner.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.DeliveryDao;
import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class DeliveryDaoImp implements DeliveryDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Delivery> getAll() {
        List<Delivery> result = null;
        try {
            String hql = "FROM Delivery";
            result = (List<Delivery>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Delivery> getByOrder(long id) {
        List<Delivery> result = null;
        try {
            String hql = "FROM Delivery WHERE order.id = :id";
            result = (List<Delivery>) entityManager.createQuery(hql).setParameter("id", id).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Listar uno
    @Transactional
    @Override
    public Delivery get(long id) {
        Delivery result = null;
        try {
            result = entityManager.find(Delivery.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Registrar
    @Transactional
    @Override
    public Delivery register(Delivery obj) {
        Delivery result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Actualizar
    @Transactional
    @Override
    public Delivery update(Delivery obj) {
        Delivery result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Eliminar
    @Transactional
    @Override
    public int delete(long id) {
        int result = 0;
        try {
            Delivery obj = get(id);
            entityManager.remove(obj);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<TravelOut> getPlans(long id) {
        List<TravelOut> result = new ArrayList<>();
        List<TruckPlan> tp = null;
        Delivery d = null;
        try {
            d = get(id);
            String hql = "SELECT DISTINCT tp FROM TruckPlan tp INNER JOIN tp.deliveries d where d.id = :id";
            tp = (List<TruckPlan>) entityManager.createQuery(hql).setParameter("id", id).getResultList();



            for (Travel travel : tp.get(0).getTravels()) {
                result.add(new TravelOut(travel));
            }


        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

}
