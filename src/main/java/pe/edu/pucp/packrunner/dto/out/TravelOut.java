package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Travel;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@Getter
@Setter
public class TravelOut implements Serializable {

    private long idTravel;
    private Date dateDeparture;
    private Date dateArrival;
    private long idEdge;

    private double edgeDistance;

    private double edgeTime;
    private long idStartVertex;
    private String nameStartVertex;
    private long idEndVertex;
    private String nameEndVertex;
    private String action;
    private boolean traversed;

    public TravelOut(Travel travel) {
        this.idTravel= travel.getId();
        this.dateDeparture = travel.getDateDeparture();
        this.dateArrival = travel.getDateArrival();
        if(travel.getEdge() != null) {
            this.idEdge = travel.getEdge().getId();
            this.edgeDistance = travel.getEdge().getDistance();
            this.edgeTime = travel.getEdge().getTime();
        }
        this.idStartVertex = travel.getStartVertex().getId();
        this.nameStartVertex = travel.getStartVertex().getProvince().getName();
        this.idEndVertex = travel.getEndVertex().getId();
        this.nameEndVertex = travel.getEndVertex().getProvince().getName();
        this.action = travel.getAction();
        this.traversed = travel.isTraversed();
    }


}
