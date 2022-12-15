package pe.edu.pucp.packrunner.utils;

import org.aspectj.weaver.ast.Or;
import pe.edu.pucp.packrunner.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Utils {
    public static double toRad(double value) {
        return value * Math.PI / 180;
    }

    public static double distHaversine(double v1Lat,double v1Lon,double v2Lat,double v2Lon) {
        int R = 6371;
        double lat1, lat2, lon1, lon2;
        double latDist, lonDist;
        double a, c;

        lat1 = v1Lat;
        lon1 = v1Lon;
        lat2 = v2Lat;
        lon2 = v2Lon;

        latDist = toRad(lat2 - lat1);
        lonDist = toRad(lon2 - lon1);

        a = Math.sin(latDist / 2) * Math.sin(latDist / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * Math.sin(lonDist / 2) * Math.sin(lonDist / 2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }


    public static double routeCost2Opt(List<Vertex> route){
        double cost=0;
        int n=route.size();
        for(int i=0;i<n-1;i++){
            cost+=Vertex.distHaversine(route.get(i),route.get(i+1));
        }
        cost+=Vertex.distHaversine(route.get(0),route.get(n-1));
        return cost;
    }

    public static void twoOpt(List<Vertex> vertexTour){
        double bestCost, newCost;
        //También se creara un Tour para manejar el ordenamiento
        List<Vertex> tempTour = new ArrayList<>(vertexTour);
        //tempTour.add(0,null);
        bestCost = routeCost2Opt(vertexTour);
        int n = vertexTour.size();
        for(int i=1; i<n-2; i++){
            for(int j=i+1; j<n+1; j++){
                if(j-i == 1) continue;
                List<Vertex> auxVertex = new ArrayList<>(vertexTour.subList(i,j));
                //List<Delivery> auxDelivery = new ArrayList<>(tempTour.subList(i,j));
                Collections.reverse(auxVertex);
                //Collections.reverse(auxDelivery);
                auxVertex.addAll(vertexTour.subList(j,n));
               // auxDelivery.addAll(tempTour.subList(j,n));
                List<Vertex> newVertexTour = new ArrayList<>(vertexTour.subList(0,i));
                //List<Delivery> newDeliveryTour = new ArrayList<>(tempTour.subList(0,i));
                newVertexTour.addAll(auxVertex);
                //newDeliveryTour.addAll(auxDelivery);
                newCost = routeCost2Opt(newVertexTour);
                if(newCost < bestCost){
                    bestCost = newCost;
                    tempTour = new ArrayList<>(newVertexTour);
                }
            }
        }
        vertexTour.clear();
        vertexTour.addAll(tempTour);
        //The depot is deleted
        //vertexTour.remove(0);
    }

}
