package pe.edu.pucp.packrunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pe.edu.pucp.packrunner.models.*;
import pe.edu.pucp.packrunner.models.algorithm.Graph;
import pe.edu.pucp.packrunner.models.algorithm.PSO;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;

import java.io.FileNotFoundException;
import java.util.*;

import static pe.edu.pucp.packrunner.models.Order.dateSubList;
import static pe.edu.pucp.packrunner.utils.FileReadMethods.*;
import static pe.edu.pucp.packrunner.utils.PrintMethods.*;

@SpringBootApplication
@EnableScheduling
public class PackrunnerApplication {

	public static void main(String[] args) throws FileNotFoundException {
		// testAlgorithm();
		// testTruckBreaking();
		SpringApplication.run(PackrunnerApplication.class, args);
	}

	public static void PURUS() throws FileNotFoundException {
		// Read Offices
		List<Region> regions = new ArrayList<>();
		List<Province> provinces = new ArrayList<>();
		List<Vertex> offices = new ArrayList<>();
		List<Vertex> depots = new ArrayList<>();
		// Read Edges
		List<Edge> edges = new ArrayList<>();
		// Read Trucks
		List<Truck> trucks = new ArrayList<>();
		// Read Orders
		List<Client> clients = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		// Read Blocks
		List<Block> blocks = new ArrayList<>();
		// Read Maintenances
		List<Maintenance> maintenances = new ArrayList<>();

		testFileReading(regions, provinces, offices, depots, edges, trucks, clients, orders, blocks, maintenances);
		Graph graph = new Graph(edges, offices, depots, blocks);

		Optional<Vertex> origin = depots.stream().filter(d -> d.getProvince().getName().equals("AREQUIPA")).findAny();
		Optional<Vertex> destiny = offices.stream().filter(o -> o.getProvince().getName().equals("PURUS")).findAny();

		long start = System.currentTimeMillis();

		if (origin.isPresent() && destiny.isPresent()) {
			List<Vertex> path = graph.AStar(origin.get(), destiny.get());
			if (path.isEmpty())
				System.out.println("PATH NOT FOUND");
			else
				for (Vertex vertex : path)
					System.out.println(vertex);
		} else {
			System.out.println("ONE OF THE VERTICES WAS NOT FOUND");
		}

		long end = System.currentTimeMillis();
		System.out.println(("Runtime: " + (double) (end - start) / 1000));
	}

	public static void testAlgorithm() throws FileNotFoundException {

		// Read Offices
		List<Region> regions = new ArrayList<>();
		List<Province> provinces = new ArrayList<>();
		List<Vertex> offices = new ArrayList<>();
		List<Vertex> depots = new ArrayList<>();
		// Read Edges
		List<Edge> edges = new ArrayList<>();
		// Read Trucks
		List<Truck> trucks = new ArrayList<>();
		// Read Orders
		List<Client> clients = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		// Read Blocks
		List<Block> blocks = new ArrayList<>();
		// Read Maintenances
		List<Maintenance> maintenances = new ArrayList<>();

		testFileReading(regions, provinces, offices, depots, edges, trucks, clients, orders, blocks, maintenances);

		// Graph created
		Graph graph;

		// Simulation created
		Simulation simulation = new Simulation(SimulationType.SEVEN);
		simulation.setId(1);
		List<TruckPlan> run = new ArrayList<>();
		for (Truck truck : trucks)
			run.add(new TruckPlan(truck, simulation));

		Calendar c = Calendar.getInstance();
		c.set(2022, Calendar.JULY, 1, 0, 0);
		Date start;
		Date end;
		List<Order> batch = new ArrayList<>();

		List<Edge> unblockedEdges;

		for (int i = 0; i < 90; i++) {
			start = c.getTime();
			c.add(Calendar.HOUR, 8);
			end = c.getTime();

			unblockedEdges = new ArrayList<>(edges);
			for (Block block : blocks)
				if (start.after(block.getStartDate()) &&
					start.before(block.getEndDate()))
					unblockedEdges.remove(block.getEdge());
			graph = new Graph(unblockedEdges, offices, depots);

			batch.addAll(0, dateSubList(orders, start, end));
			run = testRun(batch, start, simulation, graph, run);

		}

		printTruckPlans(run);

		for (TruckPlan truckPlan : run)
			if (!truckPlan.getDeliveries().isEmpty()) {
				System.out.println(truckPlan.getTruck());
				printDeliveries(truckPlan.getDeliveries());
			}

		printOrders(batch);
	}

	public static List<TruckPlan> testRun(List<Order> batch, Date date, Simulation simulation, Graph graph,
			List<TruckPlan> truckPlans) {
		simulation.update(date, truckPlans);


		PSO p = new PSO(graph, 10, 10);
		List<TruckPlan> run = p.run(batch, truckPlans);

		//printTruckPlans(run);
		for (TruckPlan truckPlan : run)
			if (!truckPlan.getDeliveries().isEmpty()) {
				//System.out.println(truckPlan.getTruck());
				//printDeliveries(truckPlan.getDeliveries());
			}

		batch.sort(Order.OrderDateComparator);
		//printOrders(batch);

		return run;
	}

