package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Block;
import pe.edu.pucp.packrunner.models.Edge;
import pe.edu.pucp.packrunner.models.Vertex;
import pe.edu.pucp.packrunner.utils.NodeAStar;
import pe.edu.pucp.packrunner.utils.QueueAStar;

import java.util.*;

import static pe.edu.pucp.packrunner.models.Vertex.areTheSame;
import static pe.edu.pucp.packrunner.models.Vertex.distHaversine;

@Getter
@Setter
public class Graph {
    // Attributes
    private List<Vertex> vertexes;
    private List<Edge> edges;
    private List<Block> blocks;
    private int nVertex;
    private int nEdge;
    private int nBlock;

    // Constructors
    public Graph() {
        this.vertexes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.nVertex = 0;
        this.nEdge = 0;
        this.nBlock = 0;
    }

    public Graph(List<Edge> edges,
            List<Vertex> offices,
            List<Vertex> depots,
            List<Block> blocks) {
        this.vertexes = new ArrayList<>();
        this.getVertexes().addAll(offices);
        this.getVertexes().addAll(depots);
        this.setEdges(edges);
        this.setBlocks(blocks);

        this.setNEdge(edges.size());
        this.setNVertex(this.getVertexes().size());
        this.setNBlock(blocks.size());

        this.getVertexes().sort(Vertex.NameComparator);
    }

    public Graph(List<Edge> edges,
                 List<Vertex> offices,
                 List<Vertex> depots) {
        this.vertexes = new ArrayList<>();
        this.getVertexes().addAll(offices);
        this.getVertexes().addAll(depots);
        this.setEdges(edges);

        this.setNEdge(edges.size());
        this.setNVertex(this.getVertexes().size());

        this.getVertexes().sort(Vertex.NameComparator);
    }

    public List<Edge> getVertexNeighbours(Vertex vertex) {
        List<Edge> neighbors = new ArrayList<>();

        for (Edge edge : getEdges()) {
            if (edge.getVertexes().contains(vertex) &&
                    !neighbors.contains(vertex)) {
                neighbors.add(edge);
            }
        }
        return neighbors;
    }

    public Edge getEdgeOf2Vertex(Vertex v1, Vertex v2) {
        for (Edge edge : getEdges()) {
            if (edge.getVertexes().contains(v1) && edge.getVertexes().contains(v2)) {
                return edge;
            }
        }
        return null;
    }

    public boolean areNeighbours(Vertex v1, Vertex v2) {
        for (Edge edge : edges) {
            if(edge.getVertexes().contains(v1) && edge.getVertexes().contains(v2))
                return true;
        }
        return false;
    }

    public List<Vertex> AStar(Vertex start, Vertex goal) {
        double weight, hValue, gValue, newGValue;
        List<Edge> neighbors;

        QueueAStar queue = new QueueAStar();       // A* Queue
        List<Vertex> route = new ArrayList<>();    // Open List
        Set<Vertex> closed = new HashSet<>();      // Closed List

        NodeAStar node = new NodeAStar(start, 0, distHaversine(start, goal), null);
        queue.enqueue(node);
        while (!queue.isEmpty()) {

            // Get the next vertex in the queue
            NodeAStar current = queue.dequeue();
            // If you reach your goal, break
            if (areTheSame(current.getVertex(), goal)) return buildPath(current);
            // If the route is not possible, try again
            neighbors = getVertexNeighbours(current.getVertex());
            for (Edge edge : neighbors) {
                // Get neighbor edge
                NodeAStar neighbour = new NodeAStar(edge.getNeighbour(current.getVertex()), Double.MAX_VALUE,
                        distHaversine(edge.getNeighbour(current.getVertex()), goal), null);

                weight = edge.getDistance();
                gValue = weight + current.getG();

                if(gValue < neighbour.getG()) {
                    neighbour.setPrevious(current);
                    neighbour.setG(gValue);
                    neighbour.setF(neighbour.getG() + neighbour.getH());

                    if (!queue.getNodes().contains(neighbour)) {
                        // Adds next vertex if not repeated
                        queue.enqueue(neighbour);
                    }

                }
            }
        }
        return new ArrayList<>();
    }

    public List<Vertex> buildPath(NodeAStar current) {
        List<Vertex> path = new ArrayList<>();
        path.add(current.getVertex());
        while(current.getPrevious() != null) {
            current = current.getPrevious();
            path.add(0, current.getVertex());
        }
        return path;
    }
}
