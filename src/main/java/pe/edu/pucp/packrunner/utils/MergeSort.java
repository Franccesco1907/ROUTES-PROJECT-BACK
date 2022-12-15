package pe.edu.pucp.packrunner.utils;

import pe.edu.pucp.packrunner.models.Order;
import pe.edu.pucp.packrunner.models.Truck;

import java.util.ArrayList;
import java.util.Collections;

public class MergeSort {
    private static void mergeOrder(ArrayList<Order> source, ArrayList<Double> priority, int ini, int mid, int end) {
        int i,j,z,end1,end2;

        end1=mid-ini+1;
        end2=end-(mid+1)+1;

        double[] aux1=new double[end1+1];
        double[] aux2=new double[end2+1];
        ArrayList<Order> auxSource1=new ArrayList<>(Collections.nCopies(end1+1,null));
        ArrayList<Order> auxSource2=new ArrayList<>(Collections.nCopies(end2+1,null));

        aux1[end1]=Double.MAX_VALUE;
        aux2[end2]=Double.MAX_VALUE;

        //Copia el lado izquierdo
        for(i=ini,j=0;j<end1;i++,j++){
            aux1[j]=priority.get(i);
            auxSource1.set(j,source.get(i));
        }
        //Copia el lado derecho
        for(i=mid+1,j=0;j<end2;i++,j++){
            aux2[j]=priority.get(i);
            auxSource2.set(j,source.get(i));
        }


        i=j=0;
        for(z=ini;i<end1 || j<end2;z++){
            if(aux1[i]<aux2[j]){
                priority.set(z,aux1[i]);
                source.set(z,auxSource1.get(i));
                i++;
            }
            else{
                priority.set(z,aux2[j]);
                source.set(z,auxSource2.get(j));
                j++;
            }
        }
    }

    public static void mergeSortOrder(ArrayList<Order> source,ArrayList<Double> priority,int ini,int end) {
        if (ini==end) {
            return;
        }
        int mid=(ini+end)/2;

        mergeSortOrder(source,priority,ini,mid);
        mergeSortOrder(source,priority,mid+1,end);

        mergeOrder(source,priority,ini,mid,end);
    }

    private static void mergeTruck(ArrayList<Truck> source, ArrayList<Double> priority, int ini, int mid, int end) {
        int i,j,z,end1,end2;

        end1=mid-ini+1;
        end2=end-(mid+1)+1;

        double[] aux1=new double[end1+1];
        double[] aux2=new double[end2+1];
        ArrayList<Truck> auxSource1=new ArrayList<>(Collections.nCopies(end1+1,null));
        ArrayList<Truck> auxSource2=new ArrayList<>(Collections.nCopies(end2+1,null));

        aux1[end1]=Double.MAX_VALUE;
        aux2[end2]=Double.MAX_VALUE;

        //Copia el lado izquierdo
        for(i=ini,j=0;j<end1;i++,j++){
            aux1[j]=priority.get(i);
            auxSource1.set(j,source.get(i));
        }
        //Copia el lado derecho
        for(i=mid+1,j=0;j<end2;i++,j++){
            aux2[j]=priority.get(i);
            auxSource2.set(j,source.get(i));
        }

        i=j=0;
        for(z=ini;i<end1 || j<end2;z++){
            if(aux1[i]<aux2[j]){
                priority.set(z,aux1[i]);
                source.set(z,auxSource1.get(i));
                i++;
            }
            else{
                priority.set(z,aux2[j]);
                source.set(z,auxSource2.get(j));
                j++;
            }
        }
    }

    public static void mergeSortTruck(ArrayList<Truck> source,ArrayList<Double> priority,int ini,int end) {
        if (ini==end) {
            return;
        }
        int mid=(ini+end)/2;

        mergeSortTruck(source,priority,ini,mid);
        mergeSortTruck(source,priority,mid+1,end);

        mergeTruck(source,priority,ini,mid,end);
    }
}
