package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Vertex;

import java.util.List;

public interface VertexDao {

    public List<Vertex> getLocation();

    // Listar todos
    public List<Vertex> getAll();

    public List<Vertex> getAllOffices();

    public List<Vertex> getAllDepots();

    // Listar uno
    public Vertex get(long id);

    // Registrar
    public Vertex register(Vertex vertex);

    // Actualizar
    public Vertex update(Vertex vertex);

    // Eliminar
    public int delete(long id);

    public Vertex getByProvince(long id);
}
