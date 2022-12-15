package pe.edu.pucp.packrunner.models;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.enumerator.TravelType;
import pe.edu.pucp.packrunner.models.enumerator.TruckStatus;

import java.io.Serializable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static pe.edu.pucp.packrunner.utils.PrintMethods.printDDMMHHssDate;

@Entity
@Table(name = "PR_Truck_Plan")
@SQLDelete(sql = "UPDATE PR_Truck_Plan SET active = 0 WHERE id = ?")
@Where(clause = "active = 1")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TruckPlan extends BaseEntity implements Serializable {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_simulation")
  private Simulation simulation;

  @Column(name = "run")
  private int run;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "id_truck")
  private Truck truck;

  @Column(name = "status")
  private TruckStatus status;

  @Column(name = "travelling")
  private boolean travelling;

  @Column(name = "num_packages")
  private int numPackages;

  @Column(name = "unassigned_packages")
  private int unassignedPackages;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "location")
  private Vertex location;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "PR_TruckPlan_Travel", joinColumns = {@JoinColumn(name = "id_truck_plan")}, inverseJoinColumns = {
          @JoinColumn(name = "id_travel")})
  private List<Travel> travels;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "id_truck_plan", referencedColumnName = "id")
  private List<Delivery> deliveries;

  public TruckPlan(Truck truck, Simulation simulation) {
    this.simulation = simulation;
    this.run = 1;
    this.truck = truck;
    this.status = TruckStatus.OPERATIONAL;
    this.travelling = false;
    this.numPackages = truck.getMaxCapacity();
    this.unassignedPackages = truck.getMaxCapacity();
    this.location = truck.getStartingDepot();
    this.deliveries = new ArrayList<>();
    this.travels = new ArrayList<>();
  }

  public TruckPlan(Truck truck, Simulation simulation, int run) {
    this.simulation = simulation;
    this.run = run;
    this.truck = truck;
    this.status = TruckStatus.OPERATIONAL;
    this.travelling = false;
    this.numPackages = truck.getMaxCapacity(); // Trucks are filled with packages
    this.unassignedPackages = truck.getMaxCapacity();
    this.location = truck.getStartingDepot();
    this.deliveries = new ArrayList<>();
    this.travels = new ArrayList<>();
  }

  public TruckPlan(TruckPlan truckPlan) {
    this.simulation = truckPlan.getSimulation();
    this.run = truckPlan.getRun() + 1;
    this.truck = truckPlan.getTruck();
    this.status = truckPlan.getStatus();
    this.travelling = truckPlan.isTravelling();
    this.numPackages = truckPlan.getNumPackages();
    this.unassignedPackages = truckPlan.getUnassignedPackages();
    this.location = truckPlan.getLocation();

    this.deliveries = new ArrayList<>();
    this.deliveries.addAll(truckPlan.getDeliveries());
    this.travels = new ArrayList<>();
    this.travels.addAll(truckPlan.getTravels());
  }

  public Optional<Travel> getCurrentTravel(Date currDate) {
    // If the tuck has no travels
    if (this.getTravels().isEmpty()) return Optional.empty();

    // Get the current travel
    for (Travel travel : this.travels)
      if (travel.getDateArrival().equals(currDate) || travel.getDateDeparture().equals(currDate) ||
          (currDate.after(travel.getDateDeparture()) && currDate.before(travel.getDateArrival()))){
        return Optional.of(travel);
      }
    return Optional.empty();
  }

  public Optional<Travel> getLastTravel(Date currDate) {
    // If the tuck has no travels
    if (this.getTravels().isEmpty()) return Optional.empty();

    for (int i=0; i<travels.size() - 1; i++)
      if (currDate.after(travels.get(i).getDateArrival()) &&
          currDate.before(travels.get(i+1).getDateDeparture())) {
        return Optional.ofNullable(travels.get(i));
      }
    return Optional.ofNullable(travels.get(travels.size()-1));
  }

  public Vertex getLocation(Date date) {

    Optional<Travel> currentTravel = getCurrentTravel(date);
    Optional<Travel> lastTravel = getLastTravel(date);

    return currentTravel.isPresent()?
            currentTravel.get().getEndVertex():
            lastTravel.isPresent()?
                    lastTravel.get().getEndVertex():
                    this.getTruck().getStartingDepot();
  }

  public void update(Date date) {

    // If the truck has no travels, it is not travelling
    for (Travel travel : this.getTravels())
      if (travel.getDateArrival().after(simulation.getClock())) travelling = true;

    Optional<Travel> currentTravel = getCurrentTravel(date);

    // We update the truck's location
    location = getLocation(date);

    if (location == this.getTruck().getStartingDepot()) {
      this.unassignedPackages += this.getTruck().getMaxCapacity() - this.numPackages;
      this.numPackages = this.getTruck().getMaxCapacity();
    }

    //this.deliveries.removeIf(Delivery::isDelivered);

    // We unload the number of deliveries that have been delivered
    for (Delivery delivery : deliveries) {
      if (delivery.getDeliveryDate() != null)
        if (!delivery.isDelivered()) {
          if (date.after(delivery.getDeliveryDate())) {
            this.numPackages -= delivery.getNumPackages();
          }
          delivery.setLocation(this.location);
          delivery.update(date);
        }
    }

    // If a truck is ready to be operational, we reactivate it
    TravelType type;
    if (currentTravel.isPresent()) {
      type = currentTravel.get().getType();
      if (type != TravelType.MODERATE_BREAKDOWN &&
          type != TravelType.DEATH &&
          type != TravelType.SERIOUS_BREAKDOWN &&
          type != TravelType.MAINTENANCE)
        setStatus(TruckStatus.OPERATIONAL);
    }
    else
      setStatus(TruckStatus.OPERATIONAL);

    // Update the Truck Plan's travels
    for (Travel travel : getTravels())
      if (travel.getDateArrival().before(date) || (travel.getDateArrival().equals(date)))
        travel.setTraversed(true);

  }

  // Gives the truck a moderate breakdown
  public List<Order> disable() {

    // Only operational trucks can break down
    if (this.getStatus() != TruckStatus.OPERATIONAL) {
      System.out.println("Truck is already not operational");
      return new ArrayList<>();
    }

    // We flag the truck as moderately broken
    this.setStatus(TruckStatus.MODERATE_BREAKDOWN);

    // We determine the break Dates and fix Dates
    Date breakDate = this.getSimulation().getClock();
    Calendar c = Calendar.getInstance();
    c.setTime(breakDate);
    c.add(Calendar.HOUR, 12);
    Date fixDate = c.getTime();

    // Filter the not yet traversed travels
    Predicate<Travel> notTraversed = t -> !t.isTraversed();
    List<Travel> travels = this.getTravels()
            .stream().filter(notTraversed)
            .collect(Collectors.<Travel>toList());

    // The travel is created
    Travel breakTravel = new Travel(breakDate, fixDate, null,
            this.getLocation(), this.getLocation(), TravelType.MODERATE_BREAKDOWN);

    // If the travel has still travels to traverse
    if (!travels.isEmpty()) {

      Optional<Travel> currentTravel = this.getCurrentTravel(breakDate);

      // If the truck was broken in its first travel
      int index = 0;
      if (travels.get(0).getDateDeparture().equals(this.getSimulation().getClock()) ||
          this.getSimulation().getClock().before(travels.get(0).getDateDeparture())) {
        index = this.getTravels().indexOf(travels.get(0));
        this.getTravels().add(index, breakTravel);
        index += 1;
      }
      // If the truck broke down mid-travel
      else if (currentTravel.isPresent()) {

        index = this.getTravels().indexOf(currentTravel.get());

        // The travel is cut short
        Travel cutTravel = new Travel(
                currentTravel.get().getDateDeparture(),
                breakDate,
                currentTravel.get().getEdge(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getType()!=TravelType.DELIVERY?currentTravel.get().getType():TravelType.MOVEMENT);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(currentTravel.get().getDateArrival());
        c2.add(Calendar.HOUR, 12);

        Travel resumeTravel = new Travel(
                fixDate,
                c2.getTime(),
                currentTravel.get().getEdge(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getEndVertex(),
                currentTravel.get().getType());
        resumeTravel.setAction(currentTravel.get().getAction());

        // The cut Travel and break Travel are added
        this.getTravels().remove(currentTravel.get());
        this.getTravels().add(index, cutTravel);
        this.getTravels().add(index + 1, breakTravel);
        this.getTravels().add(index + 2, resumeTravel);
        index += 3;
      }
      // If the truck breaks down in between travels
      else {
        Optional<Travel> lastTravel = this.getLastTravel(breakDate);
        if (lastTravel.isPresent()) {
          index = this.getTravels().indexOf(lastTravel.get());

          // Only the break Travel is added
          this.getTravels().add(index + 1, breakTravel);
          index += 2;
        }

      }

      List<Delivery> lateDeliveries = new ArrayList<>();;
      List<Order> lateOrders = new ArrayList<>();
      // Delivery Dates are updated
      for (Delivery delivery : this.getDeliveries()) {
        Calendar cDeliveryDate = Calendar.getInstance();
        cDeliveryDate.setTime(delivery.getDeliveryDate());
        cDeliveryDate.add(Calendar.HOUR, 12);

        delivery.setDeliveryDate(cDeliveryDate.getTime());
        delivery.update(this.getSimulation().getClock());

        if(!delivery.isOnTime()) {
          lateDeliveries.add(delivery);
          Order order = delivery.getOrder();
          order.setUnassignedPackages(order.getUnassignedPackages() + delivery.getNumPackages());
          this.setUnassignedPackages(this.getUnassignedPackages() + delivery.getNumPackages());
          lateOrders.add(order);
        }
      }

      // If deliveries are late they must be reassigned
      if (!lateDeliveries.isEmpty()) {
        // All future travels deleted
        int size = this.getTravels().size();
        this.getDeliveries().removeIf(lateDeliveries::contains);
        if (size > index) this.getTravels().subList(index, size).clear();
        return lateOrders;
      }
      else {
        for (int i = index; i < this.getTravels().size(); i++) {

          Calendar cStart = Calendar.getInstance();
          Calendar cEnd = Calendar.getInstance();

          cStart.setTime(this.getTravels().get(i).getDateDeparture());
          cEnd.setTime(this.getTravels().get(i).getDateArrival());

          cStart.add(Calendar.HOUR, 12);
          cEnd.add(Calendar.HOUR, 12);

          this.getTravels().get(i).setDateDeparture(cStart.getTime());
          this.getTravels().get(i).setDateArrival(cEnd.getTime());
        }
      }

    } else {
      this.getTravels().add(breakTravel);
    }
    return new ArrayList<>();
  }

  // Gives the truck a serious breakdown
  public List<Order> cripple() {

    // Only operational trucks can break down
    if (this.getStatus() != TruckStatus.OPERATIONAL) {
      System.out.println("Truck is already not operational");
      return new ArrayList<>();
    }

    // We flag the truck as moderately broken
    this.setStatus(TruckStatus.SERIOUS_BREAKDOWN);

    // We determine the break Dates and fix Dates
    Date breakDate = this.getSimulation().getClock();
    Calendar c = Calendar.getInstance();
    c.setTime(breakDate);
    c.add(Calendar.HOUR, 72);
    Date fixDate = c.getTime();

    // Filter the not yet traversed travels
    Predicate<Travel> notTraversed = t -> !t.isTraversed();
    List<Travel> travels = this.getTravels()
            .stream().filter(notTraversed)
            .collect(Collectors.<Travel>toList());

    // The break travel is created
    Travel breakTravel = new Travel(
            breakDate,
            fixDate,
            null,
            this.getLocation(),
            this.getTruck().getStartingDepot(),
            TravelType.SERIOUS_BREAKDOWN);

    // If the travel has still travels to traverse
    if (!travels.isEmpty()) {
      Optional<Travel> currentTravel = this.getCurrentTravel(breakDate);

      // If the truck was broken in its first travel
      int index = 0;
      if (travels.get(0).getDateDeparture().equals(this.getSimulation().getClock()) ||
              this.getSimulation().getClock().before(travels.get(0).getDateDeparture())) {
        index = this.getTravels().indexOf(travels.get(0));
        this.getTravels().add(index, breakTravel);
        index += 1;
      }
      // If the truck broke down mid-travel
      else if (currentTravel.isPresent()) {

        index = this.getTravels().indexOf(currentTravel.get());

        // The travel is cut short
        Travel cutTravel = new Travel(
                currentTravel.get().getDateDeparture(),
                breakDate,
                currentTravel.get().getEdge(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getType()!=TravelType.DELIVERY?currentTravel.get().getType():TravelType.MOVEMENT);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(currentTravel.get().getDateArrival());
        c2.add(Calendar.HOUR, 12);

        // The cut Travel and break Travel are added
        this.getTravels().remove(currentTravel.get());
        this.getTravels().add(index, cutTravel);
        this.getTravels().add(index + 1, breakTravel);
        index += 2;
      }
      // If the truck breaks down in between travels
      else {
        Optional<Travel> lastTravel = this.getLastTravel(breakDate);
        if (lastTravel.isPresent()) {
          index = this.getTravels().indexOf(lastTravel.get());

          // Only the break Travel is added
          this.getTravels().add(index + 1, breakTravel);
          index += 2;
        }
      }

      // All future travels deleted
      int size = this.getTravels().size();
      if (size > index) {
        this.getTravels().subList(index, size).clear();
      }

      // Delivery Dates are updated
      int deliveriesToReassign = 0;
      List<Order> lateOrders = new ArrayList<>();
      if (!this.getDeliveries().isEmpty()) {
        for (Delivery delivery : this.getDeliveries()) {
          deliveriesToReassign++;
          Order order = delivery.getOrder();
          order.setUnassignedPackages(order.getUnassignedPackages() + delivery.getNumPackages());
          this.setUnassignedPackages(this.getUnassignedPackages() + delivery.getNumPackages());
          lateOrders.add(order);
        }
      }
      this.deliveries.clear();
      // All Deliveries must be reassigned
      if (deliveriesToReassign > 0) {
        return lateOrders;
      }

    } else {
      this.getTravels().add(breakTravel);
    }
    return new ArrayList<>();
  }

  // Kill the truck, goodbye truck
  public List<Order> kill() {

    // Only operational trucks can break down
    if (this.getStatus() == TruckStatus.INOPERABLE) {
      System.out.println("Stop! He is already Dead!");
      return new ArrayList<>();
    }

    // We flag the truck as dead, perished, deceased
    this.setStatus(TruckStatus.INOPERABLE);

    // The travel is created
    Travel deathTravel = new Travel(this.getSimulation().getClock(), this.getSimulation().getClock(), null,
            this.getLocation(), this.getLocation(), TravelType.DEATH);

    // Filter the not yet traversed travels
    Predicate<Travel> notTraversed = t -> !t.isTraversed();
    List<Travel> travels = this.getTravels()
            .stream().filter(notTraversed)
            .collect(Collectors.<Travel>toList());

    // If the truck still had travels left
    if (!travels.isEmpty()) {

      Optional<Travel> currentTravel = this.getCurrentTravel(this.getSimulation().getClock());
      // If the truck is killed before it even had a chance to fly
      int index = 0;
      if (travels.get(0).getDateDeparture().equals(this.getSimulation().getClock()) ||
              this.getSimulation().getClock().before(travels.get(0).getDateDeparture())) {
        index = this.getTravels().indexOf(travels.get(0));
        this.getTravels().add(index, deathTravel);
        index += 1;
      }
      // If the truck is killed mid-travel
      else if (currentTravel.isPresent()) {

        index = this.getTravels().indexOf(currentTravel.get());

        // The travel is cut short
        Travel cutTravel = new Travel(
                currentTravel.get().getDateDeparture(),
                this.getSimulation().getClock(),
                currentTravel.get().getEdge(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getType()!=TravelType.DELIVERY?currentTravel.get().getType():TravelType.MOVEMENT);
        // currentTravel.get().

        this.getTravels().remove(currentTravel.get());
        this.getTravels().add(index, cutTravel);
        this.getTravels().add(index + 1, deathTravel);
        index += 2;

      } else {

        Optional<Travel> lastTravel = this.getLastTravel(this.getSimulation().getClock());
        if (lastTravel.isPresent()) {
          index = this.getTravels().indexOf(lastTravel.get());

          // Only the break Travel is added
          this.getTravels().add(index + 1, deathTravel);
          index += 2;
        }

      }
      // All future travels deleted
      int size = this.getTravels().size();
      if (size > index) {
        this.getTravels().subList(index, size).clear();
      }

      // All Deliveries are removed
      int deliveriesToReassign = 0;
      List<Order> lateOrders = new ArrayList<>();
      for (Delivery delivery : this.getDeliveries()) {
        deliveriesToReassign++;
        Order order = delivery.getOrder();
        order.setUnassignedPackages(order.getUnassignedPackages() + delivery.getNumPackages());
        this.setUnassignedPackages(this.getUnassignedPackages() + delivery.getNumPackages());
        lateOrders.add(order);
      }

      this.getDeliveries().clear();

      // Deliveries must be reassigned
      if (deliveriesToReassign > 0)
        return lateOrders;

    } else {
      this.getTravels().add(deathTravel);
    }
    return new ArrayList<>();
  }

  public List<Order> maintenance() {
    // Trucks in maintenance cannot be sent to maintenance again
    if (this.getStatus() == TruckStatus.IN_MAINTENANCE) {
      System.out.println("Truck is already under maintenance");
      return new ArrayList<>();
    }

    // We flag the truck as under maintenance
    this.setStatus(TruckStatus.IN_MAINTENANCE);

    Date maintenanceDate = this.getSimulation().getClock();

    // The travel is created
    Travel maintenanceTravel = new Travel(this.getSimulation().getClock(), this.getSimulation().getClock(), null,
            this.getTruck().getStartingDepot(), this.getTruck().getStartingDepot(), TravelType.MAINTENANCE);

    // Filter the not yet traversed travels
    Predicate<Travel> notTraversed = t -> !t.isTraversed();
    List<Travel> travels = this.getTravels()
            .stream().filter(notTraversed)
            .collect(Collectors.<Travel>toList());

    // If the travel has still travels to traverse
    if (!travels.isEmpty()) {
      Optional<Travel> currentTravel = this.getCurrentTravel(maintenanceDate);

      // If the truck was broken in its first travel
      int index = 0;
      if (travels.get(0).getDateDeparture().equals(this.getSimulation().getClock()) ||
              this.getSimulation().getClock().before(travels.get(0).getDateDeparture())) {
        index = this.getTravels().indexOf(travels.get(0));
        this.getTravels().add(index, maintenanceTravel);
        index += 1;
      }
      // If the truck broke down mid-travel
      else if (currentTravel.isPresent()) {

        index = this.getTravels().indexOf(currentTravel.get());

        // The travel is cut short
        Travel cutTravel = new Travel(
                currentTravel.get().getDateDeparture(),
                maintenanceDate,
                currentTravel.get().getEdge(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getStartVertex(),
                currentTravel.get().getType() != TravelType.DELIVERY ? currentTravel.get().getType() : TravelType.MOVEMENT);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(currentTravel.get().getDateArrival());
        c2.add(Calendar.HOUR, 12);

        // The cut Travel and break Travel are added
        this.getTravels().remove(currentTravel.get());
        this.getTravels().add(index, cutTravel);
        this.getTravels().add(index + 1, maintenanceTravel);
        index += 2;
      }
      // If the truck breaks down in between travels
      else {
        Optional<Travel> lastTravel = this.getLastTravel(maintenanceDate);
        if (lastTravel.isPresent()) {
          index = this.getTravels().indexOf(lastTravel.get());

          // Only the break Travel is added
          this.getTravels().add(index + 1, maintenanceTravel);
          index += 2;
        }
      }
      // All future travels deleted
      int size = this.getTravels().size();
      if (size > index) {
        this.getTravels().subList(index, size).clear();
      }

      // Delivery Dates are updated
      int deliveriesToReassign = 0;
      List<Order> lateOrders = new ArrayList<>();
      if (!this.getDeliveries().isEmpty()) {
        for (Delivery delivery : this.getDeliveries()) {
          deliveriesToReassign++;
          Order order = delivery.getOrder();
          order.setUnassignedPackages(order.getUnassignedPackages() + delivery.getNumPackages());
          this.setUnassignedPackages(this.getUnassignedPackages() + delivery.getNumPackages());
          lateOrders.add(order);
        }
      }
      this.deliveries.clear();
      // All Deliveries must be reassigned
      if (deliveriesToReassign > 0) {
        return lateOrders;
      }

    } else {
      this.getTravels().add(maintenanceTravel);
    }
    return new ArrayList<>();
  }

  public List<Order> assignMaintenances(Date date, List<Maintenance> maintenances, List<TruckPlan> truckPlans) {

    List<Order> lateOrders = new ArrayList<>();

    for (Maintenance maintenance : maintenances) {

      Calendar c1 = Calendar.getInstance();
      c1.setTime(date);
      Calendar c2 = Calendar.getInstance();
      c2.setTime(maintenance.getDate());

      if (c1.get(Calendar.DATE) == c2.get(Calendar.DATE) &&
          c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {

        for (TruckPlan truckPlan : truckPlans) {
          if (truckPlan.getTruck() == maintenance.getTruck())
            lateOrders.addAll(truckPlan.maintenance());
        }
      }
    }
    return lateOrders;
  }

  public static Comparator<TruckPlan> TruckNameComparator = new Comparator<TruckPlan>() {
    @Override
    public int compare(TruckPlan tp1, TruckPlan tp2) {
      String name1 = tp1.getTruck().getCode();
      String name2 = tp2.getTruck().getCode();
      return name1.compareTo(name2);
    }
  };

  public static Comparator<TruckPlan> UnassignedPackagesComparator = new Comparator<TruckPlan>() {
    @Override
    public int compare(TruckPlan tp1, TruckPlan tp2) {
      int capacity1 = tp1.getUnassignedPackages();
      int capacity2 = tp2.getUnassignedPackages();
      return capacity1 - capacity2;
    }
  };

  public String toString() {
    String stringDate = printDDMMHHssDate(this.getSimulation().getClock());
    String string = String.format("TRUCK: %s -- SIM: %05d -- RUN: %02d -- TIME: %s (%s) -- PACKAGES: %02d/%02d -- POSITION: %s\n",
            this.getTruck().getCode(),
            this.getSimulation().getId(),
            this.getRun(),
            stringDate,
            this.getStatus().toString(),
            this.getNumPackages(),
            this.getTruck().getMaxCapacity(),
            this.getLocation().getProvince().getName());

    for (int i = 0; i < 150; i++)
      string += "-";
    string += "\n";

    if(!this.getTravels().isEmpty())
      for (Travel travel : this.getTravels()) {
        string += String.format("%s\n", travel);
      }
    return string;
  }
}
