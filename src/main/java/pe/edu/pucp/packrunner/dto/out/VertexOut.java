package pe.edu.pucp.packrunner.dto.out;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Vertex;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
public class VertexOut implements Serializable {

    private long idVertex;
    private int type; // 1: Office, 0: Depot

    private String regionName;
    private String ubiGeo;
    private String provinceName;
    private String naturalRegion;

    private double latitude;
    private double longitude;

    private double x;
    private double y;

    public VertexOut(Vertex vertex) {
        this.idVertex = vertex.getId();
        this.type = vertex.getType();

        this.regionName = vertex.getProvince().getRegion().getName();
        this.ubiGeo = vertex.getProvince().getUbiGeo();
        this.provinceName = vertex.getProvince().getName();
        this.naturalRegion = vertex.getProvince().getNaturalRegion().toString();

        this.latitude = vertex.getLatitude();
        this.longitude = vertex.getLongitude();

        this.x = vertex.getX();
        this.y = vertex.getY();
    }
}
