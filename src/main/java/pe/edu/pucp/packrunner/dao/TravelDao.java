package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Travel;

import java.util.List;

public interface TravelDao {
    // Listar todos
    public List<Travel> getAll();

    // Listar uno
    public Travel get(long id);

    // Registrar
    public Travel register(Travel obj);

    // Actualizar
    public Travel update(Travel obj);

    // Eliminar
    public int delete(long id);

}
