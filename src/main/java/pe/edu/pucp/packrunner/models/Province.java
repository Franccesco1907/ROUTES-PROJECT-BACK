package pe.edu.pucp.packrunner.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import pe.edu.pucp.packrunner.models.enumerator.NaturalRegion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PR_Province")
@SQLDelete(sql = "UPDATE PR_Province SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Province extends BaseEntity implements Serializable {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_region")
    private Region region;

    @Column(name = "ubiGeo")
    private String ubiGeo;

    @Column(name = "name")
    private String name;

    @Column(name = "natural_region")
    private NaturalRegion naturalRegion; // Es posible que este atributo se mueva a Region

}
