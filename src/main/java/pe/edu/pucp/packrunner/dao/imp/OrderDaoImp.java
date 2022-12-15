package pe.edu.pucp.packrunner.dao.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.OrderDao;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.TruckPlan;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class OrderDaoImp implements OrderDao {

  @PersistenceContext
  EntityManager entityManager;

  @Transactional
  @Override
  public List<Order> getAll(Date start, Date end, Integer type) {
    List<Order> result = null;

    try {
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
      Root<Order> root = criteriaQuery.from(Order.class);

      List<Predicate> predicates = new ArrayList<>();

      if(start != null) {
        predicates.add(criteriaBuilder
                .greaterThanOrEqualTo(root.<Date>get("orderDate"), start));
      }
      if(end != null) {
        predicates.add(criteriaBuilder
                .lessThanOrEqualTo(root.<Date>get("orderDate"), end));
      }
      if(type != null) {
        predicates.add(criteriaBuilder
                .equal(root.<Date>get("type"), type));
      }
      criteriaQuery.where(predicates.toArray(new Predicate[]{}));
      criteriaQuery.select(root);
      TypedQuery<Order> tq = entityManager.createQuery(criteriaQuery);
      result = tq.getResultList();
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return result;
  }

  @Transactional
  @Override
  public Order get(long id) {
    Order resultado = null;
    try {
      resultado = entityManager.find(Order.class, id);
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public Order register(Order order) {
    Order resultado = null;
    try {
      resultado = entityManager.merge(order);
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public Order update(Order order) {
    Order resultado = null;
    try {
      resultado = entityManager.merge(order);
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public int delete(long id) {
    int resultado = 0;
    try {
      Order order = get(id);
      entityManager.remove(order);
      resultado = 1;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return resultado;
  }

  @Override
  public List<Order> getByClient(long id) {
    List<Order> resultado = null;
    try {
      String hql = "FROM Order WHERE client.id = :id";
      resultado = entityManager.createQuery(hql).setParameter("id", id).getResultList();
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

}
