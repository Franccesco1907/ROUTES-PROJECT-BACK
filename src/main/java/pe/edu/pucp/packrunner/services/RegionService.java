package pe.edu.pucp.packrunner.services;
import pe.edu.pucp.packrunner.dao.RegionDao;
import pe.edu.pucp.packrunner.models.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    RegionDao regDao;

    // Listar todos
    public List<Region> getAll() {
        return regDao.getAll();
    }

    // Listar uno
    public Region get(long id){
        return regDao.get(id);
    }

    // Registrar
    public Region register(Region region) {
        return regDao.register(region);
    }

    // Actualizar
    public Region update(Region region) {
        return regDao.update(region);
    }

    // Eliminar
    public int delete(long id) {
        return regDao.delete(id);
    }

    // verificar en el registro que no se repitan los nombres
    public Region findByName(String name) {
        return regDao.findByName(name);
    }

}
