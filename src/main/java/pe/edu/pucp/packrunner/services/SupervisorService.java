package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import pe.edu.pucp.packrunner.dao.SupervisorDao;
import pe.edu.pucp.packrunner.models.Supervisor;
import pe.edu.pucp.packrunner.models.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SupervisorService {
    @Autowired
    SupervisorDao objDao;

    // Trae a todos
    public List<Supervisor> getAll() {
        return objDao.getAll();
    }

    // Trae uno
    public Supervisor get(long id) {
        return objDao.get(id);
    }

    // Registrar
    public Supervisor register(Supervisor obj) {
        User u = obj.getUser();
        String hash = generateHash(u.getPassword());
        u.setPassword(hash);
        obj.setUser(u);
        return objDao.register(obj);
    }

    // Actualizar
    public Supervisor update(Supervisor obj) {
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
}
