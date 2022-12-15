package pe.edu.pucp.packrunner.utils;

import lombok.Getter;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Vertex;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class QueueAStar {
    private List<NodeAStar> nodes;
    private double bestG;

    public QueueAStar() {
        this.nodes = new ArrayList<>();
        this.bestG = Double.MAX_VALUE; // move function
    }

    public void enqueue(NodeAStar node){
        // Check if the vertex is already in the queue
        for(NodeAStar n: nodes){
            if(Vertex.areTheSame(node.getVertex(), n.getVertex()))return;
        }
        //if(bestG > g) bestG = g;
        //NodeAStar node = new NodeAStar(vertex,g,h);
        nodes.add(node);
        nodes.sort(NodeAStar.FValueComparator);
    }

    public NodeAStar dequeue(){
        if(nodes.size()>0){
            NodeAStar node= nodes.get(0);
            nodes.remove(0);
            return node;
        }
        return null;
    }

    public boolean isEmpty(){
        return nodes.isEmpty();
    }
    public double getGValue(){
        return nodes.get(0).getG();
    }

    public boolean contains(Vertex v){
        for(NodeAStar node : this.nodes)
            if(node.getVertex() == v)
                return true;
        return false;
    }

}
