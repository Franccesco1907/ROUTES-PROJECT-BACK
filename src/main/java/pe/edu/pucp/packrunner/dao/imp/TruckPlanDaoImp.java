package pe.edu.pucp.packrunner.dao.imp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.SimulationDao;
import pe.edu.pucp.packrunner.dao.TruckPlanDao;
import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.Simulation;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class TruckPlanDaoImp implements TruckPlanDao {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SimulationDao simulationDao;

    // List All
    @Transactional
    @Override
    public List<TruckPlan> getAll(Long idSimulation, Integer run, String status, Boolean travelling) {
        List<TruckPlan> result = new ArrayList<>();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TruckPlan> criteriaQuery = criteriaBuilder.createQuery(TruckPlan.class);
            Root<TruckPlan> root = criteriaQuery.from(TruckPlan.class);
            List<Predicate> predicates = new ArrayList<>();

            if (idSimulation != null) {
                Simulation simulation = entityManager.find(Simulation.class, idSimulation);
                predicates.add(criteriaBuilder
                        .equal(root.<Simulation>get("simulation"), simulation));
            }
            if (run != null) {
                predicates.add(criteriaBuilder
                        .equal(root.<String>get("run"), run));
            }
            if (status != null) {
                predicates.add(criteriaBuilder
                        .equal(root.<String>get("status"), status));
            }
            if (travelling != null) {
                predicates.add(criteriaBuilder
                        .equal(root.<Boolean>get("travelling"), travelling));
            }
            criteriaQuery.where(predicates.toArray(new Predicate[] {}));
            TypedQuery<TruckPlan> tq = entityManager.createQuery(criteriaQuery);
            result = tq.getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }

        return result;
    }

    // Listar uno
    /*
     * @Transactional
     * 
     * @Override
     * public TruckPlan get(long id) {
     * TruckPlan result = null;
     * try {
     * result = entityManager.find(TruckPlan.class, id);
     * } catch (Exception ex) {
     * System.out.print(ex.getMessage());
     * }
     * 
     * return result;
     * }
     */

    @Transactional
    @Override
    public TruckPlan get(long id) {
        TruckPlan result;
        try {
            result = entityManager.find(TruckPlan.class, id);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            result = null;
        }

        return result;
    }

    // Registrar
    @Transactional
    @Override
    public TruckPlan register(TruckPlan obj) {
        TruckPlan result = null;
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
    public TruckPlan update(TruckPlan obj) {
        TruckPlan result = null;
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
            // TruckPlan obj = get(id);
            entityManager.remove(entityManager.find(TruckPlan.class, id));
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public TruckPlan getFirstBySimulation(long id) {
        List<TruckPlan> result = null;
        try {
            String hql = "FROM TruckPlan where simulation.id = :id";
            result = entityManager.createQuery(hql).setParameter("id", id).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        if (result != null)
            return result.get(result.size() - 1);
        else
            return null;
    }

    @Transactional
    @Override
    public List<TruckPlan> listBySimulation(long id) {
        List<TruckPlan> result = null;
        try {
            String hql = "FROM TruckPlan where simulation.id = :id";
            result = entityManager.createQuery(hql).setParameter("id", id).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        if (result != null)
            return result;
        else
            return null;
    }

    @Transactional
    @Override
    public TruckPlan getComplete(long id) {
        TruckPlan result = null;
        try {
            result = entityManager.find(TruckPlan.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }

        return result;
    }

    @Transactional
    @Override
    public List<TruckPlan> getBySimulation(Long id) {
        List<TruckPlan> result = null;
        try {
            String hql = "FROM TruckPlan where simulation.id = :id";
            result = entityManager.createQuery(hql).setParameter("id", id).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }
}
