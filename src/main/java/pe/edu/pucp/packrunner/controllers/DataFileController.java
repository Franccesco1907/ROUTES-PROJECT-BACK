package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.packrunner.services.DataFileService;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("dataFile")
public class DataFileController {
    @Autowired
    DataFileService DataFileService;

    // Read Region File
    @RequestMapping(value = "/readRegion", method = RequestMethod.GET)
    int readRegionFile() throws FileNotFoundException {
        return DataFileService.readRegionFile();
    }

    // Read Office File (Inserts all Depots and Offices)
    @RequestMapping(value = "/readOffice", method = RequestMethod.GET)
    int readOfficeFile() throws FileNotFoundException {
        return DataFileService.readOfficeFile();
    }

    // Read Edge File
    @RequestMapping(value = "/readEdge", method = RequestMethod.GET)
    int readEdgeFile() throws FileNotFoundException {
        return DataFileService.readEdgeFile();
    }

    // Read Truck File
    @RequestMapping(value = "/readTruck", method = RequestMethod.GET)
    int readTruckFile() throws FileNotFoundException {
        return DataFileService.readTruckFile();
    }

    // Read Order File
    @RequestMapping(value = "/readOrder", method = RequestMethod.GET)
    int readOrderFile(@RequestParam(name = "file") String orderFileName) throws FileNotFoundException {
        return DataFileService.readOrderFile(orderFileName);
    }

    // Read Block File
    @RequestMapping(value = "/readBlock", method = RequestMethod.GET)
    int readBlockFile() throws FileNotFoundException {
        return DataFileService.readBlockFile();
    }

    // Read Maintenance File
    @RequestMapping(value = "/readMaintenance", method = RequestMethod.GET)
    int readMaintenanceFile() throws FileNotFoundException {
        return DataFileService.readMaintenanceFile();
    }

    // Read All Files (DEPRECATED)
    @RequestMapping(value = "/readAll", method = RequestMethod.GET)
    void readAll() throws FileNotFoundException {
        DataFileService.readAll();
    }

}
