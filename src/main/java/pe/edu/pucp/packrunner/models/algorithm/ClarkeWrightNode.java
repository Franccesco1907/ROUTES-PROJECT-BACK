package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Vertex;

public class ClarkeWrightNode implements Comparable<ClarkeWrightNode> {
    @Getter
    @Setter
    private Order order1;
    @Getter
    @Setter
    private Order order2;
    @Getter
    @Setter
    private double save;

    public ClarkeWrightNode(Order order1, Order order2) {
        this.order1 = order1;
        this.order2 = order2;
    }

    public void calculateSave(Vertex depot) {
        double save;
        double ord1ToDep = Vertex.distHaversine(depot, order1.getOffice());
        double ord2ToDep = Vertex.distHaversine(depot, order2.getOffice());

        if (Vertex.areTheSame(order1.getOffice(), order2.getOffice())) {
            save = ord1ToDep + ord2ToDep;
        } else {
            double ord1ToOrd2 = Vertex.distHaversine(order1.getOffice(), order2.getOffice());

            save = ord1ToDep + ord2ToDep - ord1ToOrd2;
        }
        this.save = save;
    }

    @Override
    public int compareTo(ClarkeWrightNode node) {
        // Ordena en forma descendente
        if (this.save < node.save)
            return 1;
        else if (this.save > node.save)
            return -1;
        else
            return 0;
    }
}