	public static void testTruckBreaking() throws FileNotFoundException {

		// Read Offices
		List<Region> regions = new ArrayList<>();
		List<Province> provinces = new ArrayList<>();
		List<Vertex> offices = new ArrayList<>();
		List<Vertex> depots = new ArrayList<>();
		// Read Edges
		List<Edge> edges = new ArrayList<>();
		// Read Trucks
		List<Truck> trucks = new ArrayList<>();
		// Read Orders
		List<Client> clients = new ArrayList<>();
		List<Order> orders = new ArrayList<>();
		// Read Blocks
		List<Block> blocks = new ArrayList<>();
		// Read Maintenances
		List<Maintenance> maintenances = new ArrayList<>();

		testFileReading(regions, provinces, offices, depots,
				edges, trucks, clients, orders, blocks, maintenances);

		// Graph created
		Graph graph = new Graph(edges, offices, depots, blocks);

		// Simulation created
		Calendar c = Calendar.getInstance();
		c.set(2022, Calendar.MAY, 1, 10, 0);
		Simulation simulation = new Simulation(SimulationType.SEVEN, c.getTime());

		List<TruckPlan> truckPlans = new ArrayList<>();
		for (Truck truck : trucks)
			truckPlans.add(new TruckPlan(truck, simulation));

		List<Order> first20 = new ArrayList<>(orders.subList(0, 20));

		PSO p = new PSO(graph, 10, 20);
		p.run(first20, truckPlans);
		printTruckPlans(p.getBestTruckPlans());

		simulation.update(c.getTime(), p.getBestTruckPlans());

		TruckPlan truckPlan = p.getBestTruckPlans().get(40);
		List<TruckPlan> result = p.getBestTruckPlans();
		c.set(2022, Calendar.MAY, 2, 11, 40);
		simulation.update(c.getTime(), result);

		List<Order> lateOrders = truckPlan.cripple();
		if (!lateOrders.isEmpty()) {
			p = new PSO(graph, 10, 20);
			p.run(lateOrders, result);
		}
		simulation.update(c.getTime(), p.getBestTruckPlans());
		printTruckPlans(p.getBestTruckPlans());

		for (TruckPlan tp : p.getBestTruckPlans())
			if (!tp.getDeliveries().isEmpty()) {
				System.out.println(tp.getTruck());
				printDeliveries(tp.getDeliveries());
			}

		/*
		 * System.out.println();
		 * 
		 * c.add(Calendar.HOUR, 11);
		 * simulation.updateSimulation(c.getTime(), p.getBestTruckPlans());
		 * 
		 * truckPlan.disable();
		 * simulation.updateSimulation(c.getTime(), p.getBestTruckPlans());
		 * 
		 * System.out.println(p.getBestTruckPlans().get(0));
		 */
	}

	public static void testFileReading(List<Region> regions,
			List<Province> provinces,
			List<Vertex> offices,
			List<Vertex> depots,
			List<Edge> edges,
			List<Truck> trucks,
			List<Client> clients,
			List<Order> orders,
			List<Block> blocks,
			List<Maintenance> maintenances) throws FileNotFoundException {

		// Data Filenames
		String officeFilename = "./data/inf226.oficinas.txt";
		String edgeFilename = "./data/inf226.tramos.v.2.0.txt";
		String truckFilename = "./data/inf226.camiones.txt";
		String orderFilename = "./data/inf226.ventas202207.txt";
		String blockFilename = "./data/inf226.bloqueo.07.txt";
		String maintenanceFilename = "./data/inf226.plan.mant.trim.abr.may.jun.txt";

		readVertexes(officeFilename, regions, provinces, offices, depots);
		readEdges(edgeFilename, edges, offices, depots);
		readTrucks(truckFilename, trucks, depots);
		readOrders(orderFilename, orders, offices, depots, clients);
		readBlocks(blockFilename, blocks, edges);
		readMaintenances(maintenanceFilename, maintenances, trucks);

		for (int i=0; i<regions.size(); i++) regions.get(i).setId(i+1);
		for (int i=0; i<provinces.size(); i++) provinces.get(i).setId(i+1);
		for (int i=0; i<offices.size(); i++) offices.get(i).setId(i+1);
		for (int i=0; i<depots.size(); i++) depots.get(i).setId(i+offices.size());
		for (int i=0; i<trucks.size(); i++) trucks.get(i).setId(i+1);
		for (int i=0; i<edges.size(); i++) edges.get(i).setId(i+1);
		for (int i=0; i<orders.size(); i++) orders.get(i).setId(i+1);

		// printOffices(offices);
		// printDepots(depots);
		// printEdges(edges);
		// printTrucks(trucks);
		// printOrders(orders);
		// printBlocks(blocks);
		// printMaintenances(maintenances);

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
