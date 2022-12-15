package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Delivery;

import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class DeliveryOut {

    private Long idDelivery;
    private Long idOrder;
    private int numPackages;
    private Date deliveryDate;
    private boolean onTime;

    public DeliveryOut(Delivery delivery) {
        this.idDelivery = delivery.getId();
        this.idOrder = delivery.getOrder().getId();
        this.numPackages = delivery.getNumPackages();
        this.deliveryDate = delivery.getDeliveryDate();
        this.onTime = delivery.isOnTime();
    }

}
