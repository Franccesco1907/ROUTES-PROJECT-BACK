package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.User;
import java.util.List;

public interface UserDao {
    // Listar todos
    public List<User> getAll();

    // Listar uno
    public User get(long id);

    // Registrar
    public User register(User user);

    // Actualizar
    public User update(User user);

    // Eliminar
    public int delete(long id);

    public User login(User obj);
}
