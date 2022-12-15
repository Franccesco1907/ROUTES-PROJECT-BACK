package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import pe.edu.pucp.packrunner.utils.TruckDistance;
import pe.edu.pucp.packrunner.utils.Utils;

import java.util.Random;
import java.util.Collections;
import java.util.*;

@Getter
@Setter
public class PSOABC {
//
//    // PSO Initialization Parameters
//    private int numDimensions = 31; // Number of particles in swarm
//    private int numParticles = 31; // Number of particles in swarm
//    private int maxAssessments = 30; // Max number of
//    private int niterPSO = 15;
//    private int niterABC = 15;
//    private double c1 = 1.496180; // Cognitive coefficient
//    private double c2 = 1.496180; // Social coefficient
//    private double w = 0.729844; // Inertia coefficient
//    private Graph map;
//    int nFailures = 0;
//    int nSuccess = 0;
//    double velocityLowerBound = 0.0;
//    double velocityUpperBound = 6.0;
//
//    private ArrayList<Double> F;
//
//    // ABC Initialization Parameters
//
//    private int nbees = 50;
//    private int K = 0;
//    // Particle dimensions 3*nOrder
//    // Lower Bound [1 | 1| 1]
//
//    // Upper Bound [nDepots | nTrucks | 10 ]
//
//    // Particle array [Depot Assignment | Truck Priority | Customers Priority]
//
//    private Particle[] particles;
//    private ArrayList<TruckPlan> truckPlans;
//
//    // Le metí fe, no confiar :'v
//    /* ABC PARAMETERS */
//    // public int numDimensions; /*The number of parameters of the problem to be
//    // optimized*/
//    private int NE; /* The number of total bees/colony size. employed + onlookers */
//    private int NO; /* The number of total bees/colony size. employed + onlookers */
//    // private int FOOD_NUMBER; /* The number of food sources equals the half of the
//    // colony size */
//    private int LIMIT; /*
//                        * A food source which could not be improved through "limit" trials is abandoned
//                        * by its employed bee
//                        */
//    // public int maxAssessments; /*The number of cycles for foraging {a stopping
//    // criteria}*/
//    private int MIN_SHUFFLE;
//    private int MAX_SHUFFLE;
//
//    private Random rand;
//    private ArrayList<Honey> foodSources;
//    private ArrayList<Honey> solutions;
//    private Honey gBest;
//    // private int epoch;
//    private ArrayList<Order> lOrder;
//    private ArrayList<Truck> lTruck;
//    private double distance = 0;
//
//    /*
//     * Instantiates the artificial bee colony algorithm along with its parameters.
//     *
//     * @param: size of n queens
//     */
//    public PSOABC(Graph map, List<Truck> lTruck, List<Order> lOrder, int numParticles, int maxAssessments,
//            int maxIterationsPSO, int maxIterationsABC, double c1, double c2, double w,
//            int limitTries, int nEmployees, int nOnlookers) {
//        this.numDimensions = lOrder.size();
//        this.niterPSO = maxIterationsPSO;
//        this.niterABC = maxIterationsABC;
//        this.maxAssessments = maxAssessments;
//        this.c1 = c1;
//        this.c2 = c2;
//        this.w = w;
//        this.map = map;
//        this.NE = nEmployees;
//        this.NO = nOnlookers;
//        this.numParticles = numParticles;
//        this.LIMIT = limitTries;
//        MIN_SHUFFLE = 8;
//        MAX_SHUFFLE = 20;
//        gBest = new Honey();
//        this.particles = new Particle[numParticles];
//        // epoch = 0;
//        foodSources = new ArrayList<>();
//        solutions = new ArrayList<>();
//        this.lOrder = new ArrayList<>(lOrder);
//        this.lTruck = new ArrayList<>(lTruck);
//    }
//
//    /*
//     * Starts the particle swarm optimization algorithm solving for n queens.
//     *
//     */
//    public void initParticles(ArrayList<Order> lOrder) {
//        // For each particle
//        Particle p = new Particle();
//
//        for (int i = 0; i < particles.length; i++) {
//            ArrayList<Double> positions = new ArrayList<>();
//            ArrayList<Double> velocities = new ArrayList<>();
//            ArrayList<Double> factors = new ArrayList<>();
//
//            double minPosition = p.factorLowerBound;
//            double maxPosition = p.factorUpperBound;
//
//            // For each dimension of the particle assign a random x value [-5.12,5.12] and
//            for (Order o : lOrder) {
//                // Esta operación genera un error, la primera iteración siempre será 0
//                // Long diff = Math.abs(o.getCreationDate().getTime() - new Date().getTime());
//                // TimeUnit time = TimeUnit.MINUTES;
//                // Double timeRemaining = (double) (time.convert(diff, TimeUnit.MILLISECONDS));
//                double position;
//                if (o.getOffice().getProvince().getNaturalRegion() == NaturalRegion.COSTA)
//                    position = 24 * 60 * 1;
//                else if (o.getOffice().getProvince().getNaturalRegion() == NaturalRegion.SIERRA)
//                    position = 24 * 60 * 2;
//                else
//                    position = 24 * 60 * 3;
//                double velocity = 0.0;
//                // double velocity = ((Math.random() * (maxVelocity - minVelocity)) +
//                // minVelocity);
//                double factor = ((Math.random() * (maxPosition - minPosition)) + minPosition);
//                positions.add(position);
//                velocities.add(velocity);
//                factors.add(factor);
//            }
//            // Create the particle
//            particles[i] = new Particle(positions, velocities, factors);
//            // Set particles personal best to initialized values
//            particles[i].personalBest = particles[i].position;
//            // asignar ordenes
//            particles[i].orders = lOrder;
//        }
//    }
//
//    public void algorithm() {
//        this.rand = new Random();
//
//        memorizeBestFoodSource();
//        sendEmployedBees();
//        // getFitness();
//        calculateProbabilities();
//        sendOnlookerBees();
//        memorizeBestFoodSource();
//        sendScoutBees();
//    }
//
//    /*
//     * Sends the employed bees to optimize the solution
//     *
//     */
//    public void sendEmployedBees() {
//        int neighborBeeIndex;
//        Honey currentBee;
//        Honey neighborBee;
//
//        for (int i = 0; i < numParticles; i++) {
//            // A randomly chosen solution is used in producing a mutant solution of the
//            // solution i
//            // neighborBee = getRandomNumber(0, Food_Number-1);
//            for (int j = 0; j < NE; j++) {
//                neighborBeeIndex = getExclusiveRandomNumber(numParticles - 1, i);
//                currentBee = foodSources.get(i);
//                neighborBee = foodSources.get(neighborBeeIndex);
//                sendToWork(currentBee, neighborBee);
//            }
//
//        }
//    }
//
//    /*
//     * Sends the onlooker bees to optimize the solution. Onlooker bees work on the
//     * best solutions from the employed bees. best solutions have high selection
//     * probability.
//     *
//     */
//    public void sendOnlookerBees() {
//        int i = 0;
//        int t = 0;
//        int neighborBeeIndex = 0;
//        Honey currentBee = null;
//        Honey neighborBee = null;
//
//        while (t < numParticles) {
//            currentBee = foodSources.get(i);
//            if (rand.nextDouble() < currentBee.getSelectionProbability()) {
//                t++;
//                neighborBeeIndex = getExclusiveRandomNumber(numParticles - 1, i);
//                neighborBee = foodSources.get(neighborBeeIndex);
//                sendToWork(currentBee, neighborBee);
//            }
//            i++;
//            if (i == numParticles) {
//                i = 0;
//            }
//        }
//    }
//
//    /*
//     * The optimization part of the algorithm. improves the currentbee by choosing a
//     * random neighbor bee. the changes is a randomly generated number of times to
//     * try and improve the current solution.
//     *
//     * @param: the currently selected bee
//     *
//     * @param: a randomly selected neighbor bee
//     *
//     * @param: the number of times to try and improve the solution
//     */
//    public void sendToWork(Honey currentBee, Honey neighborBee) {
//        double newValue = 0;
//        double tempValue = 0;
//        int tempIndex = 0;
//        int parameterToChange = 0;
//
//        // get number of conflicts
//        // prevConflicts = currentBee.getConflicts();
//
//        // The parameter to be changed is determined randomly
//        parameterToChange = getRandomNumber(0, numDimensions - 1);
//
//        /*
//         * v_{ij}=x_{ij}+\phi_{ij}*(x_{kj}-x_{ij})
//         * solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[
//         * neighbour][param2change])*(r-0.5)*2;
//         */
//        newValue = rand.nextDouble();
//        // trap the value within upper bound and lower bound limits
//        if (newValue > velocityUpperBound) {
//            newValue = velocityUpperBound;
//        }
//        if (newValue < velocityLowerBound) {
//            newValue = velocityLowerBound;
//        }
//
//        // get the index of the new value
//        // tempIndex = currentBee.getIndex(newValue);
//
//        // compara el viejo fitness no el candidato
//        double[] oldNectar = new double[numDimensions];
//        for (int i = 0; i < numDimensions; i++) {
//            oldNectar[i] = currentBee.getNectar()[i];
//        }
//        oldNectar[parameterToChange] -= newValue;
//        double newFitness = evaluateFitness(oldNectar);
//        // Analiza el resultado del posible cambio
//
//        if (newFitness > currentBee.getFitness()) {
//            currentBee.setNectar(parameterToChange, newValue);
//            currentBee.setFitness(newFitness);
//
//            for (int i = 0; i < numParticles; i++) {
//                List<Order> originalParticleOrder = new ArrayList<>(lOrder);
//                createCustomerOrderList(lOrder, foodSources.get(i).getFactors());
//                ArrayList<ArrayList<TruckDistance>> matrix = createPriorityMatrix(lOrder, lTruck);
//                ArrayList<TruckPlan> truckPlans = createRoutesForVehicles(lOrder, matrix);/*
//                                                                                           * promParticles[numIter] +=
//                                                                                           * (double) getNSuccess() /
//                                                                                           * (getNSuccess() +
//                                                                                           * getNFailures());
//                                                                                           * setNFailures(0);
//                                                                                           * setNSuccess(0);
//                                                                                           */
//                ArrayList<DeliveryPlan> deliveryPlans = new ArrayList<>();
//
//                // create DeliveryPlans and TruckPlan
//                createPlans(map, deliveryPlans, truckPlans);
//                lOrder = new ArrayList<>(originalParticleOrder);
//                ArrayList<Double> aux = new ArrayList<>();
//                for (int j = 0; j < numDimensions; j++) {
//                    aux.add(foodSources.get(i).getNectar(j));
//                }
//                updateFactors(foodSources.get(i).getFactors(), deliveryPlans, lOrder, aux);
//                for (int j = 0; j < numDimensions; j++) {
//                    foodSources.get(i).getNectar()[j] = aux.get(j);
//                }
//
//                foodSources.get(i).setFitness(evaluateFitness(foodSources.get(i).getNectar()));
//
//                for (Order order : lOrder)
//                    order.setUnassignedPackages(order.getNumPackages());
//                for (Truck truck : lTruck)
//                    truck.setCurrentCapacity(truck.getMaxCapacity());
//            }
//        }
//
//        // currentBee.setNectar(tempIndex, tempValue);
//        // currentBee.computeConflicts();
//        // currConflicts = currentBee.getConflicts();
//
//        // greedy selection
//        /*
//         * if (prevConflicts < currConflicts) { // No improvement
//         * currentBee.setNectar(parameterToChange, tempValue);
//         * currentBee.setNectar(tempIndex, newValue);
//         * currentBee.computeConflicts();
//         * currentBee.setTrials(currentBee.getTrials() + 1);
//         * } else { // improved solution
//         * currentBee.setTrials(0);
//         * }
//         */
//    }
//
//    /*
//     * Finds food sources which have been abandoned/reached the limit.
//     * Scout bees will generate a totally random solution from the existing and it
//     * will also reset its trials back to zero.
//     *
//     */
//    public void sendScoutBees() {
//        Honey currentBee = null;
//        int shuffles = 0;
//
//        for (int i = 0; i < numParticles; i++) {
//            currentBee = foodSources.get(i);
//            if (currentBee.getTrials() >= LIMIT) {
//                shuffles = getRandomNumber(MIN_SHUFFLE, MAX_SHUFFLE);
//                for (int j = 0; j < shuffles; j++) {
//                    randomlyArrange(i);
//                }
//                for (int k = 0; k < numParticles; k++) {
//                    List<Order> originalParticleOrder = new ArrayList<>(lOrder);
//                    createCustomerOrderList(lOrder, foodSources.get(k).getFactors());
//                    ArrayList<ArrayList<TruckDistance>> matrix = createPriorityMatrix(lOrder, lTruck);
//                    ArrayList<TruckPlan> truckPlans = createRoutesForVehicles(lOrder, matrix);/*
//                                                                                               * promParticles[numIter]
//                                                                                               * += (double)
//                                                                                               * getNSuccess() /
//                                                                                               * (getNSuccess() +
//                                                                                               * getNFailures());
//                                                                                               * setNFailures(0);
//                                                                                               * setNSuccess(0);
//                                                                                               */
//                    ArrayList<DeliveryPlan> deliveryPlans = new ArrayList<>();
//
//                    // create DeliveryPlans and TruckPlan
//                    createPlans(map, deliveryPlans, truckPlans);
//                    lOrder = new ArrayList<>(originalParticleOrder);
//                    ArrayList<Double> aux = new ArrayList<>();
//                    for (int j = 0; j < numDimensions; j++) {
//                        aux.add(foodSources.get(k).getNectar(j));
//                    }
//                    updateFactors(foodSources.get(k).getFactors(), deliveryPlans, lOrder, aux);
//                    for (int j = 0; j < numDimensions; j++) {
//                        foodSources.get(k).getNectar()[j] = aux.get(j);
//                    }
//                }
//                // currentBee.computeConflicts();
//                currentBee.setTrials(0);
//
//            }
//        }
//    }
//
//    /*
//     * Sets the fitness of each solution based on its conflicts
//     *
//     */
//    public void getFitness() {
//        // Lowest errors = 100%, Highest errors = 0%
//        Honey thisFood = null;
//        double bestScore = 0.0;
//        double worstScore = 0.0;
//
//        // The worst score would be the one with the highest energy, best would be
//        // lowest.
//        worstScore = Collections.max(foodSources).getConflicts();
//
//        // Convert to a weighted percentage.
//        bestScore = worstScore - Collections.min(foodSources).getConflicts();
//
//        for (int i = 0; i < numParticles; i++) {
//            thisFood = foodSources.get(i);
//            thisFood.setFitness((worstScore - thisFood.getConflicts()) * 100.0 / bestScore);
//        }
//    }
//
//    /*
//     * Sets the selection probability of each solution. the higher the fitness the
//     * greater the probability
//     *
//     */
//    public void calculateProbabilities() {
//        double totalFitness = 0;
//        for (int i = 0; i < numParticles; i++) {
//            totalFitness += foodSources.get(i).getFitness();
//        }
//        for (int i = 0; i < numParticles; i++) {
//            double probability = foodSources.get(i).getFitness() / totalFitness;
//            foodSources.get(i).setSelectionProbability(probability);
//        }
//    }
//
//    /*
//     * Initializes all of the solutions' placement of queens in ramdom positions.
//     *
//     */
//    public void initialize(Particle[] particles, double[][] bestFactorsMat) {
//
//        for (int i = 0; i < numParticles; i++) {
//            Honey newHoney = new Honey(numDimensions, particles[i].personalBest, bestFactorsMat[i]);
//            newHoney.setFitness(evaluateFitness(particles[i].personalBest));
//            foodSources.add(newHoney);
//        }
//    }
//
//    /*
//     * Gets a random number in the range of the parameters
//     *
//     * @param: the minimum random number
//     *
//     * @param: the maximum random number
//     *
//     * @return: random number
//     */
//    public int getRandomNumber(int low, int high) {
//        return (int) Math.round((high - low) * rand.nextDouble() + low);
//    }
//
//    /*
//     * Gets a random number with the exception of the parameter
//     *
//     * @param: the maximum random number
//     *
//     * @param: number to to be chosen
//     *
//     * @return: random number
//     */
//    public int getExclusiveRandomNumber(int high, int except) {
//        boolean done = false;
//        int getRand = 0;
//
//        while (!done) {
//            getRand = rand.nextInt(high);
//            if (getRand != except) {
//                done = true;
//            }
//        }
//
//        return getRand;
//    }
//
//    /*
//     * Changes a position of the queens in a particle by swapping a randomly
//     * selected position
//     *
//     * @param: index of the solution
//     */
//    public void randomlyArrange(int index) {
//        int positionA = getRandomNumber(0, numDimensions - 1);
//        int positionB = getExclusiveRandomNumber(numDimensions - 1, positionA);
//        Honey thisHoney = foodSources.get(index);
//        double temp = thisHoney.getNectar(positionA);
//        thisHoney.setNectar(positionA, thisHoney.getNectar(positionB));
//        thisHoney.setNectar(positionB, temp);
//    }
//
//    /*
//     * Memorizes the best solution
//     *
//     */
//    public void memorizeBestFoodSource() {
//        for (int i = 0; i < foodSources.size(); i++) {
//            Honey newHoney = foodSources.get(i);
//            double[] pBest = newHoney.getNectar();
//            double newFitness = evaluateFitness(pBest);
//            newHoney.setFitness(newFitness);
//            if (gBest.getNectar() == null) {
//                gBest.setNectar(Arrays.copyOf(pBest, pBest.length));
//                gBest.setFitness(newFitness);
//                gBest.setFactors(new ArrayList<>(newHoney.getFactors()));
//            } else {
//                if (newFitness < gBest.getFitness()) {
//                    gBest.setNectar(Arrays.copyOf(pBest, pBest.length));
//                    gBest.setFitness(newFitness);
//                    gBest.setFactors(new ArrayList<>(newHoney.getFactors()));
//                }
//            }
//        }
//
//    }
//
//    /*
//     * Prints the nxn board with the queens
//     *
//     * @param: a chromosome
//     */
//    public void printSolution(Honey solution) {
//        String board[][] = new String[numDimensions][numDimensions];
//
//        // Clear the board.
//        for (int x = 0; x < numDimensions; x++) {
//            for (int y = 0; y < numDimensions; y++) {
//                board[x][y] = "";
//            }
//        }
//
//        for (int x = 0; x < numDimensions; x++) {
//            board[x][(int) solution.getNectar(x)] = "Q";
//        }
//
//        // Display the board.
//        System.out.println("Board:");
//        for (int y = 0; y < numDimensions; y++) {
//            for (int x = 0; x < numDimensions; x++) {
//                if (board[x][y] == "Q") {
//                    System.out.print("Q ");
//                } else {
//                    System.out.print(". ");
//                }
//            }
//            System.out.print("\n");
//        }
//    }
//
//    /*
//     * gets the solutions
//     *
//     * @return: solutions
//     */
//    public ArrayList<Honey> getSolutions() {
//        return solutions;
//    }
//
//    /*
//     * gets the epoch
//     *
//     * @return: epoch
//     */
//
//    /*
//     * sets the max epoch
//     *
//     * @return: new max epoch value
//     */
//    public void setMaxEpoch(int newMaxEpoch) {
//        this.maxAssessments = newMaxEpoch;
//    }
//
//    /*
//     * gets the population size
//     *
//     * @return: pop size
//     */
//    public int getPopSize() {
//        return foodSources.size();
//    }
//
//    /*
//     * gets the start size
//     *
//     * @return: start size
//     */
//    public int getStartSize() {
//        return NO + NE;
//    }
//
//    public void ParticleConstraints() {
//
//    }
//
//    public ArrayList<Delivery> generateTruckPlan(ArrayList<Order> orders,
//            ArrayList<Truck> trucks,
//            Graph graph) {
//        // A particle is an array of TruckPlans
//        ArrayList<Delivery> deliveries = new ArrayList<>();
//        int i, k;
//
//        int truckSize = trucks.size();
//        int orderSize = orders.size();
//
//        int y[][] = new int[truckSize][orderSize];
//
//        for (Order order : orders) {
//            // Se determina cuantos paquetes faltan asignar en pedidos
//            int remainingPackages = order.getNumPackages();
//            // Mientras no se hallan asignado todos los paquetes
//            while (remainingPackages > 0) {
//                // Se le asigna el pedido según la partícula y
//                k = new Random().nextInt(trucks.size() - 1) + 1;
//
//                // Si el camión seleccionado está lleno, se elimina del pool de camiones
//                // posibles
//                if (trucks.get(k).getCurrentCapacity() == 0) {
//                    trucks.remove(k);
//                    continue;
//                }
//                // Se asigna el camión a la entrega
//                Truck assignedTruck = trucks.get(k);
//
//                // Se crea una nueva entrega
//                Delivery delivery = new Delivery();
//                delivery.setOrder(order);
//
//                // Si el camión puede contener el resto de paquetes
//                if (assignedTruck.getCurrentCapacity() >= remainingPackages) {
//                    delivery.setNumPackages(order.getNumPackages());
//                    assignedTruck.setCurrentCapacity(assignedTruck.getMaxCapacity() - delivery.getNumPackages());
//                }
//                // Si le falta espacio al camión
//                else {
//                    delivery.setNumPackages(assignedTruck.getCurrentCapacity());
//                    assignedTruck.setCurrentCapacity(0);
//                }
//                deliveries.add(delivery);
//                remainingPackages -= delivery.getNumPackages();
//            }
//        }
//
//        // Generate TruckPlan
//        return deliveries;
//    }
//
//    public void initTruckCost(ArrayList<Truck> trucks) {
//        this.K = trucks.size();
//        for (int i = 0; i < trucks.size(); i++) {
//            this.F.set(i, getRandomNumber2(0, 20));
//        }
//
//    }
//
//    public double getF1(TruckPlan truckPlan) {
//        double F1 = 0;
//        for (Travel travel : truckPlan.getTravels()) {
//            F1 += travel.getEdge().getDistance(); // Falta el x
//        }
//        return F1;
//    }
//
//    public double getMinF1() {
//        double minF1 = 999999999;
//        double F1;
//        for (TruckPlan truckPlan : truckPlans) {
//            F1 = getF1(truckPlan);
//            if (minF1 > F1) {
//                minF1 = F1;
//            }
//        }
//        return minF1;
//    }
//
//    public double getF2(TruckPlan truckPlan) {
//        double F2 = 0;
//        for (Travel travel : truckPlan.getTravels()) {
//            F2 += travel.getEdge().getTime(); // Falta el x
//        }
//        return F2;
//    }
//
//    public double getMinF2() {
//        double minF2 = 99999999;
//        double F2;
//        for (TruckPlan truckPlan : truckPlans) {
//            F2 = getF2(truckPlan);
//            if (minF2 > F2) {
//                minF2 = F2;
//            }
//        }
//        return minF2;
//    }
//
//    PSOABC(Graph graph, ArrayList<Truck> trucks, ArrayList<Order> lOrder, int numParticles, int maxAssessments,
//            int numDimensions, double c1, double c2, double w) {
//        this.numDimensions = numDimensions;
//        this.numParticles = numParticles;
//        this.maxAssessments = maxAssessments;
//        this.c1 = c1;
//        this.c2 = c2;
//        this.w = w;
//        initTruckCost(trucks);
//    }
//
//    private double getRandomNumber2(int min, int max) {
//        return ((Math.random() * (max - min)) + min);
//    }
//
//    private int asignDeliveriesRandom(Truck truck, List<Order> auxOrder, int currentCap) {
//        int indexOrder = (int) getRandomNumber(0, auxOrder.size() - 1);
//        Order chooseOrder = auxOrder.get(indexOrder);
//        Travel travel = new Travel();
//        int nOrderPack = chooseOrder.getNumPackages();
//        if (nOrderPack > currentCap) {
//            Delivery delivery = new Delivery(chooseOrder, nOrderPack - currentCap);
//            currentCap = 0;
//        } else {
//            Delivery delivery = new Delivery(chooseOrder, nOrderPack - currentCap);
//            currentCap -= nOrderPack;
//            auxOrder.remove(indexOrder);
//        }
//        return currentCap;
//    }
//
//    public TruckPlan createParticle(ArrayList<Order> lOrder, ArrayList<Truck> trucks) {
//        // For each particle
//        int indexTruck = (int) getRandomNumber(0, trucks.size() - 1);
//        Truck selectedTruck = trucks.get(indexTruck);
//        List<Order> auxOrder = new ArrayList<>(lOrder);
//        List<Travel> route = new ArrayList<>();
//        int currentCap = selectedTruck.getCurrentCapacity();
//        while (currentCap > 0) {
//            currentCap = asignDeliveriesRandom(selectedTruck, auxOrder, currentCap);
//
//        }
//
//        TruckPlan truckPlan = new TruckPlan();
//        return truckPlan;
//    }
//
//    /**
//     * Method to update the velocities vector of a particle
//     *
//     * @param particle The particle to update the velocity for
//     */
//
//    public void updateVelocity(Particle particle, double[] best, double[] r1, double[] r2) {
//        // First we clone the velocities, positions, personal and neighbourhood best
//        // double[] velocities = particle.velocity.clone();
//        // double[] personalBest = particle.personalBest.clone();
//        // Position[] positions = particle.position.clone();
//        double[] bestNeigh = best.clone();
//
//        double[] inertiaTerm = new double[numDimensions];
//        double[] difference1 = new double[numDimensions];
//        double[] difference2 = new double[numDimensions];
//
//        double[] c1Timesr1 = new double[numDimensions];
//        double[] c2Timesr2 = new double[numDimensions];
//
//        double[] cognitiveTerm = new double[numDimensions];
//        double[] socialTerm = new double[numDimensions];
//
//        // Calculate inertia component
//        for (int i = 0; i < numDimensions; i++) {
//            // inertiaTerm[i] = w*velocities[i];
//        }
//
//        // Calculate the cognitive component
//
//        // Calculate personal best - current position
//
//        for (int i = 0; i < numDimensions; i++) {
//            // difference1[i] = personalBest[i] - positions[i];
//        }
//
//        // Calculate c1*r1
//        for (int i = 0; i < numDimensions; i++) {
//            c1Timesr1[i] = c1 * r1[i];
//        }
//
//        // Calculate c1*r1*diff = cognitive term
//        for (int i = 0; i < numDimensions; i++) {
//            cognitiveTerm[i] = c1Timesr1[i] * difference1[i];
//        }
//
//        // Calculate the social term
//
//        // Calculate neighbourhood best - current position
//        for (int i = 0; i < numDimensions; i++) {
//            // difference2[i] = bestNeigh[i] - positions[i];
//        }
//
//        // Calculate c2*r2
//        for (int i = 0; i < numDimensions; i++) {
//            c2Timesr2[i] = c2 * r2[i];
//        }
//
//        // Calculate c2*r2*diff2 = social component
//        for (int i = 0; i < numDimensions; i++) {
//            socialTerm[i] = c2Timesr2[i] * difference2[i];
//        }
//
//        // Update particles velocity at all dimensions
//        for (int i = 0; i < numDimensions; i++) {
//            // particle.velocity[i] = inertiaTerm[i]+cognitiveTerm[i]+socialTerm[i];
//        }
//    }
//
//    public ArrayList<ArrayList<TruckDistance>> createPriorityMatrix(@NotNull ArrayList<Order> orders,
//            ArrayList<Truck> trucks) {
//        ArrayList<ArrayList<TruckDistance>> matrix = new ArrayList<>();
//        for (Order o : orders) {
//            ArrayList<TruckDistance> lDistanceOrderPerTruck = new ArrayList<>();
//            for (Truck t : trucks) {
//                double distance = distHaversine(o.getOffice(), t.getStartingDepot());
//                TruckDistance truckDistance = new TruckDistance(t, distance);
//                lDistanceOrderPerTruck.add(truckDistance);
//            }
//            // ordenar ascendentemente la lista
//            Collections.sort(lDistanceOrderPerTruck, new Comparator<TruckDistance>() {
//                @Override
//                public int compare(TruckDistance a, TruckDistance b) {
//                    return (int) (a.getDistance() - b.getDistance());
//                }
//            });
//            // añadir a matriz
//            matrix.add(lDistanceOrderPerTruck);
//        }
//        return matrix;
//    }
//
//    public static double distHaversine(Vertex v1, Vertex v2) {
//        int R = 6371;
//        double lat1, lat2, lon1, lon2;
//        double latDist, lonDist;
//        double a, c;
//
//        lat1 = v1.getLatitude();
//        lon1 = v1.getLongitude();
//        lat2 = v2.getLatitude();
//        lon2 = v2.getLongitude();
//
//        latDist = toRad(lat2 - lat1);
//        lonDist = toRad(lon2 - lon1);
//
//        a = Math.sin(latDist / 2) * Math.sin(latDist / 2) +
//                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
//        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return R * c;
//    }
//
//    private static double toRad(double value) {
//        return value * Math.PI / 180;
//    }
//
//    /*
//     * public Depot nearestDepot(Office office, ArrayList<Depot> lDepot) {
//     * double dist, minDist = Integer.MAX_VALUE;
//     * Depot selectedDepot = null;
//     *
//     * for (Depot depot : lDepot) {
//     * dist = Vertex.distHaversine((Vertex) office, depot);
//     * if (dist < minDist) {
//     * minDist = dist;
//     * selectedDepot = depot;
//     * }
//     * }
//     * return selectedDepot;
//     * }
//     */
//
//    /*
//     * public long nearestOrder(Office office, ArrayList<Depot> lDepot) {
//     * double dist, minDist = Integer.MAX_VALUE;
//     * long idDepot = -1;
//     *
//     * for (Depot depot : lDepot) {
//     * dist = Vertex.distHaversine((Vertex) office, depot);
//     * if (dist < minDist) {
//     * minDist = dist;
//     * idDepot = depot.getId();
//     * }
//     * }
//     * return idDepot;
//     * }
//     */
//
//    /*
//     * private double getRandomNumber(int min, int max) {
//     * return ((Math.random() * (max - min)) + min);
//     * }
//     */
//
//    /*
//     * HashMap groupOrdersToDepots(ArrayList<Order> lOrder, ArrayList<Depot> lDepot)
//     * {
//     * Depot selectedDepot;
//     *
//     * HashMap<Depot, ArrayList<Order>> depotGroup = new HashMap<>();
//     * for (Depot depot : lDepot) {
//     * depotGroup.put(depot, new ArrayList<Order>());
//     * }
//     * for (Order order : lOrder) {
//     * selectedDepot = nearestDepot(order.getOffice(), lDepot);
//     * ((ArrayList<Order>) depotGroup.get(selectedDepot.getId())).add(order);
//     * }
//     * return depotGroup;
//     * }
//     */
//
//    /*
//     * private void ClarkeWrightMatrix(ArrayList<ArrayList<Order>> groupedOrders,
//     * Depot depot, ArrayList<Order> lOrder) {
//     *
//     * ClarkeWright matrix = new ClarkeWright(depot, lOrder);
//     * matrix.chooseBestSaves();
//     * ArrayList<Order> orderGroup = matrix.getOrderChoose();
//     * groupedOrders.add(orderGroup);
//     * }
//     */
//
//    /*
//     * private ArrayList<ArrayList<Order>> groupOrders(HashMap depotDict) {
//     * ArrayList<ArrayList<Order>> groupedOrders = new ArrayList<>();
//     *
//     * for (int i = 0; i < depotDict.size(); i++) {
//     * Depot depot = (Depot) depotDict.keySet().toArray()[i];
//     * ArrayList<Order> lOrder = (ArrayList<Order>) depotDict.get(depot);
//     * // Creara subgrupos de los grupos de los depositos
//     * ClarkeWrightMatrix(groupedOrders, depot, lOrder);
//     * }
//     *
//     * return groupedOrders;
//     * }
//     */
//
//    private double getPriority(Order order) {
//        long DAYTOMILISEG = 86400000;
//        String destiny = order.getOffice().getProvince().getNaturalRegion().toString();
//        double priority;
//        double[] weight;// LIMA, AREQUIPA, TRUJILLO
//        int days = 0;
//
//        if (destiny.equals("COSTA")) {
//            days = 1;
//            // weight = new double[] { 0.8, 0.18, 0.02 };
//            // dist=distHaversiana(COSTA,order.getOffice());
//        }
//        if (destiny.equals("SIERRA")) {
//            days = 2;
//            // weight = new double[] { 0.8, 0.18, 0.02 };
//        }
//        if (destiny.equals("SELVA")) {
//            days = 3;
//        }
//
//        long maxMiliseg = days * DAYTOMILISEG;
//        Date dateOfOrder = order.getOrderDate();
//        Date currentDate = new Date();
//        long passedMiliseg = currentDate.getTime() - dateOfOrder.getTime();
//        priority = (1 - (double) (passedMiliseg * 100) / maxMiliseg);
//
//        return priority;
//    }
//
//    public void createCustomerOrderList(ArrayList<Order> orders, ArrayList<Double> factors) {
//        int i = 0;
//
//        for (Order order : orders) {
//            order.setPriority(factors.get(i));
//            i++;
//        }
//        orders.sort(Order.PriorityComparator);
//        // MergeSort.mergeSortOrder(orders, lPriorityValue, 0, orders.size() - 1);
//    }
//
//    private void insertDelivery(ArrayList<Vertex> vertexRoute, Delivery delivery, ArrayList<Delivery> deliveries) {
//        int bIndex = 0;
//        double bCost = Double.MAX_VALUE;
//        // Revisa si colocarlo despues del depot o hasta despues del delivery final
//        for (int i = 1; i < vertexRoute.size() + 1; i++) {
//            vertexRoute.add(i, delivery.getOrder().getOffice());
//            double nCost = Utils.routeCost2Opt(vertexRoute);
//            if (nCost < bCost) {
//                bCost = nCost;
//                bIndex = i;
//            }
//            vertexRoute.remove(i);
//        }
//        vertexRoute.add(bIndex, delivery.getOrder().getOffice());
//        deliveries.add(bIndex - 1, delivery);
//        Utils.twoOpt(vertexRoute, deliveries);
//    }
//
//    private boolean tryInsertCostumer(Order order,
//            TruckPlan truckPlan) {
//
//        ArrayList<Delivery> deliveries = truckPlan.getDeliveries();
//        Truck truck = truckPlan.getTruck();
//
//        int currentCapacity = truck.getCurrentCapacity();
//        int unassignedPackages = order.getUnassignedPackages();
//
//        // Si el camion no tiene capacidad no tiene sentido insertar paquetes
//        if (currentCapacity == 0)
//            return false;
//        // Se copian los vertices de los deliverys + el vertice depot
//        ArrayList<Vertex> vertexRoute = new ArrayList<>();
//        vertexRoute.add(truck.getStartingDepot());
//        for (Delivery delivery : deliveries)
//            vertexRoute.add(delivery.getOrder().getOffice());
//        // Se actualizan los contadores de paquetes sin asignar de las ordenes y de
//        // capacidad restante de camion
//        Delivery delivery;
//
//        // Si toda la orden entra en un solo camion
//        if (unassignedPackages <= currentCapacity) {
//            delivery = new Delivery(order, unassignedPackages);
//
//            order.setUnassignedPackages(0);
//            truck.setCurrentCapacity(currentCapacity - unassignedPackages);
//        }
//        // Si la orden se debe partir en dos
//        else {
//            delivery = new Delivery(order, currentCapacity);
//
//            order.setUnassignedPackages(unassignedPackages - currentCapacity);
//            truck.setCurrentCapacity(0);
//        }
//        insertDelivery(vertexRoute, delivery, deliveries);
//        return true;
//    }
//
//    private void initializeAuxObjects(ArrayList<TruckPlan> truckPlans,
//            ArrayList<ArrayList<TruckDistance>> matTruck) {
//
//        // Diccionario de Entregas
//        for (TruckDistance truckDist : matTruck.get(0)) {
//            Truck truck = truckDist.getTruck();
//
//            TruckPlan truckPlan = new TruckPlan();
//            truckPlan.setTruck(truck);
//            truckPlan.setDeliveries(new ArrayList<>());
//            truckPlans.add(truckPlan);
//        }
//    }
//
//    public ArrayList<TruckPlan> createRoutesForVehicles(ArrayList<Order> orders,
//            ArrayList<ArrayList<TruckDistance>> prioMatrix) {
//        ArrayList<TruckPlan> truckPlans = new ArrayList<>();
//
//        // LLenar estructuras
//        initializeAuxObjects(truckPlans, prioMatrix);
//
//        // Va iterando la prioridad de ordenes
//        for (int i = 0; i < orders.size(); i++) {
//            Order order = orders.get(i);
//            // Mientras la orden tenga paquetes sin asignar seguirá el loop
//            while (order.getUnassignedPackages() > 0) {
//                // Va iterando la prioridad de camiones
//                int j;
//                for (j = 0; j < prioMatrix.get(i).size(); j++) {
//                    Truck truck = prioMatrix.get(i).get(j).getTruck();
//                    String truckCode = truck.getCode();
//                    TruckPlan truckPlan = truckPlans.stream()
//                            .filter(tp -> truckCode.equals(tp.getTruck().getCode()))
//                            .findAny().orElse(null);
//                    // Indica si se pudo insertar la orden/delivery
//                    boolean success = tryInsertCostumer(order, truckPlan);
//                    if (success)
//                        break;
//                } // Si ningun camion pudo encontrar cabida
//                if (j == prioMatrix.get(i).size()) {
//                    nFailures++;
//                    break;
//                }
//                nSuccess++;
//            }
//        }
//        return truckPlans;
//    }
//
//    private List<Travel> createTravels(Graph map,
//            List<Vertex> path,
//            Date currentDate,
//            DeliveryPlan delPlan,
//            TruckPlan truckPlan) {
//        long HOURTOMILISEG = 3600000;
//        double velocity;// km/h
//        Date nextDate;
//
//        List<Travel> lTravels = new ArrayList<>();
//
//        for (int i = 0; i < path.size() - 1; i++) {
//
//            Edge edge = map.getEdgeOf2Vertex(path.get(i), path.get(i + 1));
//            nextDate = new Date(Math.round(edge.getTime() * HOURTOMILISEG) + currentDate.getTime());
//            Travel travel = new Travel(new Date(currentDate.getTime()), nextDate, delPlan, truckPlan, edge,
//                    path.get(i), path.get(i + 1));
//            // Se actualizan los tiempos
//            currentDate.setTime(nextDate.getTime());
//            lTravels.add(travel);
//        }
//        return lTravels;
//    }
//
//    public double createPlans(Graph graph,
//            List<DeliveryPlan> deliveryPlans, // vacio
//            ArrayList<TruckPlan> truckPlans) {// rutas
//        List<Vertex> path;
//        double distance = 0.0;
//        for (TruckPlan truckPlan : truckPlans) {
//            Truck truck = truckPlan.getTruck();
//            ArrayList<Delivery> deliveries = truckPlan.getDeliveries();
//            Date currDate = new Date();
//            if (!deliveries.isEmpty()) {
//                boolean depotTaken = false;
//                truckPlan.setTravels(new ArrayList<>());
//                for (int i = 0; i < deliveries.size() - 1; i++) {
//                    DeliveryPlan dPlan = new DeliveryPlan(deliveries.get(i), new ArrayList<>());
//                    Vertex startVertex = deliveries.get(i).getOrder().getOffice();
//                    Vertex endVertex = deliveries.get(i + 1).getOrder().getOffice();
//                    if (!depotTaken) {
//                        path = graph.AStar(truck.getStartingDepot(), startVertex, currDate);
//                        distance += getDistance(path);
//                        i--;
//                        depotTaken = true;
//                    } else
//                        path = graph.AStar(startVertex, endVertex, currDate);
//                    List<Travel> lTravels = createTravels(graph, path, currDate, dPlan, truckPlan);
//                    // Se agregan los planes al truckPlan
//                    truckPlan.getTravels().addAll(lTravels);
//                    // Se agregan los planes al deliveryPlan
//                    dPlan.getTravels().addAll(lTravels);
//                    deliveryPlans.add(dPlan);
//                }
//            } else
//                continue;
//
//        }
//        return distance;
//    }
//
//    private double getDistance(List<Vertex> path) {
//        double distance = 0.0;
//        for (int i = 0; i < path.size() - 1; i++)
//            distance += Vertex.distHaversine(path.get(i), path.get(i + 1));
//        return distance;
//    }
//
//    public double evaluateFitness(List<Double> positions) {
//        double fitness = 0.0;
//        // Para el tiempo en min
//        for (Double position : positions) {
//            fitness += 500 * Math.pow(1.1, -1 * position);
//        }
//        // fitness = 0;
//        return fitness;
//    }
//
//    public double evaluateFitness(double[] positions) {
//        double fitness = 0.0;
//        // Para el tiempo en min
//        for (int i = 0; i < positions.length; i++) {
//            fitness += 500 * Math.pow(1.1, -1 * positions[i]);
//        }
//        // fitness = 0;
//        return fitness;
//    }
//
//    /*
//     * public Position getParticleSolution(ArrayList<Order> lOrder,ArrayList<Truck>
//     * lTruck,HashMap depotDict){
//     * //Inicializar diccionario de camiones con sus visitas respectivas
//     * Dictionary dictTruck=new Hashtable();
//     * ArrayList<Long> lTruckId=new ArrayList<>();
//     * for(Truck truck:lTruck){
//     * dictTruck.put(truck.getId(),new ArrayList<Order>());
//     * lTruckId.add(truck.getId());
//     * }
//     * while(!lTruckId.isEmpty()){
//     * long selectedTruck=(long)getRandomNumber(0,lTruckId.size()-1);
//     * lTruckId.remove(selectedTruck);
//     * }
//     * //Se agrupan las ordenes del deposito a un camion
//     * for(Enumeration enm = depotDict.keys(); enm.hasMoreElements();){
//     * long idDep=(Long)enm.nextElement();
//     * //Se crea una copia de las ordenes del deposito
//     * ArrayList<Order> lOrderTemp=new ArrayList<>();
//     * lOrderTemp.addAll((ArrayList<Order>)depotDict.elements());
//     * //Se asignan las ordenes al camion en orden especifico
//     * while(!lOrderTemp.isEmpty()){
//     * long selectedOrder=(long)getRandomNumber(0,lOrderTemp.size()-1);
//     * lOrderTemp.remove(selectedOrder);
//     * dictTruck.get()
//     * }
//     *
//     * }
//     * }
//     */
//
//    public void updateVelocity(Particle particle, ArrayList<Double> best) {
//        // First we clone the velocities, positions, personal and neighbourhood best
//        ArrayList<Double> velocities = particle.velocity;
//        ArrayList<Double> personalBest = particle.personalBest;
//        ArrayList<Double> positions = particle.position;
//        ArrayList<Double> bestNeigh;
//        bestNeigh = best;
//
//        // Initialize velocity elements
//        ArrayList<Double> inertiaTerm = new ArrayList<>();
//        ArrayList<Double> difference1 = new ArrayList<>();
//        ArrayList<Double> difference2 = new ArrayList<>();
//        ArrayList<Double> c1Timesr1 = new ArrayList<>();
//        ArrayList<Double> c2Timesr2 = new ArrayList<>();
//
//        ArrayList<Double> cognitiveTerm = new ArrayList<>();
//        ArrayList<Double> socialTerm = new ArrayList<>();
//
//        // Calculate inertia component
//        for (Double vel : velocities) {
//            inertiaTerm.add(w * vel);
//        }
//
//        // We clear the particle's old velocity
//        particle.velocity.clear();
//
//        // Calculate personal best - current position
//        for (int i = 0; i < positions.size(); i++) {
//
//            // Calculate personal best - current position
//            difference1.add(personalBest.get(i) - positions.get(i));
//
//            // Calculate c1*r1
//            c1Timesr1.add(c1 * Math.random());
//
//            // Calculate c1*r1*diff = cognitive term
//            cognitiveTerm.add(c1Timesr1.get(i) * difference1.get(i));
//
//            // Calculate neighbourhood best - current position
//            difference2.add(bestNeigh.get(i) - positions.get(i));
//
//            // Calculate c2*r2
//            c2Timesr2.add(c2 * Math.random());
//
//            // Calculate c2*r2*diff2 = social component
//            socialTerm.add(c2Timesr2.get(i) - difference2.get(i));
//
//            double newVelocity = inertiaTerm.get(i) + cognitiveTerm.get(i) + socialTerm.get(i);
//            if (newVelocity > particle.velocityUpperBound)
//                newVelocity = particle.velocityUpperBound;
//            if (newVelocity < particle.velocityLowerBound)
//                newVelocity = particle.velocityLowerBound;
//            // Update particles velocity at all dimensions
//            particle.velocity.add(newVelocity);
//        }
//    }
//
//    public void updatePosition(Particle particle) {
//        // Since new position is ALWAYS calculated after calculating new velocity, it is
//        // okay to just add old position to the current velocity (as velocity would have
//        // already been updated).
//        // for (int i = 0; i < particle.position.size(); i++) {
//        // double newPosition = particle.position.get(i) + particle.velocity.get(i);
//        // if (newPosition > particle.positionUpperBound)
//        // newPosition = particle.velocityUpperBound;
//        // if (newPosition < particle.positionLowerBound)
//        // newPosition = particle.velocityLowerBound;
//        // particle.position.set(i, newPosition);
//        // }
//        for (int i = 0; i < particle.position.size(); i++) {
//            particle.position.set(i, particle.position.get(i) - particle.velocity.get(i));
//            particle.factors.set(i, particle.factors.get(i));
//        }
//    }
//
//    public ArrayList<Double> findBest(Particle[] particles) {
//        ArrayList<Double> best = null;
//        double bestFitness = Double.MAX_VALUE;
//        for (int i = 0; i < this.numParticles; i++) {
//            if (evaluateFitness(particles[i].personalBest) <= bestFitness) {
//                bestFitness = evaluateFitness(particles[i].personalBest);
//                best = particles[i].personalBest;
//            }
//        }
//        return best;
//    }
//
//    public void updateFactors(ArrayList<Double> factors, ArrayList<DeliveryPlan> deliveryPlans,
//            ArrayList<Order> lOrders, ArrayList<Double> position) {
//        for (DeliveryPlan delPlan : deliveryPlans) {
//            Order order = delPlan.getDelivery().getOrder();
//            for (int i = 0; i < lOrders.size(); i++) {
//                if (lOrders.get(i).equals(order)) {
//                    if (position.get(i) <= 60)
//                        factors.set(i, 0.99);
//                    else if (position.get(i) <= 60 * 2)
//                        factors.set(i, 0.95);
//                    else if (position.get(i) <= 60 * 3)
//                        factors.set(i, 0.90);
//                    else if (position.get(i) <= 60 * 4)
//                        factors.set(i, 0.85);
//                    else if (position.get(i) <= 60 * 5)
//                        factors.set(i, 0.80);
//                    else if (position.get(i) <= 60 * 6)
//                        factors.set(i, 0.75);
//                    else if (position.get(i) >= 60 * 24)
//                        factors.set(i, 0.45);
//                    break;
//                }
//            }
//
//        }
//
//    }

}
