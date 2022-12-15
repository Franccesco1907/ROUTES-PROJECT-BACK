package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.List;

public interface TruckPlanDao {
    // Get All
    public List<TruckPlan> getAll(Long idSimulation, Integer run, String status, Boolean travelling);

    // Get One
    public TruckPlan get(long id);

    // Register
    public TruckPlan register(TruckPlan obj);

    // Update
    public TruckPlan update(TruckPlan obj);

    // Delete
    public int delete(long id);

    // Get a Truck plan by simulation
    public TruckPlan getFirstBySimulation(long id);

    // Get All truck plans from a simulation
    public List<TruckPlan> listBySimulation(long id);

    public TruckPlan getComplete(long id);

    public List<TruckPlan> getBySimulation(Long id);

}
