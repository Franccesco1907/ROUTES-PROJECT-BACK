package pe.edu.pucp.packrunner.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.TravelType;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMHHssDate;

@Entity
@Table(name = "PR_Travel")
@SQLDelete(sql = "UPDATE PR_Travel SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Travel extends BaseEntity implements Serializable {
  @Column(name = "date_departure")
  private Date dateDeparture;

  @Column(name = "date_arrival")
  private Date dateArrival;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_edge")
  private Edge edge;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "start_vertex")
  private Vertex startVertex;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "end_vertex")
  private Vertex endVertex;

  @Column(name = "type")
  private TravelType type;

  @Column(name = "action")
  private String action;

  @Column(name = "traversed")
  private boolean traversed;

  public Travel(Date dateDeparture,
                Date dateArrival,
                Edge edge,
                Vertex startVertex,
                Vertex endVertex) {
    this.dateDeparture = dateDeparture;
    this.dateArrival = dateArrival;
    this.edge = edge;
    this.startVertex = startVertex;
    this.endVertex = endVertex;
    this.traversed = false;
  }

  public Travel(Date dateDeparture,
                Date dateArrival,
                Edge edge,
                Vertex startVertex,
                Vertex endVertex,
                TravelType type) {
    this.dateDeparture = dateDeparture;
    this.dateArrival = dateArrival;
    this.edge = edge;
    this.startVertex = startVertex;
    this.endVertex = endVertex;
    this.setType(type, 0);
    this.traversed = false;
  }

  public Travel(Date dateDeparture,
                Date dateArrival,
                Edge edge,
                Vertex startVertex,
                Vertex endVertex,
                TravelType type,
                int deliveredPackages) {
    this.dateDeparture = dateDeparture;
    this.dateArrival = dateArrival;
    this.edge = edge;
    this.startVertex = startVertex;
    this.endVertex = endVertex;
    this.setType(type, deliveredPackages);
    this.traversed = false;
  }

  public void setType(TravelType type, int deliveredPackages) {
    this.setType(type);
    switch(type) {
      case MOVEMENT:
        this.setAction("The truck moves");
        break;
      case DELIVERY:
        this.setAction(deliveredPackages + " package" + (deliveredPackages==1?" is ":"s are ") + "delivered");
        break;
      case RETURN:
        this.setAction("The truck goes home");
        break;
      case MAINTENANCE:
        this.setAction("Under Maintenance");
        break;
      case MODERATE_BREAKDOWN:
        this.setAction("Moderate Breakdown");
        break;
      case SERIOUS_BREAKDOWN:
        this.setAction("Serious Breakdown");
        break;
      case DEATH:
        this.setAction("The truck died :(");
    }
  }

  public String toString() {
    String startDate = printDDMMHHssDate(this.getDateDeparture());
    String endDate = printDDMMHHssDate(this.getDateArrival());

    return String.format("%-25s (%s) -> %-25s (%s) -> %-25s (%c)",
        this.getStartVertex().getProvince().getName(),
        startDate,
        this.getEndVertex().getProvince().getName(),
        endDate,
        this.action,
        this.traversed?'X':' ');
  }
}
