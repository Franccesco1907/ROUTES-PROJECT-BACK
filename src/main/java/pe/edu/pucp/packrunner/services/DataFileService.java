package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.pucp.packrunner.dao.BlockDao;
import pe.edu.pucp.packrunner.dao.ClientDao;
import pe.edu.pucp.packrunner.dao.DataFileDao;
import pe.edu.pucp.packrunner.dao.EdgeDao;
import pe.edu.pucp.packrunner.dao.OrderDao;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Client;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.utils.Process;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.*;

@Service
public class DataFileService {

    @Autowired
    DataFileDao dataFileDao;

    @Autowired
    VertexDao vertexDao;

    @Autowired
    ClientDao clientDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    EdgeDao edgeDao;

    @Autowired
    BlockDao blockDao;

    public int readRegionFile() throws FileNotFoundException {
        return dataFileDao.readRegionFile();
    }

    public int readOfficeFile() throws FileNotFoundException {
        return dataFileDao.readOfficeFile();
    }

    public int readEdgeFile() throws FileNotFoundException {
        return dataFileDao.readEdgeFile();
    }

    public int readTruckFile() throws FileNotFoundException {
        return dataFileDao.readTruckFile();
    }

    public int readOrderFile(String orderFileName) throws FileNotFoundException {
        return dataFileDao.readOrderFile(orderFileName);
    }

    public int readBlockFile() throws FileNotFoundException {
        return dataFileDao.readBlockFile();
    }

    public int readMaintenanceFile() throws FileNotFoundException {
        return dataFileDao.readMaintenanceFile();
    }

    public int readAll() throws FileNotFoundException {
        return dataFileDao.readAllFiles();
    }

}
