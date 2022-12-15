package pe.edu.pucp.packrunner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.pucp.packrunner.dao.*;
import pe.edu.pucp.packrunner.dto.out.TruckPlanOut;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Simulation;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.models.TruckPlan;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.models.algorithm.Graph;
import pe.edu.pucp.packrunner.models.algorithm.PSO;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SimulationService {

    @Autowired
    SimulationDao simulationDao;

    @Autowired
    TruckDao truckDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    VertexDao vertexDao;

    @Autowired
    EdgeDao edgeDao;

    @Autowired
    BlockDao blockDao;

    @Autowired
    MaintenanceDao maintenanceDao;

    @Autowired
    TruckPlanDao truckPlanDao;

    public Simulation register(Simulation simulation) {
        return simulationDao.register(simulation);
    }

    public Simulation get(long id) {
        return simulationDao.get(id);
    }

    // Register a new simulation
    public Simulation startSimulation(String stringType, Date clock) {

        // Convert Type String to Enum
        SimulationType type;
        if (stringType == null)
            type = SimulationType.DAY2DAY;
        else
            switch (stringType) {
                case "7days":
                    type = SimulationType.SEVEN;
                    break;
                case "collapse":
                    type = SimulationType.COLLAPSE;
                    break;
                default:
                    type = SimulationType.DAY2DAY;
            }

        // Initialize the simulation
        Simulation simulation = new Simulation(type, clock);
        Simulation result = simulationDao.register(simulation);

        // Get All Trucks
        List<Truck> trucks = truckDao.getAll();

        // Initialize the simulation's truck Plans
        for (Truck truck : trucks)
            truckPlanDao.register(new TruckPlan(truck, result));

        return result;
    }

    public List<TruckPlanOut> maintenance(TruckPlan truckPlan) {
        System.out.println("Truck Maintenance Started...");
        List<TruckPlanOut> result = new ArrayList<>();

        System.out.println(truckPlan);

        System.out.println("Running Maintenance for Truck Plan " + truckPlan.getId() + " ...");
        List<Order> lateOrders = truckPlan.maintenance();
        truckPlanDao.register(truckPlan);

        System.out.println("Saving changes to truck Plan...");
        truckPlanDao.register(truckPlan);

        if (!lateOrders.isEmpty()) {
            System.out.println("Reassigning orders...");
            result = runAlgorithm(truckPlan.getSimulation().getId(),
                    truckPlan.getRun(), lateOrders, truckPlan.getSimulation().getClock(), false);
        }

        System.out.println("Truck Plan has been put under maintenance...");
        System.out.println(truckPlan);

        return result;
    }

    public List<TruckPlanOut> runMaintenance(Long idSimulation, Integer run, Date date) {
        List<TruckPlanOut> result = new ArrayList<>();
        System.out.println("Maintenance Run Started...");

        //Get the trucks that need maintenance
        System.out.println("Reading Maintenances...");
        List<Truck> trucks = maintenanceDao.getTrucks(date);

        System.out.println(trucks.size() + " truck" + (trucks.size()!=1?"s":"") + " require maintenance...");
        if (!trucks.isEmpty()) {

            // Get the simulation
            System.out.println("Reading Truck Plans...");
            Simulation simulation = get(idSimulation);
            List<TruckPlan> truckPlans = truckPlanDao.getAll(idSimulation, run, null, null);
            simulation.update(date, truckPlans);

            List<TruckPlan> selected = truckPlans.stream()
                    .filter(tp -> tp.getTruck().getId() == trucks.get(0).getId())
                    .collect(Collectors.toList());

            for (TruckPlan truckPlan : selected) System.out.println(truckPlan);

            System.out.println("Assigning Maintenance...");
            for (TruckPlan truckPlan : selected) {
                maintenance(truckPlan);
                result.add(new TruckPlanOut(truckPlan));
            }
        }

        System.out.println("Maintenance has been successfully run...");
        return result;
    }

    // Run the Algorithm for Orders in a date range
    public List<TruckPlanOut> runAlgorithm(Long idSimulation, Integer run, Date start, Date end) {
        System.out.println("Algorithm Execution Started -- Date Range");
        // Order List declaration
        System.out.println("Reading Orders from date range...");
        List<Order> orders = orderDao.getAll(start, end, 1);
        System.out.println("Running Algorithm for " + orders.size() + " order" + (orders.size()!=1?"s...":"..."));
        return runAlgorithm(idSimulation, run, orders, start, true);
    }

    // Run the Algorithm for a list of orders
    public List<TruckPlanOut> runAlgorithm(Long idSimulation, Integer run, List<Order> orders, Date date, boolean reset) {

        // If reset value is true, all orders are reset
        if (reset) {
            System.out.println("Orders are reset...");
            for (Order order : orders) order.reset();
        }

        // Get the simulation
        System.out.println("Reading Truck Plans...");
        Simulation simulation = get(idSimulation);
        List<TruckPlan> truckPlans = truckPlanDao.getAll(idSimulation, run, null, null);

        System.out.println("Simulation is updated...");
        simulation.update(date, truckPlans);

        // Graph classes declaration
        System.out.println("Reading Offices...");
        List<Vertex> offices = vertexDao.getAllOffices();
        System.out.println("Reading Depots...");
        List<Vertex> depots = vertexDao.getAllDepots();
        System.out.println("Reading Edges...");
        List<Edge> edges = edgeDao.getAll(null);
        //List<Block> blocks = blockDao.getAll(null, null);
        System.out.println("Edges: " + edges.size());

        System.out.println("Reading Edges that are blocked...");
        List<Edge> blockedEdges = blockDao.getBlockedEdges(simulation.getClock());

        System.out.println("Filtering Edges out of the Graph");
        edges.removeIf(blockedEdges::contains);
        System.out.println("Edges after filtering: " + edges.size());

        // Graph is initialized
        System.out.println("Building Graph");
        Graph graph = new Graph(edges, offices, depots);

        System.out.println("Executing PSO Algorithm...");
        PSO p = new PSO(graph, 10, 20);
        List<TruckPlan> tp = p.run(orders, truckPlans);

        List<TruckPlan> result = new ArrayList<>();

        System.out.println("Simulation is updated...");
        simulation.update(simulation.getClock(), result);

        System.out.println("Registering changes...");
        for (TruckPlan truckPlan : tp)
            result.add(truckPlanDao.register(truckPlan));
        simulationDao.register(simulation);

        List<TruckPlanOut> truckPlanOuts = new ArrayList<>();
        for (TruckPlan truckPlan : result)
            if (truckPlan.isTravelling())
                truckPlanOuts.add(new TruckPlanOut(truckPlan));

        System.out.println("Algorithm Run Finished...");

        return truckPlanOuts;
    }

    // Run the algorithm for a single new order
    public List<TruckPlanOut> runAlgorithm(Long idSimulation, Integer run, Long idOrder) {
        System.out.println("Algorithm Execution Started -- Single Order");
        System.out.println("Reading Orders from ID " + idOrder + "...");
        Order order = orderDao.get(idOrder);
        order.setLimitDate(order.calculateLimitDate());
        order.setType(SimulationType.DAY2DAY);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        System.out.println("Running Algorithm for order " + order.getId());
        return runAlgorithm(idSimulation, run, orders, order.getOrderDate(), true);
    }

    public Simulation getLastSimulation() {
        return simulationDao.getLastSimulation();
    }
}
