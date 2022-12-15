package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.Setter;
import pe.edu.pucp.packrunner.utils.TruckDistance;

import java.util.*;

import static pe.edu.pucp.packrunner.utils.PrintMethods.*;

@Getter
@Setter
@SuppressWarnings("unused")
public class PSOABCExpNum {
//    public final int numParticles = 6; // Number of particles in swarm
//    public final int maxDimensions; // Max number of iterations
//    public final int maxIterations = 30; // Max number of iterations
//    public final int maxIterationsPSO = 15; // Max number of iterations
//    public final int maxIterationsABC = 15; // Max number of iterations
//    public final double c1 = 1.496180; // Cognitive coefficient
//    public final double c2 = 1.496180; // Social coefficient
//    public final double w = 0.729844; // Inertia coefficient
//    public Graph graph;
//    public ArrayList<Order> lOrder;
//    public ArrayList<Truck> lTruck;
//    ArrayList<Double> best;
//    ArrayList<Double> bestFactors;
//    ArrayList<Double> r1;
//    ArrayList<Double> r2;
//    double[] tiempos;
//    double[] promParticles;
//    double[] distancias;
//
//    public PSOABCExpNum(Graph map, ArrayList<Order> lOrder, ArrayList<Truck> lTruck,
//            int numParticles, int maxIterations, int maxIterationsPSO,
//            int maxIterationsABC, double c1, double c2, double w,
//            int limitTries, int nEmployees, int nOnlookers) {
//        // psoAbc Algorithm
//        this.maxDimensions = lOrder.size();
//
//        PSOABC psoAbc = new PSOABC(map, lTruck, lOrder, numParticles, maxIterations,
//                maxIterationsPSO, maxIterationsABC, c1, c2, w, limitTries,
//                nEmployees, nOnlookers);
//
//        // Initialize Particles
//        psoAbc.initParticles(lOrder);
//        Particle[] particles = psoAbc.getParticles();
//        best = new ArrayList<>(Collections.nCopies(psoAbc.getNumDimensions(), 0.0));
//        bestFactors = new ArrayList<>(Collections.nCopies(psoAbc.getNumDimensions(), 0.0));
//        // Matriz de factores que guardara los mejores factores de cada particula
//        double[][] bestFactorsMat = new double[numParticles][];
//        for (int i = 0; i < numParticles; i++) {
//            bestFactorsMat[i] = new double[lOrder.size()];
//        }
//        tiempos = new double[maxIterations];
//        promParticles = new double[maxIterations];
//        tiempos = new double[maxIterations];
//        distancias = new double[maxIterations];
//        long start2 = System.currentTimeMillis();
//        // Algorithm loop
//        // PSO Loop
//        int numIter = 0;
//        while (numIter < maxIterations) {
//            // PSO-phase
//            long inicio = System.currentTimeMillis();
//            double distance = 0.0;
//            for (int iterPSO = 0; iterPSO < maxIterationsPSO; iterPSO++) {
//                System.out.println("ITERATION " + (iterPSO + 1));
//                printLine(80, "=");
//                for (int i = 0; i < numParticles; i++) {
//                    List<Order> originalParticleOrder = new ArrayList<>(particles[i].orders);
//                    psoAbc.createCustomerOrderList(particles[i].orders, particles[i].factors);
//                    ArrayList<ArrayList<TruckDistance>> matrix = psoAbc.createPriorityMatrix(particles[i].orders,
//                            lTruck);
//                    ArrayList<TruckPlan> truckPlans = psoAbc.createRoutesForVehicles(particles[i].orders, matrix);
//
//                    ArrayList<DeliveryPlan> deliveryPlans = new ArrayList<>();
//
//                    // Create DeliveryPlans and TruckPlans
//                    distance += psoAbc.createPlans(map, deliveryPlans, truckPlans);
//                    particles[i].orders.clear();
//                    particles[i].orders.addAll(originalParticleOrder);
//                    psoAbc.updateFactors(particles[i].factors, deliveryPlans, particles[i].orders,
//                            particles[i].position);
//
//                    // Determine fitness for particle
//                    particles[i].fitness = psoAbc.evaluateFitness(particles[i].position);
//
//                    // Update personal best position and best factors of particle
//                    if (particles[i].fitness <= psoAbc.evaluateFitness(particles[i].personalBest)) {
//                        particles[i].personalBest = particles[i].position;
//                        for (int j = 0; j < particles[i].personalBest.size(); j++) {
//                            bestFactorsMat[i][j] = particles[i].factors.get(j);
//                        }
//                    }
//
//                    //printParticle(i, particles);
//
//                    // Reset Order and Truck capacities and unassigned packages
//                    for (Order order : lOrder)
//                        order.setUnassignedPackages(order.getNumPackages());
//                    for (Truck truck : lTruck)
//                        truck.setCurrentCapacity(truck.getMaxCapacity());
//                }
//
//                // Evaluate fitness of each particle
//
//                // Find the best particle in set
//                best = psoAbc.findBest(particles);
//
//                // Initialize the random vectors for updates
//
//                // Update the velocity and position vectors
//                for (int i = 0; i < numParticles; i++) {
//                    psoAbc.updateVelocity(particles[i], best);
//                    psoAbc.updatePosition(particles[i]);
//                }
//
//            }
//
//            // ABC-phase
//            psoAbc.initialize(particles, bestFactorsMat);
//            for (int iterABC = 0; iterABC < maxIterationsABC; iterABC++) {
//
//                psoAbc.algorithm();
//
//                // Convierte el bestGlobal del ABC en el del pso
//                for (int i = 0; i < psoAbc.getNumDimensions(); i++) {
//                    best.set(i, psoAbc.getGBest().getNectar(i));
//                    bestFactors.set(i, psoAbc.getGBest().getFactors().get(i));
//                }
//
//                for (int i = 0; i < numParticles; i++) {
//                    System.out.printf("Nectar Positions Iter %d particle %d:\n", iterABC + 1, i + 1);
//                    System.out.printf("nectar ");
//                    for (int j = 0; j < psoAbc.getNumDimensions(); j++) {
//                        System.out.printf("%f ", psoAbc.getFoodSources().get(i).getNectar(j));
//                    }
//                    System.out.printf("\nfactor ");
//                    for (int j = 0; j < psoAbc.getNumDimensions(); j++) {
//                        System.out.printf("%f ", psoAbc.getFoodSources().get(i).getFactors().get(j));
//                    }
//                    System.out.printf("\nfitness " + psoAbc.getFoodSources().get(i).getFitness());
//                    System.out.println();
//                    System.out.println();
//
//                }
//
//            }
//            promParticles[numIter] += (double) psoAbc.getNSuccess()
//                    / (psoAbc.getNSuccess() + psoAbc.getNFailures());
//            psoAbc.setNFailures(0);
//            psoAbc.setNSuccess(0);
//            long fin = System.currentTimeMillis();
//            tiempos[numIter] = (double) ((fin - inicio));
//            distancias[numIter] = distance;
//            System.out.println("tiempo ejecución: " + tiempos[numIter]);
//            System.out.println("distancia recorrida: " + distancias[numIter]);
//            System.out.println("porcentaje de aciertos: " + promParticles[numIter]);
//            numIter++;
//        }
//
//        long fin2 = System.currentTimeMillis();
//        System.out.println("Tiempo total: " + (fin2 - start2));
//        double promedio = 0, promPorc = 0;
//        for (int i = 0; i < tiempos.length; i++) {
//            promPorc += promParticles[i];
//        }
//        promedio /= tiempos.length;
//        promPorc /= numIter;
//        double promedioDist = 0;
//        for (int i = 0; i < distancias.length; i++) {
//            promedioDist += distancias[i];
//        }
//        promedio = promedio / tiempos.length;
//        promedioDist = promedioDist / distancias.length;
//        System.out.println("Promedio porcentaje aciertos: " + promPorc);
//        System.out.println("Promedio distancia ejecución: " + promedioDist);
//        // Print the best solution
//        System.out.println("Best Fitness: " + psoAbc.evaluateFitness(best));
//    }
//
//    public void printParticle(int i, Particle[] particles) {
//        System.out.println("Particle " + (i + 1));
//        printLine(80, "-");
//        System.out.println("Particle Positions: " + particles[i].position);
//        System.out.println("Particle Factors: " + particles[i].factors);
//        System.out.println("Particle Velocities: " + particles[i].velocity);
//        System.out.println("Particle Fitness: " + particles[i].fitness);
//        System.out.println();
//    }

}
