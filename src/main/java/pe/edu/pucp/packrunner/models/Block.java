package pe.edu.pucp.packrunner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMHHssDate;


@Entity
@Table(name = "PR_Block")
@SQLDelete(sql = "UPDATE PR_Block SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Block extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_edge")
    private Edge edge;

    @Column(name = "starting_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    public static boolean isBlocked(Date departureDate, Date arrivalDate, Edge edge, List<Block> blocks) {
        for (Block block : blocks) {
            if(block.edge.equals(edge))
                if(
                    departureDate.after(block.startDate) && departureDate.before(block.endDate) ||
                    arrivalDate.after(block.startDate) && arrivalDate.before(block.endDate) ||
                    departureDate.after(block.startDate) && arrivalDate.before(block.endDate) ||
                    departureDate.before(block.startDate) && arrivalDate.after(block.endDate))
                return true;
        }
        return false;
    }

    public String toString() {
        String startDate = printDDMMHHssDate(this.getStartDate());
        String endDate = printDDMMHHssDate(this.getEndDate());

        List<Vertex> vertexes = edge.getVertexes();

        return String.format("Edge (%6s) %-25s == (%6s) %-25s Blocked from %s to %s",
                vertexes.get(0).getProvince().getUbiGeo(),
                vertexes.get(0).getProvince().getName(),
                vertexes.get(1).getProvince().getUbiGeo(),
                vertexes.get(1).getProvince().getName(),
                startDate,
                endDate);
    }
}
