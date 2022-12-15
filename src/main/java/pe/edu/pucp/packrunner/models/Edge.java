package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.NaturalRegion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PR_Edge")
@SQLDelete(sql = "UPDATE PR_Edge SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Edge extends BaseEntity implements Serializable {
    // Attributes
    @Column(name = "blocked")
    private boolean blocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PR_Vertex_Edge", joinColumns = {
            @JoinColumn(name = "id_edge") }, inverseJoinColumns = { @JoinColumn(name = "id_vertex") })
    private List<Vertex> vertexes = new ArrayList<>(); // Constraints

    @Column(name = "distance")
    private double distance;

    @Column(name = "speed")
    private double speed;

    @Column(name = "time")
    private double time;

    // CONSTRUCTOR
    public Edge(Vertex a, Vertex b) {
        this.vertexes.add(a);
        this.vertexes.add(b);
        this.distance = Vertex.distHaversine(a, b);
        this.speed = getSpeed(a, b);
        this.time = this.speed != 0 ? this.distance / this.speed : 0; // en horas
        this.blocked = false;
    }

    // METHODS

    public double getSpeed(Vertex a, Vertex b) {
        double speed;
        // costa - costa (70 km/h)
        if (a.getProvince().getNaturalRegion() == NaturalRegion.COSTA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.COSTA)
            return 70;
        // costa - sierra (50 km/h)
        if ((a.getProvince().getNaturalRegion() == NaturalRegion.COSTA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.SIERRA) ||
                (a.getProvince().getNaturalRegion() == NaturalRegion.SIERRA &&
                        b.getProvince().getNaturalRegion() == NaturalRegion.COSTA))
            return 50;
        // sierra - sierra (60 km/h)
        if (a.getProvince().getNaturalRegion() == NaturalRegion.SIERRA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.SIERRA)
            return 60;
        // sierra - selva (55 km/h)
        if ((a.getProvince().getNaturalRegion() == NaturalRegion.SIERRA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.SELVA) ||
                (a.getProvince().getNaturalRegion() == NaturalRegion.SELVA &&
                        b.getProvince().getNaturalRegion() == NaturalRegion.SIERRA))
            return 55;
        // selva - selva (65 km/h)
        if (a.getProvince().getNaturalRegion() == NaturalRegion.SELVA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.SELVA)
            return 65;
        // costa - selva (60 km/h)
        if ((a.getProvince().getNaturalRegion() == NaturalRegion.COSTA &&
                b.getProvince().getNaturalRegion() == NaturalRegion.SELVA) ||
                (a.getProvince().getNaturalRegion() == NaturalRegion.SELVA &&
                        b.getProvince().getNaturalRegion() == NaturalRegion.COSTA))
            return 60;
        return 60;
    }

    public Vertex getNeighbour(Vertex v) {

        //Vertex[] vertexes = this.getVertexes().stream().toArray(Vertex[]::new);

        if (Vertex.areTheSame(vertexes.get(0), v))
            return vertexes.get(1);
        else
            return vertexes.get(0);
    }

    public boolean equals(Edge edge) {
        return this.getId() == edge.getId();
    }

    public String toString() {

        //Vertex[] vertexes = this.getVertexes().stream().toArray(Vertex[]::new);

        return String.format("%7s - %-25s ==> %7s - %-25s",
                vertexes.get(0).getProvince().getUbiGeo(),
                vertexes.get(0).getProvince().getName(),
                vertexes.get(1).getProvince().getUbiGeo(),
                vertexes.get(1).getProvince().getName());
    }

}

