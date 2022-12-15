package pe.edu.pucp.packrunner.dao;

import java.util.List;

import pe.edu.pucp.packrunner.models.Truck;

public interface TruckDao {
  public List<Truck> getAll();

  public Truck get(long id);

  public Truck register(Truck truck);

  public Truck update(Truck truck);

  public int delete(long id);
}
