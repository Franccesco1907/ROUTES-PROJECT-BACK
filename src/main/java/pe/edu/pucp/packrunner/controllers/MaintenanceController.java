package pe.edu.pucp.packrunner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.services.MaintenanceService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("maintenance")
public class MaintenanceController {

    @Autowired
    MaintenanceService maintenanceService;

    // Get All
    @RequestMapping(value = "/trucks", method = RequestMethod.GET)
    List<Truck> getAll(@RequestParam(name = "date") @DateTimeFormat(pattern = "dd-MM-yyyy") Date date) {
        return maintenanceService.getTrucks(date);
    }
}
