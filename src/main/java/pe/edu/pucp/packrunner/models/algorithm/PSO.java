package pe.edu.pucp.packrunner.models.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pe.edu.pucp.packrunner.models.*;
import pe.edu.pucp.packrunner.models.enumerator.TravelType;
import pe.edu.pucp.packrunner.models.enumerator.TruckStatus;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pe.edu.pucp.packrunner.models.Vertex.distHaversine;
import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMHHssDate;
import static pe.edu.pucp.packrunner.utils.PrintMethods.printLine;
import static pe.edu.pucp.packrunner.utils.Utils.routeCost2Opt;
import static pe.edu.pucp.packrunner.utils.Utils.twoOpt;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PSO {
    int numParticles = 10;      // Number of particles in swarm
    int maxIterations = 30;     // Max number of iterations
    List<Particle> particles;
    double c1 = 1.496180;       // Cognitive coefficient
    double c2 = 1.496180;       // Social coefficient
    double w = 0.729844;        // Inertia coefficient
    Graph graph;
    int nFailures = 0;

    List<TruckPlan> bestTruckPlans = new ArrayList<>();
    double bestFitness;

    double[] times = new double[maxIterations];
    double[] failures = new double[maxIterations];
    double[] distances = new double[maxIterations];

    List<Double> best = new ArrayList<>();

    public PSO(Graph graph,
            int numParticles,
            int maxIterations,
            double c1,
            double c2,
            double w) {
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
        this.graph = graph;
        this.particles = new ArrayList<>();
    }

    public PSO(Graph graph, int numParticles, int maxIterations) {
        this.graph = graph;
        this.numParticles = numParticles;
        this.maxIterations = maxIterations;
        this.particles = new ArrayList<>();
    }

    public List<TruckPlan> run(List<Order> orders, List<TruckPlan> truckPlans) {

        long algorithmStart = System.currentTimeMillis();

        List<Order> ordersToAssign = orders.stream()
                .filter(o -> o.getUnassignedPackages() > 0 && !o.isDelivered())
                .collect(Collectors.<Order>toList());

        printInitialData(orders, ordersToAssign, truckPlans);

        if (ordersToAssign.isEmpty()) {
            System.out.println("No order or packages to assign -- Finishing Algorithm Execution");
            return truckPlans;
        }

        List<Order> referenceOrders = new ArrayList<>();
        for (Order order : ordersToAssign) {
            referenceOrders.add(new Order(order));
        }

        // Particles are initialized
        initParticles(ordersToAssign);

        for (int iteration = 0; iteration < maxIterations; iteration++) {

            long iterationStart = System.currentTimeMillis();

            failures[iteration] = 0;
            for (Particle particle : particles) {

                nFailures = 0;

                for (int i=0; i<ordersToAssign.size(); i++) {
                    ordersToAssign.get(i).setUnassignedPackages(referenceOrders.get(i).getUnassignedPackages());
                    ordersToAssign.get(i).setDeliveryDate(referenceOrders.get(i).getDeliveryDate());
                    ordersToAssign.get(i).setDeliveredPackages(referenceOrders.get(i).getDeliveredPackages());
                }

                sortOrdersByPosition(particle, ordersToAssign);
                sortOrdersByPosition(particle, referenceOrders);
                particle.truckPlans = assignOrders(truckPlans, ordersToAssign);

                // We evaluate the fitness
                particle.fitness = evaluateFitness(particle);
                if (particle.fitness >= bestFitness) {
                    particle.personalBest = particle.position;
                    bestTruckPlans = particle.truckPlans;
                    bestFitness = evaluateFitness(particle);
                }

                failures[iteration] += nFailures;
                setNFailures(0);

            }
            failures[iteration] /= numParticles;

            best = findBest(particles);
            for (Particle particle : particles) {
                updateVelocity(particle, best);
                updatePosition(particle);
            }

            long iterationEnd = System.currentTimeMillis();

            times[iteration] = (double) ((iterationEnd - iterationStart))/1000;
            failures[iteration] /= numParticles;
            //printIteration(iteration);
        }

        for(TruckPlan truckPlan : bestTruckPlans) {
            truckPlan.update(truckPlan.getSimulation().getClock());
        }

        long algorithmEnd = System.currentTimeMillis();

        printAlgorithmResults(algorithmStart,algorithmEnd);
        return getBestTruckPlans();
    }

    public void printInitialData(List<Order> orders,
                                 List<Order> newOrders,
                                 List<TruckPlan> truckPlans) {

        System.out.printf("Running PSO Algorithm - SIMULATION: %04d -- RUN: %02d (%s)\n",
                truckPlans.get(0).getSimulation().getId(),
                truckPlans.get(0).getRun(),
                printDDMMHHssDate(truckPlans.get(0).getSimulation().getClock()));
        printLine(100, "=");
        System.out.println("Number of Orders: " + orders.size());
        int totalPreviousPackages = 0;
        int totalDeliveredPackages = 0;
        for (TruckPlan truckPlan : truckPlans)
            for (Delivery delivery : truckPlan.getDeliveries())
                if (delivery.isDelivered()) totalDeliveredPackages += delivery.getNumPackages();
                else totalPreviousPackages += delivery.getNumPackages();
        System.out.println("Total Delivered Packages: " + totalDeliveredPackages);
        System.out.println("Packages from previous Run: " + totalPreviousPackages);
        int totalNewPackages = 0;
        for (Order order : newOrders) totalNewPackages += order.getUnassignedPackages();
        System.out.println("Number of New Packages: " + totalNewPackages);
        System.out.printf("Total Packages to Assign: %d\n", totalNewPackages + totalPreviousPackages);
        printLine(100, "-");
        int totalFreeSpace = 0;
        for (TruckPlan truckPlan : truckPlans) totalFreeSpace += truckPlan.getUnassignedPackages();
        System.out.println("Total Free Space: " + totalFreeSpace);
        int totalSpace = 0;
        for (TruckPlan truckPlan : truckPlans) totalSpace += truckPlan.getTruck().getMaxCapacity();
        System.out.println("Total Space: " + totalSpace);
        printLine(100, "-");
        System.out.println("Total Blocked Edges: " + (3504 - graph.getEdges().size()));
        printLine(100,"=");
    }

    public void printIteration(int iteration){
        System.out.printf("ITERATION %d\n", (iteration + 1));
        printLine(80, "=");
        System.out.printf("Runtime: %4.2f seconds\n", this.times[iteration]);
        System.out.printf("Number of Failures: %4.2f\n", this.failures[iteration]);
        System.out.printf("Best Fitness: %6.2f\n", bestFitness);
        printLine(80, "-");
    }

    public void printAlgorithmResults(double algorithmStart, double algorithmEnd) {

        // Determine final runtime
        double algorithmRuntime = (algorithmEnd - algorithmStart)/1000;
        System.out.println("Best Fitness: " + bestFitness);
        System.out.printf("Total Runtime: %4.2f seconds\n\n", algorithmRuntime);
    }

    public void initParticles(List<Order> orders) {
        for (int i = 0; i < numParticles; i++) {

            Particle particle = new Particle();

            List<Double> positions = new ArrayList<>();
            List<Double> velocities = new ArrayList<>();


            // For each particle dimension assign a random x value [-5.12,5.12] and
            for (Order order : orders) {

                // Assign Position
                double position = Math.random() * (particle.positionUpperBound - particle.positionLowerBound)
                        + particle.positionLowerBound;
                // Assign Velocity
                double velocity = 0;

                positions.add(position);
                velocities.add(velocity);
            }
            // Create the particle
            particle.setPosition(positions);
            particle.setVelocity(velocities);
            // Set particles personal best to initialized values
            particle.setPersonalBest(positions);

            particles.add(particle);
        }
    }

    public void sortOrdersByPosition(Particle particle, List<Order> orders) {
        int i = 0;
        for (Order order : orders) {
            order.setPriority(particle.getPosition().get(i));
            i++;
        }
        orders.sort(Order.PriorityComparator);
    }

    private List<TruckPlan> initializeTruckPlans(List<TruckPlan> truckPlans) {

        ArrayList<TruckPlan> newPlans = new ArrayList<>();
        // A TruckPlan is generated for each truck
        for (TruckPlan truckPlan : truckPlans) {
            TruckPlan newPlan = new TruckPlan(truckPlan);
            newPlans.add(newPlan);
        }
        return newPlans;
    }

    public List<TruckPlan> assignOrders(List<TruckPlan> truckPlans,
                                        List<Order> orders) {

        List<TruckPlan> newPlans = initializeTruckPlans(truckPlans);
        boolean success = false;

        for (Order order : orders) {

            //System.out.println(order.toString());

            Predicate<TruckPlan> operational = t -> t.getStatus() == TruckStatus.OPERATIONAL ;
            Predicate<TruckPlan> sameDepot = t -> t.getTruck().getStartingDepot() == order.getDepot();
            Predicate<TruckPlan> hasSpace = t -> t.getUnassignedPackages() > 0;

            List<TruckPlan> validPlans = newPlans
                    .stream()
                    .filter(operational.and(sameDepot.and(hasSpace)))
                    .sorted(TruckPlan.UnassignedPackagesComparator)
                    .collect(Collectors.<TruckPlan>toList());

            while (order.getUnassignedPackages() > 0) {
                if (validPlans.isEmpty()) break;
                for (TruckPlan validPlan : validPlans) {
                    success = updateTruckPlan(order,validPlan);
                    if(success) {
                        //System.out.println(" Success " + validPlan.getTruck().toString());
                        break;
                    }
                }
                // If no valid truck could be assigned to the order, break
                if(!success) {
                    //System.out.println(" Failure ");
                    break;
                }
            }
            //System.out.println();
        }
        return newPlans;
    }

    public boolean updateTruckPlan(Order order, TruckPlan truckPlan) {

        Date currDate = truckPlan.getSimulation().getClock();
        Predicate<Travel> isCurrentTravel = t -> truckPlan.getCurrentTravel(currDate).isPresent() &&
                t.equals(truckPlan.getCurrentTravel(currDate).get()) && !t.getDateDeparture().equals(currDate);
        Predicate<Travel> isNormalTravel = t -> t.getType() == TravelType.MOVEMENT ||
                t.getType() == TravelType.DELIVERY || t.getType() == TravelType.RETURN;

        // If the truck already assigned all its packages
        if(truckPlan.getUnassignedPackages() == 0) return false;

        // Create the new delivery
        Delivery delivery = createDelivery(order, truckPlan);
        // Create the route for the truck Plan
        List<Vertex> route = createRoute(truckPlan, delivery);
        // Create travels from the deliveries

        List<Travel> previousTravels = new ArrayList<>(truckPlan.getTravels());
        truckPlan.getTravels().removeIf(travel -> (!travel.isTraversed() &&
                !isCurrentTravel.test(travel) &&
                isNormalTravel.test(travel)));
        boolean success = updateTravels(route, truckPlan);

        if(!success) {
            truckPlan.setUnassignedPackages(truckPlan.getUnassignedPackages() + delivery.getNumPackages());
            order.setUnassignedPackages(order.getUnassignedPackages() + delivery.getNumPackages());
            truckPlan.getDeliveries().remove(delivery);
            truckPlan.setTravels(previousTravels);
            nFailures++;
            return false;
        }

        if(truckPlan.getUnassignedPackages() == 0) {
            insertPathHome(truckPlan);
        }

        return true;
    }

    private void insertPathHome(TruckPlan truckPlan) {

        List<Travel> travelHome = new ArrayList<>();

        List<Vertex> pathHome = graph.AStar(
                truckPlan.getTravels().get(truckPlan.getTravels().size()-1).getEndVertex(), // Last Stop
                truckPlan.getTruck().getStartingDepot());                                   // Truck's Depot

        long hoursToMilliseconds = 3600000;
        Date currentDate = new Date(
                truckPlan.getTravels().get(truckPlan.getTravels().size()-1).getDateArrival().getTime() +
                hoursToMilliseconds);

        for (int i = 0; i < pathHome.size() - 1; i++) {

            // Get the edge from the path vertices
            Edge edge = graph.getEdgeOf2Vertex(pathHome.get(i), pathHome.get(i + 1));
            // Set the Truck's arrival time
            Date nextDate = new Date(Math.round((edge.getTime()) * hoursToMilliseconds) + currentDate.getTime());

            Travel travel = new Travel(
                    new Date(currentDate.getTime()),
                    new Date(nextDate.getTime()),
                    edge,
                    pathHome.get(i),
                    pathHome.get(i + 1),
                    TravelType.RETURN);

            currentDate.setTime(nextDate.getTime() + hoursToMilliseconds);
            travelHome.add(travel);
        }
        truckPlan.getTravels().addAll(travelHome);
    }

    private Delivery createDelivery(Order order, TruckPlan truckPlan) {
        Delivery delivery;

        int freePackages = truckPlan.getUnassignedPackages();   // Packages in a truck that have not been assigned
        int packagesToAssign = order.getUnassignedPackages();   // Packages in an order that need to be assigned

        if (packagesToAssign <= freePackages) {
            delivery = new Delivery(order, packagesToAssign);
            order.setUnassignedPackages(0);
            truckPlan.setUnassignedPackages(freePackages - packagesToAssign);
        }
        else {
            delivery = new Delivery(order, freePackages);
            order.setUnassignedPackages(packagesToAssign - freePackages);
            truckPlan.setUnassignedPackages(0);
        }

        return delivery;
    }

    private List<Vertex> createRoute(TruckPlan truckPlan, Delivery delivery) {
        List<Vertex> route = new ArrayList<>();

        // Add the current location to the route
        route.add(truckPlan.getLocation());
        // Add previous vertices to the route
        for (Delivery d : truckPlan.getDeliveries())
            if (!d.isDelivered()) {
                route.add(d.getOrder().getOffice());
            }

        insertDelivery(route, delivery, truckPlan.getDeliveries());

        return route;

    }

    private void insertDelivery(List<Vertex> route,
                                Delivery delivery,
                                List<Delivery> deliveries) {
        int bIndex = 0;
        double bCost = Double.MAX_VALUE;

        // Check the position for the new delivery that yields the least cost
        for (int i = 1; i < route.size() + 1; i++) {
            route.add(i, delivery.getOrder().getOffice());
            double nCost = routeCost2Opt(route);
            if (nCost < bCost) {
                bCost = nCost;
                bIndex = i;
            }
            route.remove(i);
        }
        // We add the delivery's vertex to its optimal position in the route
        route.add(bIndex, delivery.getOrder().getOffice());
        // We add the delivery to its optimal position in the array
        deliveries.add(bIndex - 1, delivery);

        twoOpt(route);

    }

    public boolean updateTravels(List<Vertex> route, TruckPlan truckPlan) {
        List<Vertex> path;
        List<Delivery> deliveries = truckPlan.getDeliveries();
        boolean success;

        Date currentDate = new Date(truckPlan.getSimulation().getClock().getTime());

        // If the truck is already at the depot
        for (Delivery delivery : deliveries)
            if (delivery.getOrder().getOffice() == truckPlan.getLocation() && !delivery.isCompleted()) {

                Date finalArrivalDate = truckPlan.getTravels().get(truckPlan.getTravels().size()-1).getDateArrival();

                if (finalArrivalDate.after(delivery.getOrder().getLimitDate()))
                    return false;

                Travel travel = new Travel(
                        new Date(finalArrivalDate.getTime()),
                        new Date(finalArrivalDate.getTime()),
                        null,
                        truckPlan.getLocation(),
                        truckPlan.getLocation(),
                        TravelType.DELIVERY,
                        delivery.getNumPackages());

                truckPlan.getTravels().add(travel);
                delivery.setDeliveryDate(new Date(finalArrivalDate.getTime()));
                delivery.update(truckPlan.getSimulation().getClock());
            }


        for (int i=0; i < route.size() - 1; i++) {

            Vertex startVertex = route.get(i);
            Vertex endVertex = route.get(i + 1);

            // If the truck must wait before the next order is made
            if (deliveries.get(i).getOrder().getOrderDate().after(currentDate))
                currentDate = new Date(deliveries.get(i).getOrder().getOrderDate().getTime());

            path = graph.AStar(startVertex, endVertex);
            success = insertTravels(path, currentDate, truckPlan);
            if(!success) return false;
        }
        return true;
    }

    private boolean insertTravels(List<Vertex> path,
                               Date currentDate,
                               TruckPlan truckPlan) {

        long hoursToMilliseconds = 3600000;
        List<Delivery> deliveries = truckPlan.getDeliveries();
        Date nextDate;
        Travel travel;

        for (int i = 0; i < path.size() - 1; i++) {

            // Get the edge from the path vertices
            Edge edge = graph.getEdgeOf2Vertex(path.get(i), path.get(i + 1));
            // Set the Truck's arrival time
            nextDate = new Date(Math.round((edge.getTime()) * hoursToMilliseconds) + currentDate.getTime());


            // If the truck reaches an office where a delivery needs to be made
            if (i == path.size() - 2) {

                int deliveredPackages = 0;

                for (Delivery delivery : deliveries) {
                    if (delivery.getOrder().getOffice() == path.get(i+1)) {
                        deliveredPackages += delivery.getNumPackages();
                    }
                }
                travel = new Travel(
                        new Date(currentDate.getTime()),
                        new Date (nextDate.getTime()),
                        edge,
                        path.get(i),
                        path.get(i + 1),
                        TravelType.DELIVERY,
                        deliveredPackages);
            }
            // Otherwise
            else {
                travel = new Travel(
                        new Date(currentDate.getTime()),
                        new Date (nextDate.getTime()),
                        edge,
                        path.get(i),
                        path.get(i + 1),
                        TravelType.MOVEMENT);

            }
            currentDate.setTime(nextDate.getTime() + hoursToMilliseconds);


            // Update the deliveries' delivery date
            for (Delivery delivery : deliveries) {
                if (!delivery.isCompleted()) {
                    Vertex office = delivery.getOrder().getOffice();
                    if (path.get(i + 1).equals(office)) {

                        if (nextDate.after(delivery.getOrder().getLimitDate()))
                            return false;

                        delivery.setDeliveryDate(nextDate);
                        delivery.update(truckPlan.getSimulation().getClock());
                    }
                }
            }

            truckPlan.getTravels().add(travel);
        }
        return true;
    }

    public static double evaluateFitness(Particle particle) {
        double fitness = 0.0;
        for (TruckPlan truckPlan : particle.getTruckPlans()) {
            if (!truckPlan.getDeliveries().isEmpty()) {
                for (Delivery delivery : truckPlan.getDeliveries())
                    if (delivery.isOnTime() && !delivery.isDelivered()) fitness += delivery.getNumPackages();
            }
        }
        return fitness;
    }

    public void updateVelocity(Particle particle, List<Double> best) {
        // First we clone the velocities, positions, personal and neighbourhood best
        List<Double> velocities = particle.velocity;
        List<Double> personalBest = particle.personalBest;
        List<Double> positions = particle.position;
        List<Double> bestNeigh;
        bestNeigh = best;

        // Initialize velocity elements
        List<Double> inertiaTerm = new ArrayList<>();
        List<Double> difference1 = new ArrayList<>();
        List<Double> difference2 = new ArrayList<>();
        List<Double> c1TimesR1 = new ArrayList<>();
        List<Double> c2TimesR2 = new ArrayList<>();

        ArrayList<Double> cognitiveTerm = new ArrayList<>();
        ArrayList<Double> socialTerm = new ArrayList<>();

        // Calculate inertia component
        for (Double vel : velocities) {
            inertiaTerm.add(w * vel);
        }

        // We clear the particle's old velocity
        particle.velocity.clear();

        // Calculate personal best - current position
        for (int i = 0; i < positions.size(); i++) {

            // Calculate personal best - current position
            difference1.add(personalBest.get(i) - positions.get(i));

            // Calculate c1*r1
            c1TimesR1.add(c1 * Math.random());

            // Calculate c1*r1*diff = cognitive term
            cognitiveTerm.add(c1TimesR1.get(i) * difference1.get(i));

            // Calculate neighbourhood best - current position
            difference2.add(bestNeigh.get(i) - positions.get(i));

            // Calculate c2*r2
            c2TimesR2.add(c2 * Math.random());

            // Calculate c2*r2*diff2 = social component
            socialTerm.add(c2TimesR2.get(i) - difference2.get(i));

            double newVelocity = inertiaTerm.get(i) + cognitiveTerm.get(i) + socialTerm.get(i);
            if (newVelocity > particle.velocityUpperBound)
                newVelocity = particle.velocityUpperBound;
            if (newVelocity < particle.velocityLowerBound)
                newVelocity = particle.velocityLowerBound;
            // Update particles velocity at all dimensions
            particle.velocity.add(newVelocity);
        }
    }

    public void updatePosition(Particle particle) {

        for (int i = 0; i < particle.position.size(); i++) {
            particle.position.set(i, particle.position.get(i) + particle.velocity.get(i));
            // particle.factors.set(i, particle.factors.get(i));
        }
    }

    public List<Double> findBest(List<Particle> particles) {
        List<Double> best = null;
        double bestFitness = Double.NEGATIVE_INFINITY;
        for (Particle particle : particles) {
            if (evaluateFitness(particle) >= bestFitness) {
                bestFitness = evaluateFitness(particle);
                best = particle.personalBest;
            }
        }
        return best;
    }
}
