package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Edge;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class BlockDaoImp implements BlockDao {

    @PersistenceContext
    EntityManager entityManager;

    // Get All
    @Transactional
    @Override
    public List<Block> getAll(Date start, Date end) {
        List<Block> result = new ArrayList<>();
        try {
            String hql =
                    "SELECT DISTINCT b FROM Block b INNER JOIN FETCH b.edge e JOIN FETCH e.vertexes ";
            if (start != null) hql += "WHERE starting_date < :start ";
            if (end != null) hql += start==null?"WHERE":"AND" + " end_date > :end ";
            hql += "ORDER BY b.id ASC";

            result = (List<Block>) entityManager.createQuery(hql)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Get one
    @Transactional
    @Override
    public Block get(long id) {
        Block result = null;
        try {
            result = entityManager.find(Block.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    // Register
    @Transactional
    @Override
    public Block register(Block obj) {
        Block result = null;
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
    public Block update(Block obj) {
        Block result = null;
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
            Block obj = get(id);
            entityManager.remove(obj);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    // Get all Edges that are blocked in a Date
    @Override
    public List<Edge> getBlockedEdges(Date date) {
        List<Edge> result = new ArrayList<>();
        try {
            String hql =
                    "SELECT edge FROM Block b WHERE starting_date < :date AND end_date > :date";
            result = (List<Edge>) entityManager.createQuery(hql)
                    .setParameter("date", date)
                    .getResultList();
            hql = "SELECT e from Edge e WHERE blocked = true";
            result.addAll((List<Edge>) entityManager.createQuery(hql)
                    .getResultList());
            System.out.println(result.size() + " edges are blocked");
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

}
