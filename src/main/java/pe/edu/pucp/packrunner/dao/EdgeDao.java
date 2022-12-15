package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Edge;

import java.util.Date;
import java.util.List;

public interface EdgeDao {
    // Get All
    public List<Edge> getAll(Long idVertex);

    // Get one
    public Edge get(long id);

    // Register
    public Edge register(Edge edge);

    // Update
    public Edge update(Edge edge);

    // Delete
    public int delete(long id);

    // Block Edges
    public Edge block(long id);
}
