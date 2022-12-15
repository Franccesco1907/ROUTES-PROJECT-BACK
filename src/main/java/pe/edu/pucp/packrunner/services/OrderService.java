package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.OrderDao;
import pe.edu.pucp.packrunner.dao.TruckPlanDao;
import pe.edu.pucp.packrunner.dao.VertexDao;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Simulation;
import pe.edu.pucp.packrunner.models.TruckPlan;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao objDao;

    @Autowired
    SimulationService simService;

    @Autowired
    TruckPlanService truckPlanService;

    @Autowired
    VertexDao verDao;

    @Autowired
    TruckPlanDao truckPlanDao;

    // Get All
    public List<Order> getAll(Date start, Date end, Integer type) { return objDao.getAll(start, end, type);
    }

    // Get One
    public Order get(long id) {
        return objDao.get(id);
    }

    // Register
    public Order register(Order obj) {
        List<Vertex> depots = verDao.getAllDepots();
        Vertex dep = null;
        double min = Double.MAX_VALUE;
        for (Vertex d : depots) {
            double dis = Vertex.distHaversine(d, obj.getOffice());
            System.out.println(dis);
            if (dis < min) {
                min = dis;
                dep = d;
            }
        }
        obj.setDepot(dep);
        obj.setUnassignedPackages(obj.getNumPackages());
        Order resultOrder = objDao.register(obj);
        return resultOrder;
    }

    public void runAlgorithm(long id) {
        Simulation sim = simService.getLastSimulation();
        if (sim == null) {
            sim = new Simulation();
            sim.setRunning(true);
            sim.setSpeed(1);
            sim.setType(SimulationType.DAY2DAY);
            sim.setClock(new Date());
            simService.register(sim);
        }

        TruckPlan tp = truckPlanDao.getFirstBySimulation(sim.getId());
        simService.runAlgorithm(sim.getId(), tp.getRun(), id);
    }

    // Update
    public Order update(Order obj) {
        return objDao.update(obj);
    }

    // Delete
    public int delete(long id) {
        return objDao.delete(id);
    }

    public List<Order> getByClient(long id) {
        return objDao.getByClient(id);
    }

}
