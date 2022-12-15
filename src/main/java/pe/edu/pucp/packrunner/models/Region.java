package pe.edu.pucp.packrunner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PR_Region")
@SQLDelete(sql = "UPDATE PR_Region SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Region extends BaseEntity implements Serializable {
    @Column(name = "name")
    private String name;
}
