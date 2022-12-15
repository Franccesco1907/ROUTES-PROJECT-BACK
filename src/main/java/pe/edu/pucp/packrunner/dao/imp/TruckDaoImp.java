package pe.edu.pucp.packrunner.dao.imp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import pe.edu.pucp.packrunner.dao.TruckDao;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.models.Vertex;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.readTrucks;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class TruckDaoImp implements TruckDao {
  @PersistenceContext
  EntityManager entityManager;

  @Transactional
  @Override
  public List<Truck> getAll() {
    List<Truck> resultado = null;
    try {
      String hql = "FROM Truck";
      resultado = entityManager.createQuery(hql).getResultList();
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public Truck get(long id) {
    Truck resultado = null;
    try {
      resultado = entityManager.find(Truck.class, id);
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public Truck register(Truck truck) {
    Truck resultado = null;
    try {
      resultado = entityManager.merge(truck);
    } catch (Exception ex) {
      System.out.print(ex.getMessage());
    }
    return resultado;
  }

  @Transactional
  @Override
  public Truck update(Truck truck) {
    Truck resultado = null;
    try {
      resultado = entityManager.merge(truck);
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
      Truck truck = get(id);
      entityManager.remove(truck);
      resultado = 1;
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
    return resultado;
  }
}
