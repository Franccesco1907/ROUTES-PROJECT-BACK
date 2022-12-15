package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.MaintenanceDao;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Maintenance;
import pe.edu.pucp.packrunner.models.Truck;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class MaintenanceDaoImp implements MaintenanceDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<Maintenance> getAll() {
        List<Maintenance> result = null;
        try {
            String hql = "FROM Client";
            result = (List<Maintenance>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Maintenance get(long id) {
        Maintenance result = null;
        try {
            result = entityManager.find(Maintenance.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Maintenance register(Maintenance obj) {
        Maintenance result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Maintenance update(Maintenance obj) {
        Maintenance result = null;
        try {
            result = entityManager.merge(obj);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int delete(long id) {
        int result = 0;
        try {
            Maintenance obj = get(id);
            entityManager.remove(obj);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public List<Truck> getTrucks(Date date) {
        List<Truck> result = null;
        try {
            String hql = "SELECT DISTINCT truck FROM Maintenance m where DATE(:date) = DATE(date)";
            result = (List<Truck>) entityManager.createQuery(hql)
                    .setParameter("date", date)
                    .getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }
}
