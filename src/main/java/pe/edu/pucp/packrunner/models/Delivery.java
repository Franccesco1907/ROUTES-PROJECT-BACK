package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.TruckStatus;

import java.util.Date;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMHHssDate;
import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMYYYYHHssDate;

@Entity
@Table(name = "PR_Delivery")
@SQLDelete(sql = "UPDATE PR_Delivery SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Delivery extends BaseEntity {
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_order")
  private Order order;

  @Column(name = "num_packages")
  private int numPackages;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "location")
  private Vertex location;

  @Column(name = "completed")
  private boolean completed = false;

  @Column(name = "delivered")
  private boolean delivered = false;

  @Column(name = "delivery_date")
  private Date deliveryDate;

  @Column(name = "on_time")
  private boolean onTime = true;

  public Delivery(Order order, int numPackages) {
    this.order = order;
    this.numPackages = numPackages;
    this.location = order.getDepot();
    this.completed = false;
    this.delivered = false;
    this.onTime = true;
  }

  public Delivery(Delivery delivery) {
    this.order = delivery.getOrder();
    this.numPackages = delivery.getNumPackages();
    this.location = delivery.getLocation();
    this.completed = delivery.isCompleted();
    this.delivered = delivery.isDelivered();
    this.deliveryDate = delivery.getDeliveryDate();
    this.onTime = delivery.isOnTime();
  }

  public void update(Date date) {


    if (deliveryDate == null) {
      order.setDeliveryDate(null);
      setDelivered(false);
      order.setDelivered(false);
      order.setOnTime(false);
      setOnTime(false);
      setCompleted(false);
    }
    else {



      // Update Deliveries and Orders
      if (deliveryDate.after(date)) {
        setDelivered(false);
        order.setDelivered(false);
      }
      else {
        // Set the delivery as delivered
        setDelivered(true);
        // Update the number of the order's delivered packages
        order.setDeliveredPackages(order.getDeliveredPackages() + numPackages);
        // If all packages have been delivered, set the order as delivered
        if (order.getDeliveredPackages() >= order.getNumPackages())
          order.setDelivered(true);
      }

      // Set the order's delivery Date
      if (order.getUnassignedPackages() == 0) {
        if (order.getDeliveryDate() != null) {
          if (order.getDeliveryDate().before(this.getDeliveryDate()))
            order.setDeliveryDate(this.getDeliveryDate());
        } else {
          order.setDeliveryDate(this.getDeliveryDate());
        }
      }
      else
        order.setDeliveryDate(null);

      if (deliveryDate.after(order.getLimitDate())) {
        setOnTime(false);
        order.setOnTime(false);
        setCompleted(false);
      }
      else {
        setOnTime(true);
        order.setOnTime(true);
        setCompleted(true);
      }

      if (!order.isDelivered() && order.getLimitDate().before(date))
        order.setOnTime(false);


    }

    if (!order.isDelivered() && date.after(order.getLimitDate()))
      order.setOnTime(false);

    // order.setOnTime(order.isDelivered() || date.before(order.getLimitDate()));

  }

  public String toString() {
    String orderDate = printDDMMHHssDate(this.getOrder().getOrderDate());
    String limitDate = printDDMMHHssDate(this.getOrder().getLimitDate());
    String deliveryDate = this.getDeliveryDate() != null ?printDDMMHHssDate(this.getDeliveryDate()):"XX/XX XX:XX";
    return String.format("DELIVERY: ORDER %05d -- %-25s -> %-25s %s a %s (%s) %3d Packages %s%s",
        this.getOrder().getId(),
        this.getOrder().getDepot().getProvince().getName(),
        this.getOrder().getOffice().getProvince().getName(),
        orderDate,
        deliveryDate,
        limitDate,
        this.getNumPackages(),
        this.delivered ? "(DELIVERED)" : "(NOT DELIVERED)",
        this.onTime ? "" : "(LATE)");
  }
}
