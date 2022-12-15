package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.ProvinceDao;
import pe.edu.pucp.packrunner.models.Province;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class ProvinceDaoImp implements ProvinceDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Province> getAll() {
        List<Province> resultado = null;
        try {
            String hql = "FROM Province";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Province get(long id) {
        Province resultado = null;
        try {
            resultado = entityManager.find(Province.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Province getByName(String name) {
        Province resultado = null;
        try {
            String hql = "FROM Province WHERE name = :name";

            resultado = (Province) entityManager.createQuery(hql).setParameter("name", name).getResultList().get(0);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public List<Province> getByRegion(long id) {
        List<Province> resultado = null;
        try {
            String hql = "FROM Province WHERE region.id = :id";

            resultado = entityManager.createQuery(hql).setParameter("id", id).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public Province register(Province province) {
        Province resultado = null;
        try {
            resultado = entityManager.merge(province);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public Province update(Province province) {
        Province resultado = null;
        try {
            resultado = entityManager.merge(province);
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
            Province province = get(id);
            entityManager.remove(province);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Province findByNameDepartment(String provinceName, String departmentName) {
        Province resultado = null;
        try {
            String hql = "FROM Province WHERE " + provinceName + " = name AND " + departmentName
                    + " = department.name;";
            resultado = (Province) entityManager.createQuery(hql).getResultList().get(0);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }
}
