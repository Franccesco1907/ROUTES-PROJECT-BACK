package pe.edu.pucp.packrunner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pe.edu.pucp.packrunner.models.algorithm.PSO;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;
import pe.edu.pucp.packrunner.models.enumerator.TravelType;
import pe.edu.pucp.packrunner.models.enumerator.TruckStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
@Table(name = "PR_Simulation")
@SQLDelete(sql = "UPDATE PR_Simulation SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Simulation extends BaseEntity {

    @Column(name = "speed")
    private int speed = 1;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "clock", columnDefinition = "DATETIME")
    private Date clock;

    @Column(name = "type")
    private SimulationType type;

    @Column(name = "running")
    private boolean running = true;



    public Simulation(SimulationType type) {
        this.type = type;
        this.clock = new Date();
        this.running = true;
    }

    public Simulation(SimulationType type, Date clock) {
        this.type = type;
        this.clock = clock;
    }

    public void update(Date newClock, List<TruckPlan> truckPlans) {

        // Update simulation Clock
        this.clock = newClock;

        // Update truck Plans
        if (!truckPlans.isEmpty())
            for (TruckPlan truckPlan : truckPlans) {
                truckPlan.update(newClock);
            }
    }

    public void stopSimulation() {
        this.running = false;
    }
}
