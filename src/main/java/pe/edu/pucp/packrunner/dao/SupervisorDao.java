package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Supervisor;

import java.util.List;

public interface SupervisorDao {
    // Listar todos
    public List<Supervisor> getAll();

    // Listar uno
    public Supervisor get(long id);

    // Registrar
    public Supervisor register(Supervisor obj);

    // Actualizar
    public Supervisor update(Supervisor obj);

    // Eliminar
    public int delete(long id);
}
