package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.TruckDao;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.models.Province;
import pe.edu.pucp.packrunner.models.Region;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.models.Vertex;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.readTrucks;
import static pe.edu.pucp.packrunner.utils.FileReadMethods.readVertexes;

@Service
public class TruckService {
    @Autowired
    TruckDao objDao;

    VertexDao vertexDao;

    //Trae a todos
    public List<Truck> getAll(){
        return objDao.getAll();
    }

    //Trae uno
    public Truck get(long id){
        return objDao.get(id);
    }

    //Registrar
    public Truck register(Truck obj){
        return objDao.register(obj);
    }

    //Actualizar
    public Truck update(Truck obj){
        return objDao.update(obj);
    }

    //Eliminar
    public int delete(long id){
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

        // Data Filenames
        String truckFilename = "./data/inf226.camiones.txt";

        ArrayList<Truck> trucks = new ArrayList<>();

        readTrucks(truckFilename, trucks, depots);

        for (Truck truck : trucks) {
            objDao.register(truck);
        }
    }
}
