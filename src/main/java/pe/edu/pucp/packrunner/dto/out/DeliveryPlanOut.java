package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Delivery;
import pe.edu.pucp.packrunner.models.Travel;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DeliveryPlanOut {
    private Long idDelivery;
    private Long idOrder;
    private int numPackages;
    private Date deliveryDate;
    private boolean onTime;
    private List<Travel> travels;


    public DeliveryPlanOut(Delivery delivery, TruckPlan truckPlan) {
        this.idDelivery = delivery.getId();
        this.idOrder = delivery.getOrder().getId();
        this.numPackages = delivery.getNumPackages();
        this.deliveryDate = delivery.getDeliveryDate();
        this.onTime = delivery.isOnTime();



    }
}
