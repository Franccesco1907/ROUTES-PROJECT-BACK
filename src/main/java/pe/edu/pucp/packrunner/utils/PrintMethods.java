package pe.edu.pucp.packrunner.utils;

import pe.edu.pucp.packrunner.models.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrintMethods {

    public static void printOffices(List<Vertex> offices) {
        System.out.println("OFFICES");
        printLine(120, "-");
        for (Vertex office : offices) {
            System.out.println(office);
        }
        System.out.println("TOTAL OFFICES: " + offices.size() + "\n");
    }

    public static void printDepots(List<Vertex> depots) {
        System.out.println("DEPOTS");
        printLine(120, "-");
        for (Vertex depot : depots) {
            System.out.println(depot);
        }
        System.out.println("TOTAL DEPOTS: " + depots.size() + "\n");
    }

    public static void printEdges(List<Edge> edges) {
        System.out.println("EDGES");
        printLine(120, "-");
        for (Edge edge : edges) {
            System.out.println(edge);
        }
        System.out.println("TOTAL EDGES: " + edges.size() + "\n");
    }

    public static void printTrucks(List<Truck> trucks) {
        System.out.println("TRUCKS");
        printLine(120, "-");
        for (Truck truck : trucks)
            System.out.println(truck);
        printLine(120, "-");
        System.out.println("TOTAL TRUCKS: " + trucks.size() + "\n");
    }

    public static void printOrders(List<Order> orders) {
        System.out.println("ORDERS");
        printLine(150, "-");
        int totalPackages = 0;
        int deliveredOrders = 0;
        int lateOrders = 0;
        for (Order order : orders) {
            totalPackages += order.getNumPackages();
            deliveredOrders += order.isDelivered()?1:0;
            lateOrders += !order.isOnTime()?1:0;
            System.out.println(order);
        }
        printLine(150, "-");
        System.out.println(
                "Total Orders: " + orders.size() +
                " -- Total Packages: " + totalPackages +
                " -- Delivered Orders: " + deliveredOrders +
                " -- Late Orders: " + lateOrders);
        System.out.println();
    }

    public static void printBlocks(List<Block> blocks) {
        System.out.println("BLOCKS");
        printLine(120, "-");
        for (Block block : blocks) {
            System.out.println(block);
        }
        System.out.println("NUMBER OF SCHEDULED BLOCKS: " + blocks.size());
    }

    public static void printMaintenances(List<Maintenance> maintenances) {
        System.out.println("MAINTENANCES");
        printLine(120, "-");
        for (Maintenance maintenance : maintenances) {
            System.out.println(maintenance);
        }
        System.out.println("NUMBER OF PLANNED MAINTENANCES: " + maintenances.size());
    }

    public static void printTruckPlans(List<TruckPlan> truckPlans) {
        for(TruckPlan truckPlan : truckPlans)
            if(truckPlan.isTravelling())
                System.out.println(truckPlan);
    }

    public static void printDeliveries(List<Delivery> deliveries) {
        printLine(150, "=");
        for (Delivery d : deliveries)
            System.out.println(d);
        printLine(150, "-");
    }

    public static String printDDMMYYYYDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return String.format("%02d/%02d/%04d", day, month, year);
    }

    public static String printDDMMHHssDate(Date date) {
        if (date == null) return "N/A       ";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return String.format("%02d/%02d %02d:%02d", day, month, hour, minutes);
    }

    public static String printDDMMYYYYHHssDate(Date date) {
        if (date == null) return "dd/MM/YYYY HH:mm";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return String.format("%02d/%02d/%04d %02d:%02d", day, month, year, hour, minutes);
    }

    public static void printLine(int length, String s) {
        for (int i = 0; i < length; i++)
            System.out.print(s);
        System.out.println();
    }

}
