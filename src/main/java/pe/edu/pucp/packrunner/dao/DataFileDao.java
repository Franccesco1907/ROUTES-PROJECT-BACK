package pe.edu.pucp.packrunner.dao;

import pe.edu.pucp.packrunner.models.DataFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface DataFileDao {

    // Get All
    public List<DataFile> getAll();

    // Get One
    public DataFile get(long id);

    // Register
    public DataFile register(DataFile region);

    // Update
    public DataFile update(DataFile region);

    // Delete
    public int delete(long id);

    public int readRegionFile() throws FileNotFoundException;

    public int readOfficeFile() throws FileNotFoundException;

    public int readEdgeFile() throws FileNotFoundException;

    public int readTruckFile() throws FileNotFoundException;

    public int readOrderFile(String orderFileName) throws FileNotFoundException;

    public int readBlockFile() throws FileNotFoundException;

    public int readMaintenanceFile() throws FileNotFoundException;

    // Read all Files in server
    public int readAllFiles() throws FileNotFoundException;
}
