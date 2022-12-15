package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.ProvinceDao;
import pe.edu.pucp.packrunner.dao.RegionDao;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.models.Province;
import pe.edu.pucp.packrunner.models.Region;
import pe.edu.pucp.packrunner.models.Vertex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.*;

@Service
public class ProvinceService {
    @Autowired
    ProvinceDao objDao;

    @Autowired
    RegionDao regDao;

    @Autowired
    VertexDao verDao;

    // Trae a todos
    public List<Province> getAll() {
        return objDao.getAll();
    }

    // Trae varios
    public List<Province> getByRegion(long id) {
        return objDao.getByRegion(id);
    }

    // Trae uno
    public Province get(long id) {
        return objDao.get(id);
    }

    // Trae por nombre de provincia
    public Province getByName(String name) {
        return objDao.getByName(name);
    }

    // Registrar
    public Province register(Province obj) {
        return objDao.register(obj);
    }

    // Actualizar
    public Province update(Province obj) {
        return objDao.update(obj);
    }

    // Eliminar
    public int delete(long id) {
        return objDao.delete(id);
    }

    public void registerAll() throws FileNotFoundException {
        // Data Filenames
        String officeFilename = "./data/inf226.oficinas.txt";

        ArrayList<Region> regions = new ArrayList<>();
        ArrayList<Province> provinces = new ArrayList<>();
        ArrayList<Vertex> offices = new ArrayList<>();
        ArrayList<Vertex> depots = new ArrayList<>();
        readVertexes(officeFilename, regions, provinces, offices, depots);

        for (Vertex ver : offices)
            verDao.register(ver);
        for (Vertex ver : depots)
            verDao.register(ver);
    }
}
