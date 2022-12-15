package pe.edu.pucp.packrunner.dto.out;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Simulation;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class SimulationOut implements Serializable {

    private Long idSimulation;
    private Date clock;
    private int run;
    private boolean collapsed;
    private List<TruckPlanOut> truckPlans;
    private String report;

    public SimulationOut (List<TruckPlan> truckPlans) {
        if(!truckPlans.isEmpty()) {
            this.idSimulation = truckPlans.get(0).getSimulation().getId();
            this.run = truckPlans.get(0).getRun();
            this.clock = truckPlans.get(0).getSimulation().getClock();
            this.collapsed = false;
            this.truckPlans = new ArrayList<>();
            this.report = "";
            for (TruckPlan truckPlan : truckPlans) {

                this.report += truckPlan.toString() + "\n\n";

                for (Delivery delivery : truckPlan.getDeliveries()) {
                    if (!delivery.isOnTime()) {
                        this.collapsed = true;
                        break;
                    }
                }
                this.truckPlans.add(new TruckPlanOut(truckPlan));
            }
        }
        else {
            System.out.println("No truck Plans");
            this.truckPlans = new ArrayList<>();
        }
    }

}
