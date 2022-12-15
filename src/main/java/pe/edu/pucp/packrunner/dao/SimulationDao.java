package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Simulation;

import java.util.List;

public interface SimulationDao {

    // Get All
    public List<Simulation> getAll();

    // Get one
    public Simulation get(long id);

    // Register
    public Simulation register(Simulation simulation);

    // Update
    public Simulation update(Simulation simulation);

    // Delete
    public int delete(long id);

    public Simulation getLastSimulation();

}
