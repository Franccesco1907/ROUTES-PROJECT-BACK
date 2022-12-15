package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "PR_Delivery_Plan")
@SQLDelete(sql = "UPDATE PR_Delivery_Plan SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeliveryPlan extends BaseEntity {
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinColumn(name = "id_delivery")
  private Delivery delivery;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "PR_DeliveryPlan_Travel", joinColumns = {
      @JoinColumn(name = "id_delivery_plan") }, inverseJoinColumns = { @JoinColumn(name = "id_travel") })
  private List<Travel> travels;

  @Column(name = "date_departure")
  private Date dateDeparture;

  @Column(name = "date_arrival")
  private Date dateArrival;

  @Column(name = "time_to_arrive")
  private Long timeToArrive;

  public DeliveryPlan(Delivery delivery) {
    this.delivery = new Delivery(delivery);
    this.travels = new ArrayList<>();
  }

  public DeliveryPlan(Delivery delivery, List<Travel> travels) {
    this.delivery = new Delivery(delivery);

    this.travels = new ArrayList<>();
    for (Travel travel : travels) {
      if (travel.getDateDeparture().after(delivery.getOrder().getOrderDate()) &&
          travel.getDateArrival().equals(delivery.getDeliveryDate()) ||
          travel.getDateArrival().before(delivery.getDeliveryDate()))
        this.travels.add(travel);
    }
  }


  public String toString() {
    String string = String.format("%s\n", this.getDelivery().toString());

    for (int i = 0; i < 125; i++)
      string += "-";
    string += "\n";

    if(!this.getTravels().isEmpty())
      for (Travel travel : this.getTravels()) {
        string += String.format("%s\n", travel);
      }
    for (int i = 0; i < 125; i++)
      string += "-";
    string += "\n";

    return string;
  }
}
