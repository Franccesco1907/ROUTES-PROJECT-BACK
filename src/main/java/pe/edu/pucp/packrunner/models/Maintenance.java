package pe.edu.pucp.packrunner.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMYYYYDate;

@Entity
@Table(name = "PR_Maintenance")
@SQLDelete(sql = "UPDATE PR_Maintenance SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Maintenance extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_truck")
    private Truck truck;

    @Column(name = "date")
    private Date date;

    public static Comparator<Maintenance> DateComparator = new Comparator<Maintenance>() {
        @Override
        public int compare(Maintenance m1, Maintenance m2) {
            Date date1 = m1.getDate();
            Date date2 = m2.getDate();
            return date1.compareTo(date2);
        }
    };

    public static Comparator<Maintenance> TruckCodeComparator = new Comparator<Maintenance>() {
        @Override
        public int compare(Maintenance m1, Maintenance m2) {
            String truckCode1 = m1.getTruck().getCode();
            String truckCode2 = m2.getTruck().getCode();
            return truckCode1.compareTo(truckCode2);
        }
    };

    public String toString() {
        String date = printDDMMYYYYDate(this.getDate());
        return String.format("Camión %4s tiene mantenimiento en %s",
                this.getTruck().getCode(),
                date);
    }
}
