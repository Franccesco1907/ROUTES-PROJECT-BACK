package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.User;
import pe.edu.pucp.packrunner.models.Vertex;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class VertexDaoImp implements VertexDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Vertex> getLocation() {
        List<Vertex> resultado = null;
        try {
            String hql = "SELECT id, province.id, province.name,  latitude, longitude, x, y FROM Vertex";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public List<Vertex> getAll() {
        List<Vertex> resultado = null;
        try {
            String hql = "FROM Vertex";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    @Override
    public List<Vertex> getAllOffices() {
        List<Vertex> resultado = null;
        try {
            String hql = "FROM Vertex WHERE type = 1";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    @Override
    public List<Vertex> getAllDepots() {
        List<Vertex> resultado = null;
        try {
            String hql = "FROM Vertex WHERE type = 0";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Vertex get(long id) {
        Vertex result = null;
        try {
            result = entityManager.find(Vertex.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Registrar
    @Transactional
    @Override
    public Vertex register(Vertex vertex) {
        Vertex resultado = null;
        try {
            resultado = entityManager.merge(vertex);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public Vertex update(Vertex vertex) {
        Vertex resultado = null;
        try {
            resultado = entityManager.merge(vertex);
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
            Vertex vertex = get(id);
            entityManager.remove(vertex);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Vertex getByProvince(long id) {
        Vertex resultado = null;
        try {
            String hql = "FROM Vertex WHERE type = 1 AND province.id = :id";
            resultado = (Vertex) entityManager.createQuery(hql).setParameter("id", id).getSingleResult();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }
}
