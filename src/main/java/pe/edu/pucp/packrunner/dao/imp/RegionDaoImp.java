package pe.edu.pucp.packrunner.dao.imp;

import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.RegionDao;
import pe.edu.pucp.packrunner.models.Region;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class RegionDaoImp implements RegionDao {
    @PersistenceContext
    EntityManager entityManager;

    // Listar todos
    @Transactional
    @Override
    public List<Region> getAll() {
        List<Region> resultado = null;
        try {
            String hql = "FROM Region";
            resultado = entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Listar uno
    @Transactional
    @Override
    public Region get(long id) {
        Region resultado = null;
        try {
            resultado = entityManager.find(Region.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Registrar
    @Transactional
    @Override
    public Region register(Region region) {
        Region resultado = null;
        try {
            resultado = entityManager.merge(region);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }

    // Actualizar
    @Transactional
    @Override
    public Region update(Region region) {
        Region resultado = null;
        try {
            resultado = entityManager.merge(region);
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
            Region region = get(id);
            entityManager.remove(region);
            resultado = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return resultado;
    }

    @Transactional
    @Override
    public Region findByName(String name) {
        Region resultado = null;
        try {
            String hql = "FROM Region WHERE " + name + " = name;";
            resultado = (Region) entityManager.createQuery(hql).getResultList().get(0);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return resultado;
    }
}
