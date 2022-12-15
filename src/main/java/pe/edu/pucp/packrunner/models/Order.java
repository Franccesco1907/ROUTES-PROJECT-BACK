package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMYYYYHHssDate;

@Entity
@Table(name = "PR_Order")
@SQLDelete(sql = "UPDATE PR_Order SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order extends BaseEntity implements Serializable {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_depot")
    private Vertex depot;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_office")
    private Vertex office;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "num_packages")
    private int numPackages;

    @Column(name = "type")
    private SimulationType type;

    @Column(name = "unassigned_packages")
    private int unassignedPackages = numPackages;

    @Column(name = "delivered_packages")
    private int deliveredPackages = 0;

    @Column(name = "order_date")
    protected Date orderDate = creationDate;

    @Column(name = "limit_date")
    protected Date limitDate;

    @Column(name = "delivery_date")
    protected Date deliveryDate;

    @Column(name = "priority")
    private double priority;

    @Column(name = "delivered")
    private boolean delivered = false;

    @Column(name = "on_time")
    private boolean onTime = true;

    // METHODS

    public Order(Vertex depot, Vertex office, Client client,
            int numPackages, Date orderDate, SimulationType type) {
        this.depot = depot;
        this.office = office;
        this.client = client;
        this.numPackages = numPackages;
        this.type = type;
        this.unassignedPackages = numPackages;
        this.deliveredPackages = 0;
        this.orderDate = orderDate;
        this.limitDate = calculateLimitDate();
        this.priority = limitDate.getTime() - orderDate.getTime();
        this.delivered = false;
        this.onTime = true;
    }

    public Order(int numPackages, int unassignedPackages, Date orderDate) {
        this.numPackages = numPackages;
        this.unassignedPackages = unassignedPackages;
        this.orderDate = orderDate;
        this.limitDate = calculateLimitDate();
    }

    public Order(Order order) {
        this.depot = order.getDepot();
        this.office = order.getOffice();
        this.client = order.getClient();
        this.numPackages = order.getNumPackages();
        this.type = order.getType();
        this.unassignedPackages = order.getUnassignedPackages();
        this.deliveredPackages = order.getDeliveredPackages();
        this.orderDate = order.getOrderDate();
        this.limitDate = calculateLimitDate();
        this.deliveryDate = order.getDeliveryDate();
        this.priority = order.getPriority();
        this.delivered = order.isDelivered();
        this.onTime = order.isOnTime();
    }

    public void reset() {
        this.unassignedPackages = this.numPackages;
        this.deliveredPackages = 0;
        this.deliveryDate = null;
        this.priority = 0;
        this.setDelivered(false);
        this.setOnTime(true);

    }

    public void softReset() {
        this.unassignedPackages = this.numPackages - this.deliveredPackages;
        this.deliveryDate = null;
        this.priority = 0;
    }

    public Date calculateLimitDate() {
        int dayLimit;

        switch (this.office.getProvince().getNaturalRegion()) {
            case COSTA:
                dayLimit = 1;
                break;
            case SIERRA:
                dayLimit = 2;
                break;
            case SELVA:
                dayLimit = 3;
                break;
            default:
                dayLimit = 0;
                break;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(this.orderDate);
        c.add(Calendar.DATE, dayLimit);
        Date limitDate = c.getTime();

        return limitDate;
    }

    public static List<Order> dateSubList(List<Order> orders, Date start, Date end) {

        Predicate<Order> after = o -> o.getOrderDate().after(start);
        Predicate<Order> before = o -> o.getOrderDate().before(end);

        return orders.stream().filter(after.and(before)).collect(Collectors.toList());
    }

    public boolean equals(Order order) {
        return this.getId() == order.getId();
    }

    public static Comparator<Order> PriorityComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            Double priority1 = o1.getPriority();
            Double priority2 = o2.getPriority();
            return priority1.compareTo(priority2);
        }
    };

    public static Comparator<Order> OrderDateComparator = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            Date od1 = o1.getOrderDate();
            Date od2 = o2.getOrderDate();
            return od1.compareTo(od2);
        }
    };

    public String toString() {
        String orderDate = printDDMMYYYYHHssDate(this.getOrderDate());
        String limitDate = printDDMMYYYYHHssDate(this.getLimitDate());
        String deliveryDate = printDDMMYYYYHHssDate(this.getDeliveryDate());
        return String.format("ORDER %05d: %-25s -> %-25s %s a %s (%s)  %02d/%02d Packages %s%s",
                this.getId(),
                this.getDepot().getProvince().getName(),
                this.getOffice().getProvince().getName(),
                orderDate,
                deliveryDate,
                limitDate,
                this.deliveredPackages,
                this.numPackages,
                this.delivered ? "(DELIVERED)" : "(NOT DELIVERED)",
                this.onTime ? "" : "(LATE)");
    }

}
