package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Vertex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class EdgeOut implements Serializable {

    private long idEdge;
    private boolean blocked = false;

    private List<VertexOut> vertexes = new ArrayList<>();

    private double time;
    private double distance;
    private double speed;

    public EdgeOut(Edge edge) {
        this.idEdge = edge.getId();
        this.blocked = edge.isBlocked();

        for (Vertex vertex : edge.getVertexes())
            this.vertexes.add(new VertexOut(vertex));

        this.time = edge.getTime();
        this.distance = edge.getDistance();
        this.speed = edge.getSpeed();
    }
}
