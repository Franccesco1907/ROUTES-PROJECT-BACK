package pe.edu.pucp.packrunner.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Vertex;

import java.util.Comparator;


@NoArgsConstructor
@Getter @Setter
public class NodeAStar {
    private Vertex vertex;
    private double g;   // move function
    private double h;   // heuristic function
    private double f;   // cost function
    private NodeAStar previous;

    public NodeAStar(Vertex vertex, double gValue, double hValue, NodeAStar previous) {
        this.vertex = vertex;
        this.g = gValue;
        this.h = hValue;
        this.f = h + g;
        this.previous = previous;
    }

    public static Comparator<NodeAStar> FValueComparator = new Comparator<NodeAStar>() {
        @Override
        public int compare(NodeAStar n1, NodeAStar n2) {
            double f1 = n1.getF();
            double f2 = n2.getF();
            return (int) (f1 - f2);
        }
    };

}
