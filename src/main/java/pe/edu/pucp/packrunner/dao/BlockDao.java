package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Maintenance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;


public interface BlockDao {

    // Get All
    public List<Block> getAll(Date start, Date end);

    // Get One
    public Block get(long id);

    // Register
    public Block register(Block obj);

    // Update
    public Block update(Block obj);

    // Delete
    public int delete(long id);

    // Get all blocked edges
    public List<Edge> getBlockedEdges(Date date);

}
