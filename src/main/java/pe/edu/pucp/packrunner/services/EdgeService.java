package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.EdgeDao;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Province;
import pe.edu.pucp.packrunner.models.Region;
import pe.edu.pucp.packrunner.models.Vertex;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.*;

@Service
public class EdgeService {
    @Autowired
    EdgeDao objDao;

    // Get All
    public List<EdgeOut> getAll(Long idVertex) {
        List<EdgeOut> result = new ArrayList<>();
        for(Edge edge : objDao.getAll(idVertex)) result.add(new EdgeOut(edge));
        return result;
    }

    // Get One
    public Edge get(long id) {
        return objDao.get(id);
    }

    // Register
    public Edge register(Edge obj) {
        return objDao.register(obj);
    }

    // Update
    public Edge update(Edge obj) {
        return objDao.update(obj);
    }

    // Delete
    public int delete(long id) {
        return objDao.delete(id);
    }

    public Edge block(long id) { return objDao.block(id); }

    public void registerAll() throws FileNotFoundException {
        // Data Filenames
        String edgeFilename = "./data/inf226.tramos.v.2.0.txt";
        String officeFilename = "./data/inf226.oficinas.txt";

        ArrayList<Region> regions = new ArrayList<>();
        ArrayList<Province> provinces = new ArrayList<>();
        ArrayList<Vertex> offices = new ArrayList<>();
        ArrayList<Vertex> depots = new ArrayList<>();
        readVertexes(officeFilename, regions, provinces, offices, depots);

        // Read Edges
        ArrayList<Edge> edges = new ArrayList<>();
        readEdges(edgeFilename, edges, offices, depots);

        for (Edge ed : edges) {
            objDao.register(ed);
        }
    }
}
