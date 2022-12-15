package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.edu.pucp.packrunner.dao.TruckPlanDao;
import pe.edu.pucp.packrunner.dto.out.SimulationOut;
import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.ArrayList;
import java.util.List;

@Service
public class TruckPlanService {
    @Autowired
    TruckPlanDao objDao;

    @Autowired
    SimulationService simService;

    // Get All
    @Cacheable(value = "truckPlans")
    public SimulationOut getAll(Long idSimulation, Integer run, String status, Boolean travelling) {

        List<TruckPlan> truckPlans = objDao.getAll(idSimulation, run, status, travelling);
        return new SimulationOut(truckPlans);
    }

    // Get complete truck plan
    public TruckPlan getComplete(long id) {
        return objDao.getComplete(id);
    }

    // Get One
    public TruckPlanOut get(long id) {
        return new TruckPlanOut(objDao.get(id));
    }

    // Register
    public TruckPlan register(TruckPlan obj) {
        return objDao.register(obj);
    }

    // Update
    public TruckPlan update(TruckPlan obj) {
        return objDao.update(obj);
    }

    // Delete
    public int delete(long id) {
        return objDao.delete(id);
    }

    // Disable a Truck
    public List<TruckPlanOut> disable(Long idTruckPlan) {
        List<TruckPlanOut> result = new ArrayList<>();
        System.out.println("Truck Disabling Started...");

        System.out.println("Reading Truck Plan...");
        TruckPlan truckPlan = getComplete(idTruckPlan);
        System.out.println(truckPlan);

        System.out.println("Disabling truck Plan...");
        List<Order> lateOrders = truckPlan.disable();
        System.out.println(truckPlan);

        System.out.println("Saving changes to truck Plan...");
        objDao.register(truckPlan);


        if (!lateOrders.isEmpty()) {
            System.out.println("Reassigning orders...");
            result = simService.runAlgorithm(truckPlan.getSimulation().getId(),
                    truckPlan.getRun(), lateOrders, truckPlan.getSimulation().getClock(), false);
        }

        System.out.println("Truck Plan has been disabled...");
        System.out.println(truckPlan);

        return result;
    }

    // Cripple a Truck
    public List<TruckPlanOut> cripple(Long idTruckPlan) {
        System.out.println("Truck Crippling Started...");
        List<TruckPlanOut> result = new ArrayList<>();

        System.out.println("Reading Truck Plan...");
        TruckPlan truckPlan = getComplete(idTruckPlan);
        System.out.println(truckPlan);

        System.out.println("Crippling truck Plan...");
        List<Order> lateOrders = truckPlan.cripple();
        objDao.register(truckPlan);

        System.out.println("Saving changes to truck Plan...");
        objDao.register(truckPlan);

        if (!lateOrders.isEmpty()) {
            System.out.println("Reassigning orders...");
            result = simService.runAlgorithm(truckPlan.getSimulation().getId(),
                    truckPlan.getRun(), lateOrders, truckPlan.getSimulation().getClock(), false);
        }

        System.out.println("Truck Plan has been crippled...");
        System.out.println(truckPlan);

        return result;
    }

    // Kill a Truck
    public List<TruckPlanOut> kill(Long idTruckPlan) {
        System.out.println("Truck Killing Started...");
        List<TruckPlanOut> result = new ArrayList<>();

        System.out.println("Reading Truck Plan...");
        TruckPlan truckPlan = getComplete(idTruckPlan);
        System.out.println(truckPlan);

        System.out.println("Killing truck Plan...");
        List<Order> lateOrders = truckPlan.kill();
        objDao.register(truckPlan);

        System.out.println("Saving changes to truck Plan...");
        objDao.register(truckPlan);

        if (!lateOrders.isEmpty()) {
            System.out.println("Reassigning orders...");
            result = simService.runAlgorithm(truckPlan.getSimulation().getId(),
                    truckPlan.getRun(), lateOrders, truckPlan.getSimulation().getClock(), false);
        }

        System.out.println("Truck Plan has been killed...");
        System.out.println(truckPlan);

        return result;
    }


    public TruckPlan getFirstBySimulation(long id) {
        return objDao.getFirstBySimulation(id);
    }

    public List<TruckPlan> listBySimulation(long id) {
        return objDao.listBySimulation(id);
    }

    public List<TruckPlanOut> getBySimulation(Long id) {
        List<TruckPlan> result = objDao.getBySimulation(id);
        List<TruckPlanOut> truckPlanOuts = new ArrayList<>();

        for (TruckPlan truckPlan : result)
            truckPlanOuts.add(new TruckPlanOut(truckPlan));
        return truckPlanOuts;
    }
}
