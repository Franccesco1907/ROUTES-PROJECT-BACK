package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.DeliveryPlan;

import java.util.List;

public interface DeliveryPlanDao {

    // Get All
    public List<DeliveryPlan> getAll();

    // Get One
    public DeliveryPlan get(long id);

    // Register
    public DeliveryPlan register(DeliveryPlan obj);

    // Update
    public DeliveryPlan update(DeliveryPlan obj);

    // Delete
    public int delete(long id);



}
