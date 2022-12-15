package pe.edu.pucp.packrunner.models.algorithm;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.TruckPlan;

@NoArgsConstructor
@Getter
@Setter
public class Particle { // for each Truck
    public double positionLowerBound = 0;
    public double positionUpperBound = 1;
    public double velocityLowerBound = -6.0;
    public double velocityUpperBound = 6.0;

    
    public List<TruckPlan> truckPlans;
    public List<Double> position; // Order List ordered by time remaining
    public Double fitness; // The fitness of this particle
    public List<Double> velocity;
    public List<Double> personalBest; // Personal best of the particle

    public List<Double> nBest; // neighbor Best
    public List<Double> gBest; // global Best
    public List<Double> pBest; // personal Best

    public Particle(ArrayList<Double> position, ArrayList<Double> velocity, ArrayList<Double> factors) {
        this.position = position;
        this.velocity = velocity;
    }
}
