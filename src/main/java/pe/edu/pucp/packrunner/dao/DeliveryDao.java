package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.dto.out.TravelOut;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.List;

public interface DeliveryDao {
    // Get All
    public List<Delivery> getAll();

    // Get One
    public Delivery get(long id);

    // Get By Order
    public List<Delivery> getByOrder(long id);

    // Register
    public Delivery register(Delivery obj);

    // Update
    public Delivery update(Delivery obj);

    // Delete
    public int delete(long id);

    // Get Plans
    public List<TravelOut> getPlans(long id);

}
