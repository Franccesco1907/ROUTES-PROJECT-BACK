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
import java.io.File;

@Entity
@Table(name = "PR_DataFile")
@SQLDelete(sql = "UPDATE PR_Datafile SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataFile extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "file")
    private File file;

    @Column(name = "type")
    private String type;

}
