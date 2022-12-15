package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Client;
import pe.edu.pucp.packrunner.models.Maintenance;
import pe.edu.pucp.packrunner.models.Truck;

import java.util.Date;
import java.util.List;

public interface MaintenanceDao {

    // Get All
    public List<Maintenance> getAll();

    // Get One
    public Maintenance get(long id);

    // Register
    public Maintenance register(Maintenance obj);

    // Update
    public Maintenance update(Maintenance obj);

    // Delete
    public int delete(long id);

    // Get Truck that needs Maintenance
    public List<Truck> getTrucks(Date date);

}
