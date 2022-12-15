package pe.edu.pucp.packrunner.dao.imp;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pe.edu.pucp.packrunner.dao.*;
import pe.edu.pucp.packrunner.dto.out.EdgeOut;
import pe.edu.pucp.packrunner.models.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.FileReadMethods.*;
import static pe.edu.pucp.packrunner.utils.PrintMethods.printLine;

@Transactional
@Repository
@SuppressWarnings("unchecked")
public class DataFileDaoImp implements DataFileDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    RegionDao regionDao;

    @Autowired
    VertexDao vertexDao;

    @Autowired
    EdgeDao edgeDao;

    @Autowired
    TruckDao truckDao;

    @Transactional
    @Override
    public List<DataFile> getAll() {
        List<DataFile> result = null;
        try {
            String hql = "FROM DataFile";
            result = (List<DataFile>) entityManager.createQuery(hql).getResultList();
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public DataFile get(long id) {
        DataFile result = null;
        try {
            result = entityManager.find(DataFile.class, id);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public DataFile register(DataFile datafile) {
        DataFile result = null;
        try {
            result = entityManager.merge(datafile);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public DataFile update(DataFile datafile) {
        DataFile result = null;
        try {
            result = entityManager.merge(datafile);
        } catch (Exception ex) {
            System.out.print(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int delete(long id) {
        int result = 0;
        try {
            DataFile obj = get(id);
            entityManager.remove(obj);
            result = 1;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Override
    public int readRegionFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;

        String officeFilename = "./data/inf226.oficinas.txt";
        List<Region> regions = new ArrayList<>();

        System.out.println("Region Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Regions from File...");
            readRegions(officeFilename, regions);
            System.out.println("Inserting " + regions.size() + " offices...");
            for (Region region : regions)
                entityManager.merge(region);
            result = 1;
            System.out.println("Region Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readOfficeFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;

        String officeFilename = "./data/inf226.oficinas.txt";

        List<Province> provinces = new ArrayList<>();
        List<Vertex> offices = new ArrayList<>();
        List<Vertex> depots = new ArrayList<>();

        System.out.println("Vertex Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Regions...");
            List<Region> regions = regionDao.getAll();
            System.out.println("Reading Vertexes from File...");
            readVertexes(officeFilename, regions, provinces, offices, depots);
            System.out.println("Inserting " + offices.size() + " offices...");
            for (Vertex office : offices)
                entityManager.merge(office);
            System.out.println("Inserting " + depots.size() + " depots...");
            for (Vertex depot : depots)
                entityManager.merge(depot);
            result = 1;
            System.out.println("Vertex Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readEdgeFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;
        String edgeFilename = "./data/inf226.tramos.v.2.0.txt";
        List<Edge> edges = new ArrayList<>();


        System.out.println("Edge Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Offices...");
            List<Vertex> offices = vertexDao.getAllOffices();
            System.out.println("Reading Depots...");
            List<Vertex> depots = vertexDao.getAllDepots();
            System.out.println("Reading Edges from File...");
            readEdges(edgeFilename, edges, offices, depots);
            System.out.println("Inserting " + edges.size() + " edges...");
            for (Edge edge : edges)
                entityManager.merge(edge);
            result = 1;
            System.out.println("Edge Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readTruckFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;
        String truckFilename = "./data/inf226.camiones.txt";
        ArrayList<Truck> trucks = new ArrayList<>();
        System.out.println("Truck Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Vertexes...");
            List<Vertex> vertexes = vertexDao.getAll();
            System.out.println("Reading Trucks from File...");
            readTrucks(truckFilename, trucks, vertexes);
            System.out.println("Inserting " + trucks.size() + " trucks...");
            for (Truck truck : trucks)
                entityManager.merge(truck);
            result = 1;
            System.out.println("Truck Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readOrderFile(String orderFileName) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        //File Format: ./data/inf226.ventasYYYYMM.txt
        int result = 0;

        orderFileName = "./data/inf226.ventas" + orderFileName + ".txt";

        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();

        System.out.println("Order Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Offices...");
            List<Vertex> offices = vertexDao.getAllOffices();
            System.out.println("Reading Depots...");
            List<Vertex> depots = vertexDao.getAllDepots();
            System.out.println("Reading Orders from File...");
            readOrders(orderFileName, orders, offices, depots, clients);
            System.out.println("Inserting " + clients.size() + " clients...");
            for (Client client : clients)
                entityManager.merge((client));
            System.out.println("Inserting " + orders.size() + " orders...");
            for (Order order : orders)
                entityManager.merge(order);
            result = 1;
            System.out.println("Order Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readBlockFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;

        String[] blockFilename = {
                "./data/inf226.bloqueo.01.txt", "./data/inf226.bloqueo.02.txt", "./data/inf226.bloqueo.03.txt",
                "./data/inf226.bloqueo.04.txt", "./data/inf226.bloqueo.05.txt", "./data/inf226.bloqueo.06.txt",
                "./data/inf226.bloqueo.07.txt", "./data/inf226.bloqueo.08.txt", "./data/inf226.bloqueo.09.txt",
                "./data/inf226.bloqueo.10.txt", "./data/inf226.bloqueo.11.txt", "./data/inf226.bloqueo.12.txt" };
        try {
            List<Edge> edges = edgeDao.getAll(null);

            System.out.println("Block Reading from File Initiated");
            printLine(100, "=");
            for (int i = 0; i < 12; i++) {
                List<Block> blocks = new ArrayList<>();
                System.out.println("Reading Blocks from File...");
                readBlocks(blockFilename[i], blocks, edges);
                System.out.println("Inserting " + blocks.size() + " blocks...");
                for (Block b : blocks)
                    entityManager.merge(b);
            }
            result = 1;
            System.out.println("Block Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readMaintenanceFile() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result = 0;

        String maintenanceFilename = "./data/inf226.plan.mant.trim.abr.may.jun.txt";
        List<Maintenance> maintenances = new ArrayList<>();

        System.out.println("Maintenance Reading from File Initiated");
        printLine(100, "=");
        try {
            System.out.println("Reading Trucks...");
            List<Truck> trucks = truckDao.getAll();
            System.out.println("Reading Maintenances from File...");
            readMaintenances(maintenanceFilename, maintenances, trucks);
            System.out.println("Inserting " + maintenances.size() + " blocks...");
            for (Maintenance maintenance : maintenances)
                entityManager.merge((maintenance));
            result = 1;
            System.out.println("Maintenance Reading from File Finished Successfully");
            long end = System.currentTimeMillis();
            System.out.printf("Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
            printLine(100, "=");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @Transactional
    @Override
    public int readAllFiles() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        int result;

        System.out.println("Complete Reading from Files Initiated");
        printLine(100, "=");

        result = readRegionFile();
        if(result==0) return result;
        result = readOfficeFile();
        if(result==0) return result;
        result = readTruckFile();
        if(result==0) return result;
        result = readEdgeFile();
        if(result==0) return result;
        result = readBlockFile();
        if(result==0) return result;
        result = readMaintenanceFile();

        String[] orderFileNames = {
                "202202", "202203", "202204", "202205", "202206",
                "202207", "202208", "202209", "202210", "202211",
                "202212", "202301", "202302", "202302", "202303",
                "202304", "202305" };
        for (String orderFileName : orderFileNames)
            readOrderFile(orderFileName);

        System.out.println("Reading of All Files Finished Successfully");
        long end = System.currentTimeMillis();
        System.out.printf("Final Runtime: %6.3f seconds\n", ((double) (end-start))/1000);
        printLine(100, "=");
        return result;
    }
}
