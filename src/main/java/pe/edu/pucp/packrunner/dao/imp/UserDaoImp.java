package pe.edu.pucp.packrunner.dao.imp;

import pe.edu.pucp.packrunner.dao.UserDao;

import org.springframework.stereotype.Repository;

import org.springframework.util.StringUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import pe.edu.pucp.packrunner.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class UserDaoImp implements UserDao {
    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public List<User> getAll() {
        List<User> resultado = null;
        try {
            String hql = "FROM User";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public User get(long id) {
        User resultado = null;
        try {
            resultado = entityManager.find(User.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public User register(User user) {
        User resultado = null;
        try {
            resultado = entityManager.merge(user);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public User update(User user) {
        User resultado = null;
        try {
            resultado = entityManager.merge(user);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Eliminar
    @Transactional
    @Override
    public int delete(long id) {
        int resultado = 0;
        try {
            User user = get(id);
            entityManager.remove(user);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public User login(User obj) {
        boolean isAuthenticated = false;
        String hql = "From User as u WHERE u.password is not null and u.email = :email";
        List<User> result = entityManager.createQuery(hql.toString()).setParameter("email", obj.getEmail())
                .getResultList();
        if (result.size() == 0)
            return null;
        User user = result.get(0);
        isAuthenticated = true;
        if (!hasText(obj.getPassword())) {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String passwordReceived = obj.getPassword();
            String password = user.getPassword();
            isAuthenticated = argon2.verify(password, passwordReceived.getBytes(StandardCharsets.UTF_8));
        }
        if (isAuthenticated)
            return user;
        return null;
    }
}
