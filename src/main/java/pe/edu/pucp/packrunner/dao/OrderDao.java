package pe.edu.pucp.packrunner.dao;

import java.util.Date;
import java.util.List;

import pe.edu.pucp.packrunner.models.Order;

public interface OrderDao {
  public List<Order> getAll(Date start, Date end, Integer type);

  public Order get(long id);

  public Order register(Order order);

  public Order update(Order order);

  public int delete(long id);

  public List<Order> getByClient(long id);
}
