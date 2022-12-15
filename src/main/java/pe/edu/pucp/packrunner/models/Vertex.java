package pe.edu.pucp.packrunner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pe.edu.pucp.packrunner.utils.Mercator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "PR_Vertex")
@SQLDelete(sql = "UPDATE PR_Vertex SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vertex extends BaseEntity implements Serializable {
    @Getter
    final static double X_LIMIT_RIGHT = Mercator.xAxisProjection(-68.654087);
    @Getter
    final static double X_LIMIT_LEFT = Mercator.xAxisProjection(-81.324216);
    @Getter
    final static double Y_LIMIT_TOP = Mercator.yAxisProjection(-0.043656);
    @Getter
    final static double Y_LIMIT_BOT = Mercator.yAxisProjection(-18.345605);

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "type")
    private int type; // 0: Depot 1:Office

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_province")
    private Province province;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    private int nNeighbor;

    // CONSTRUCTOR
    public Vertex(long id, Province province, double latitude, double longitude) {
        setId(id);
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nNeighbor = 0;
    }

    // METODOS
    public void addNeighbor(Vertex vertex) {
        // this.lNeighbor.add(vertex);
        this.nNeighbor++;
    }

    public void addEdge(Edge edge, Vertex vertex) {
        // this.lEdge.add(edge);
        addNeighbor(vertex);
    }

    private static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public static boolean areTheSame(Vertex v1, Vertex v2) {
        return v1.getProvince().getUbiGeo() == v2.getProvince().getUbiGeo();
    }

    public static double distHaversine(Vertex v1, Vertex v2) {
        int R = 6371;
        double lat1, lat2, lon1, lon2;
        double latDist, lonDist;
        double a, c;

        lat1 = v1.getLatitude();
        lon1 = v1.getLongitude();
        lat2 = v2.getLatitude();
        lon2 = v2.getLongitude();

        latDist = toRad(lat2 - lat1);
        lonDist = toRad(lon2 - lon1);

        a = Math.sin(latDist / 2) * Math.sin(latDist / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    public static Comparator<Vertex> UbigeoComparator = (o1, o2) -> {
        String ubigeo1 = o1.getProvince().getUbiGeo();
        String ubigeo2 = o2.getProvince().getUbiGeo();
        return ubigeo1.compareTo(ubigeo2);
    };

    public static Comparator<Vertex> NameComparator = (o1, o2) -> {
        String name1 = o1.getProvince().getName();
        String name2 = o2.getProvince().getName();
        return name1.compareTo(name2);
    };

    public static boolean isInLimit(Vertex v) {
        return v.getX() <= X_LIMIT_RIGHT && v.getX() >= X_LIMIT_LEFT &&
                v.getY() <= Y_LIMIT_TOP && v.getY() >= Y_LIMIT_BOT;
    }

    public String toString() {
        return String.format("PROVINCE %6s: %-25s REGION: %-7s - %-20s  COORDINATES: (%-5.8f, %-5.8f)",
                this.getProvince().getUbiGeo(),
                this.getProvince().getName(),
                this.getProvince().getNaturalRegion(),
                this.getProvince().getRegion().getName(),
                this.getLongitude(),
                this.getLatitude());
    }
}
