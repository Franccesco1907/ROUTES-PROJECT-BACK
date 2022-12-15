package pe.edu.pucp.packrunner.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Truck;
import pe.edu.pucp.packrunner.models.TruckPlan;

import java.util.Comparator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TruckDistance {
  TruckPlan truckPlan;
  Order order;
  double distance;

  public static Comparator<TruckDistance> DistanceComparator = new Comparator<TruckDistance>() {
    @Override
    public int compare(TruckDistance a, TruckDistance b) {
      return (int) (a.getDistance() - b.getDistance());
    }
  };

}

