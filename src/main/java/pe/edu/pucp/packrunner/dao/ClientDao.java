package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.Client;

import java.util.List;

public interface ClientDao {
    // Listar todos
    public List<Client> getAll();

    // Listar uno
    public Client get(long id);

    // Registrar
    public Client register(Client obj);

    // Actualizar
    public Client update(Client obj);

    // Eliminar
    public int delete(long id);

    public Client getByUser(long id);
}
