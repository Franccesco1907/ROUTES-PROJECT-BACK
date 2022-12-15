package pe.edu.pucp.packrunner.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.UserDao;
import pe.edu.pucp.packrunner.models.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao objDao;

    // Trae a todos
    public List<User> getAll() {
        return objDao.getAll();
    }

    // Trae uno
    public User get(long id) {
        return objDao.get(id);
    }

    // Registrar
    public void register(User obj) {
        String hash = generateHash(obj.getPassword());
        obj.setPassword(hash);
        objDao.register(obj);
    }

    // Actualizar
    public User update(User obj) {
        return objDao.update(obj);
    }

    // Eliminar
    public int delete(long id) {
        return objDao.delete(id);
    }

    private String generateHash(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return argon2.hash(1, 1024 * 1, 1, password.getBytes(StandardCharsets.UTF_8));
    }

    public User login(User obj) {
        return objDao.login(obj);
    }
}
