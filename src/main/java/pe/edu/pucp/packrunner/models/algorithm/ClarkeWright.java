package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.models.algorithm.ClarkeWrightNode;

import java.util.ArrayList;
import java.util.Collections;

public class ClarkeWright {
    private ArrayList<ClarkeWrightNode> lNode;
    @Getter
    private ArrayList<Order> orderChoose;
    private Vertex depot;

    public ClarkeWright(Vertex depot, ArrayList<Order> lOrder) {
        this.depot = depot;
        this.orderChoose = new ArrayList<Order>();
        this.lNode = new ArrayList<ClarkeWrightNode>();
        for (int i = 0; i < lOrder.size() - 1; i++) {
            for (int j = i + 1; j < lOrder.size(); j++) {
                ClarkeWrightNode node = new ClarkeWrightNode(lOrder.get(i), lOrder.get(j));
                node.calculateSave(depot);
                lNode.add(node);
            }
        }
    }

    private boolean allNodesRepeated(ClarkeWrightNode node, ArrayList<Order> orderChoose) {
        Order node1 = node.getOrder1();
        Order node2 = node.getOrder2();
        // Mira si los vertices son repetidos
        boolean r1 = false, r2 = false;
        for (int i = 0; i < orderChoose.size(); i++) {
            long idChoose = orderChoose.get(i).getId();
            if (node1.getId() == idChoose) {
                r1 = true;
            }
            if (node2.getId() == idChoose) {
                r2 = true;
            }
        }
        // Si el id del pedido no fue encontrado tendra que agregarse a la lista de
        // "Choose"
        if (!r1)
            orderChoose.add(node1);
        if (!r2)
            orderChoose.add(node2);
        if (r1 && r2)
            return true;
        else
            return false;
    }

    public void chooseBestSaves() {
        // Lo ordena descendentemente
        Collections.sort(lNode);
        // Elige los mejores y descarta los otros
        int nNodes = lNode.size();
        for (int i = 0; i < nNodes; i++) {
            ClarkeWrightNode node = lNode.get(i);
            // Actualiza la lista de pedidos escogidos
            if (allNodesRepeated(node, orderChoose)) {
                lNode.remove(i);
                nNodes--;
                i--;
            }

        }
    }

}
