package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.Fleet;

@Entity
@Table(name = "PR_Truck")
@SQLDelete(sql = "UPDATE PR_Truck SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Truck extends BaseEntity {
    // ATTRIBUTES
    @Column(name = "fleet")
    private Fleet fleet;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_starting_depot")
    private Vertex startingDepot;

    public Truck(String code, Fleet fleet, Vertex startingDepot) {
        this.setCode(code);
        this.fleet = fleet;
        this.maxCapacity = fleet.getCapacity();
        this.startingDepot = startingDepot;
    }

    public String toString() {
        return String.format("TRUCK %-4s - CAPACITY: %d PACKAGES - STARTING DEPOT: %-10s",
                this.getCode(),
                this.getMaxCapacity(),
                this.getStartingDepot().getProvince().getName());
    }

}
