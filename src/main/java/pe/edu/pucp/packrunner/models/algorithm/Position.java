package pe.edu.pucp.packrunner.models.algorithm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;

@NoArgsConstructor
public class Position {
    @Getter @Setter
    private ArrayList<Long> partId;//IDs de parte de un pedido

    @Getter @Setter
    private ArrayList<Long> truckId;//IDs de camión

    @Getter @Setter
    private ArrayList<Integer> obligatory;//indica si es obligatorio: ejm para pedidos que quedaron de choques

    public Position(int numDim) {
        this.partId = new ArrayList<>();
        this.truckId = new ArrayList<>();
        this.obligatory=new ArrayList<>();
    }
}
