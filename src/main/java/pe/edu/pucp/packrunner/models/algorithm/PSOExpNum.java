package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.*;
import pe.edu.pucp.packrunner.utils.TruckDistance;

import java.util.*;

import static pe.edu.pucp.packrunner.utils.PrintMethods.*;

@Getter
@Setter
@SuppressWarnings("unused")
public class PSOExpNum {
  public final int numParticles = 10;   // Number of particles in swarm
  public final int maxIterations = 30;  // Max number of iterations
  public final double c1 = 1.496180;    // Cognitive coefficient
  public final double c2 = 1.496180;    // Social coefficient
  public final double w = 0.729844;     // Inertia coefficient

  public Graph graph;
  public List<Order> orders;
  public List<TruckPlan> truckPlans;
  List<Double> best = new ArrayList<>();
  List<Double> allTimeBest = new ArrayList<>();
  List<TruckPlan> bestTruckPlans;
  List<DeliveryPlan> bestDeliveryPlans;

  double[] times = new double[maxIterations];
  double[] successRates = new double[maxIterations];
  double[] distances = new double[maxIterations];

  public PSOExpNum(Graph graph, List<Order> orders, List<TruckPlan> truckPlans) {
  }
}
/*
  public PSOExpNum(Graph graph, List<Order> orders, List<TruckPlan> truckPlans) {

    long algorithmStart = System.currentTimeMillis();

    // PSO algorithm
    PSO PSO = new PSO(graph, numParticles, maxIterations, c1, c2, w);

    // Initialize particles
    PSO.initParticles(orders);
    List<Particle> particles = PSO.getParticles();

    // PSO loop
    for (int iteration = 0; iteration < maxIterations; iteration++) {

      long iterationStart = System.currentTimeMillis();
      double distance = 0.0;

      // Evaluate fitness of each particle
      for (Particle particle : particles) {

        // Orders to be assigned
        this.orders = new ArrayList<>(particle.orders);

        // Create Truck Priority Matrix and Sorted Customer List
        PSO.sortOrdersByPosition(particle, orders);
        List<List<TruckDistance>> matrix = PSO.createPriorityMatrix(particle.orders, truckPlans);

        // The particle's truck plans are modified as the new orders are assigned
        List<TruckPlan> particlePlans = PSO.AssignOrdersToTrucks(particle.orders, truckPlans, matrix);
        List<DeliveryPlan> deliveryPlans = new ArrayList<>();
        distance += PSO.updatePlans(graph, deliveryPlans, particlePlans);

        for (int i=0; i<particle.position.size(); i++)
          particle.position.set(i,
                  (double) ((orders.get(i).getLimitDate().getTime()
                          - orders.get(i).getDeliveryDate().getTime()) / 60000));

        particle.orders.clear();
        particle.orders.addAll(this.orders);

        // Determine fitness for particle
        particle.fitness = PSO.evaluateFitness(particle.position);

        // Update personal best position
        if (particle.fitness >= PSO.evaluateFitness(particle.personalBest)) {
          particle.personalBest = particle.position;
          particle.bestFactors = particle.factors;
          bestTruckPlans = particlePlans;
          bestDeliveryPlans = deliveryPlans;
        }

        // Reset Order and Truck capacities, unassigned packages and number of successes and failures
        for (Order order : orders) {
          order.setUnassignedPackages(order.getNumPackages());
          order.setDeliveryDate(null);
        }
        for (TruckPlan truckPlan : truckPlans)
          truckPlan.getTruck().setCurrentCapacity(truckPlan.getTruck().getMaxCapacity());

        // Set the success rate of the algorithm and reset the values
        successRates[iteration] += (double) PSO.getNSuccess() / (PSO.getNSuccess() + PSO.getNFailures());
        PSO.setNFailures(0);
        PSO.setNSuccess(0);

        printParticle(particle);
      }

      // Find the best particle in set
      best = PSO.findBest(particles);
      if(PSO.evaluateFitness(best) > PSO.evaluateFitness(allTimeBest))
        allTimeBest = best;

      // Update the velocity and position vectors
      for (Particle particle : particles) {
        PSO.updateVelocity(particle, best);
        PSO.updatePosition(particle);
      }

      long iterationEnd = System.currentTimeMillis();

      // Assign Iteration metrics
      times[iteration] = (double) ((iterationEnd - iterationStart))/1000;
      distances[iteration] = distance;
      successRates[iteration] /= numParticles;
      successRates[iteration] *= 100;

      // Print Iteration Metrics
      printIteration(iteration);
    }
    long algorithmEnd = System.currentTimeMillis();

    // Print Algorithm Results
    printAlgorithmResults(algorithmStart, algorithmEnd);
  }

  public void printAlgorithmResults(double algorithmStart, double algorithmEnd) {

    // Determine final runtime
    double algorithmRuntime = (algorithmEnd - algorithmStart)/1000;

    // Determine average success rate
    double avgSuccessRate = 0;
    for (double avgParticle : successRates) avgSuccessRate += avgParticle;
    avgSuccessRate /= successRates.length;

    // Determine average distance travelled
    double avgDistance = 0;
    for (double distance : distances) avgDistance += distance;
    avgDistance /= distances.length;

    System.out.printf("Total Runtime: %4.2f seconds\n", algorithmRuntime);
    System.out.println("Best Fitness: " + PSO.evaluateFitness(this.allTimeBest));
    System.out.printf("Success percent average: %4.4f %%\n", avgSuccessRate);
    System.out.printf("Runtime distance average: %4.2f km\n", avgDistance);
    System.out.println();
  }

  public void printParticle(Particle particle) {
    System.out.println("Particle");
    printLine(80, "-");
    System.out.println("Particle Positions: " + particle.position);
    System.out.println("Particle Factors: " + particle.factors);
    System.out.println("Particle Velocities: " + particle.velocity);
    System.out.println("Particle Fitness: " + particle.fitness);
    System.out.println();
  }

  public void printIteration(int iteration){
    System.out.printf("ITERATION %d\n", (iteration + 1));
    printLine(80, "=");
    System.out.printf("Runtime: %4.2f seconds\n", this.times[iteration]);
    System.out.printf("Iteration distance: %6.2f km\n", this.distances[iteration]);
    System.out.printf("Success rate: %4.4f %%\n", this.successRates[iteration]);
    System.out.printf("Best Fitness: %6.2f\n", PSO.evaluateFitness(this.best));
    printLine(80, "-");
  }
}

*/