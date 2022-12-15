package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.dao.EdgeDao;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.TruckPlan;
import pe.edu.pucp.packrunner.models.Vertex;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class EdgeDaoImp implements EdgeDao {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    BlockDao blockDao;

    // Listar todos
    @Transactional
    @Override
    public List<Edge> getAll(Long idVertex) {
        List<Edge> result = new ArrayList<>();
        try {
            String hql = "SELECT DISTINCT e FROM Edge e JOIN e.vertexes v ";
            if (idVertex != null) {
                hql += " WHERE e.id in " +
                        " (SELECT DISTINCT e.id FROM Edge e JOIN e.vertexes v" +
                        " WHERE v.id = :id_vertex)";
            }
            hql += " ORDER BY e.id asc";
            Query query = entityManager.createQuery(hql);
            if(idVertex != null) query.setParameter("id_vertex", idVertex);
            result = query.getResultList();

        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Get one
    @Transactional
    @Override
    public Edge get(long id) {
        Edge result = null;
        try {
            result = entityManager.find(Edge.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Registrar
    @Transactional
    @Override
    public Edge register(Edge edge) {
        Edge resultado = null;
        try {
            resultado = entityManager.merge(edge);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public Edge update(Edge edge) {
        Edge resultado = null;
        try {
            resultado = entityManager.merge(edge);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Delete
    @Transactional
    @Override
    public int delete(long id) {
        int resultado = 0;
        try {
            Edge edge = get(id);
            entityManager.remove(edge);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Edge block(long id) {
        Edge edge = get(id);
        edge.setBlocked(!edge.isBlocked());
        entityManager.merge(edge);
        return edge;
    }


}
