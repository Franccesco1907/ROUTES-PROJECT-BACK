package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.*;
import pe.edu.pucp.packrunner.models.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class SimulationDaoImp implements SimulationDao {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<Simulation> getAll() {
        List<Simulation> result = null;
        try {
            String hql = "FROM Simulation";
            result = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Simulation get(long id) {
        Simulation result = null;
        try {
            result = entityManager.find(Simulation.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Simulation register(Simulation simulation) {
        Simulation result = null;
        try {
            result = entityManager.merge(simulation);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Simulation update(Simulation simulation) {
        Simulation result = null;
        try {
            result = entityManager.merge(simulation);
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
            Simulation simulation = get(id);
            entityManager.remove(simulation);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public Simulation getLastSimulation() {
        List<Simulation> result = null;
        try {
            String hql = "FROM Simulation WHERE type = 0";
            result = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        if (result != null)
            return result.get(result.size() - 1);
        else
            return null;
    }
}
