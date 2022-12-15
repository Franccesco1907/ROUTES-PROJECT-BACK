package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TruckPlanOut implements Serializable {

    private long idTruckPlan;
    private long idSimulation;
    private int run;
    private long idTruck;
    private String status;
    private boolean travelling;
    private int numPackages;
    private int unassignedPackages;
    private long idCurrentVertex;
    private List<TravelOut> travels = new ArrayList<>();
    private List<DeliveryOut> deliveries = new ArrayList<>();

    public TruckPlanOut(TruckPlan truckPlan) {
        this.idTruckPlan = truckPlan.getId();
        this.idSimulation = truckPlan.getSimulation().getId();
        this.run = truckPlan.getRun();
        this.idTruck = truckPlan.getTruck().getId();
        this.status = truckPlan.getStatus().toString();
        this.travelling = truckPlan.isTravelling();
        this.numPackages = truckPlan.getNumPackages();
        this.unassignedPackages = truckPlan.getUnassignedPackages();
        this.idCurrentVertex = truckPlan.getLocation().getId();
        for (Travel travel : truckPlan.getTravels())
            this.travels.add(new TravelOut(travel));
        for (Delivery delivery : truckPlan.getDeliveries())
            this.deliveries.add(new DeliveryOut(delivery));
    }

}
