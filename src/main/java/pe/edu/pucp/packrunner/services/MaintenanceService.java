package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.MaintenanceDao;
import pe.edu.pucp.packrunner.dao.OrderDao;
import pe.edu.pucp.packrunner.models.Truck;

import java.util.Date;
import java.util.List;

@Service
public class MaintenanceService {

    @Autowired
    MaintenanceDao maintenanceDao;

    // Get Trucks that need Maintenance
    public List<Truck> getTrucks(Date date) {
        return maintenanceDao.getTrucks(date);
    }

}
