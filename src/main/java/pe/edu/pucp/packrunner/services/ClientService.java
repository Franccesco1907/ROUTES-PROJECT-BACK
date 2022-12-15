package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import pe.edu.pucp.packrunner.dao.ClientDao;
import pe.edu.pucp.packrunner.models.Client;
import pe.edu.pucp.packrunner.models.User;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientDao objDao;

    // Trae a todos
    public List<Client> getAll() {
        return objDao.getAll();
    }

    // Trae uno
    public Client get(long id) {
        return objDao.get(id);
    }

    // Registrar
    public Client register(Client obj) {
        User u = obj.getUser();
        String hash = generateHash(u.getPassword());
        u.setPassword(hash);
        obj.setUser(u);
        return objDao.register(obj);
    }

    // Actualizar
    public Client update(Client obj) {
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

    public Client getByUser(long id) {
        return objDao.getByUser(id);
    }
}
