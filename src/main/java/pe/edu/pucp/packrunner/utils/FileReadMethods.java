package pe.edu.pucp.packrunner.utils;

import pe.edu.pucp.packrunner.models.*;
import pe.edu.pucp.packrunner.models.enumerator.Fleet;
import pe.edu.pucp.packrunner.models.enumerator.NaturalRegion;
import pe.edu.pucp.packrunner.models.enumerator.SimulationType;

import java.io.FileNotFoundException;
import java.util.*;

import java.io.*;

public class FileReadMethods {

    public static void readRegions(String filename,
                                   List<Region> regions) throws FileNotFoundException {
        regions.clear();
        // Filename: "inf226.oficinas.txt"
        Scanner sc = new Scanner(new File((filename)));
        sc.useLocale(Locale.ENGLISH);
        sc.useDelimiter("\\s*,\\s*|\\s*\\n\\s*");
        while (sc.hasNext()) {
            // Variables Inicializadas
            String regionName, provinceName, ubigeo;
            NaturalRegion naturalRegion;
            double latitude;
            double longitude;

            // Se lee el archivo
            ubigeo = sc.next();
            regionName = sc.next();
            provinceName = sc.next();
            latitude = sc.nextDouble();
            longitude = sc.nextDouble();
            String strNatReg = sc.next();
            naturalRegion = NaturalRegion.valueOf(strNatReg);

            // Busco la region leida en mi arreglo de regiones, si no lo encuentra, se crea
            // una region nueva
            Optional<Region> searchRegion = regions.stream().filter(reg -> regionName.equals(reg.getName())).findAny();
            Region region;
            if (searchRegion.isEmpty()) {
                // Region Assignment
                region = new Region();
                region.setName(regionName);
                regions.add(region);
            }
        }
        sc.close();
    }

    public static void readVertexes(String filename,
                                    List<Region> regions,
                                    List<Province> provinces,
                                    List<Vertex> offices,
                                    List<Vertex> depots) throws FileNotFoundException {
        provinces.clear();
        offices.clear();
        depots.clear();

        // Filename: "inf226.oficinas.txt"
        Scanner sc = new Scanner(new File((filename)));
        sc.useLocale(Locale.ENGLISH);
        sc.useDelimiter("\\s*,\\s*|\\s*\\n\\s*");
        while (sc.hasNext()) {
            // Variables Inicializadas
            String regionName, provinceName, ubigeo;
            NaturalRegion naturalRegion;
            double latitude;
            double longitude;

            // Se lee el archivo
            ubigeo = sc.next();
            regionName = sc.next();
            provinceName = sc.next();
            latitude = sc.nextDouble();
            longitude = sc.nextDouble();
            String strNatReg = sc.next();
            naturalRegion = NaturalRegion.valueOf(strNatReg);

            // Busco la region leida en mi arreglo de regiones, si no lo encuentra, se crea una region nueva
            Optional<Region> searchRegion = regions.stream().filter(reg -> regionName.equals(reg.getName())).findAny();
            Region region;
            if (searchRegion.isPresent()) {
                region = searchRegion.get();
            } else {
                // Region Assignment
                region = new Region();
                region.setName(regionName);
                regions.add(region);
            }

            // Province Assignment
            Province province = new Province();
            province.setName(provinceName);
            province.setRegion(region);
            province.setUbiGeo(ubigeo);
            province.setNaturalRegion(naturalRegion);
            provinces.add(province);

            // Depot Assignment
            // Estos Ubigeos corresponden a los almacenes (150101 = Lima, 130101 = Trujillo, 040101 = Arequipa)
            if (ubigeo.contains("150101") || ubigeo.contains("130101") || ubigeo.contains("040101")) {
                Vertex depot = new Vertex();
                depot.setType(0);
                depot.setLatitude(latitude);
                depot.setLongitude(longitude);
                depot.setProvince(province);
                depot.setAddress("");
                depot.setName(depot.getProvince().getName() + " DEPOT");
                depot.setX(Mercator.xAxisProjection(longitude));
                depot.setY(Mercator.yAxisProjection(latitude));

                depots.add(depot);
            }
            // Office Assignment
            else {
                Vertex office = new Vertex();
                office.setType(1);
                office.setLatitude(latitude);
                office.setLongitude(longitude);
                office.setProvince(province);
                office.setAddress("");
                office.setName(office.getProvince().getName() + " OFFICE");
                office.setX(Mercator.xAxisProjection(longitude));
                office.setY(Mercator.yAxisProjection(latitude));

                offices.add(office);
            }
        }
        sc.close();
    }

    public static void readEdges(String filename,
                                 List<Edge> edges,
                                 List<Vertex> offices,
                                 List<Vertex> depots) throws FileNotFoundException {
        edges.clear();
        // Filename = "inf226.tramos.v.2.0.txt"
        Scanner sc = new Scanner(new File((filename)));
        sc.useDelimiter("\\s*=>\\s*|\\s*\\n\\s*");
        while (sc.hasNext()) {
            String ubigeo1, ubigeo2;

            ubigeo1 = sc.next();
            ubigeo2 = sc.next();

            Vertex a = findVertexByUbigeo(ubigeo1, offices, depots);
            Vertex b = findVertexByUbigeo(ubigeo2, offices, depots);

            // Me salto una línea para no repetir tramos bidireccionales
            sc.next();
            sc.next();

            Edge edge = new Edge(a, b);
            edges.add(edge);
        }
        sc.close();
    }

    public static void readTrucks(String filename,
                                  List<Truck> trucks,
                                  List<Vertex> depots) throws FileNotFoundException {
        trucks.clear();
        // Filename: "inf226.camiones.txt"
        Scanner sc = new Scanner(new File((filename)));
        sc.useDelimiter("\\s*,\\s*|\\s*\\n\\s*");
        while (sc.hasNext()) {

            String code = sc.next();
            Fleet fleet = Fleet.valueOf(code.substring(0, 1));

            String provinceName = sc.next().toUpperCase();
            Vertex depot = findDepotByProvinceName(provinceName, depots);

            Truck truck = new Truck(code, fleet, depot);
            trucks.add(truck);
        }
        sc.close();
    }

    public static void readOrders(String filename,
                                  List<Order> orders,
                                  List<Vertex> offices,
                                  List<Vertex> depots,
                                  List<Client> clients) throws FileNotFoundException {

        // Filename: inf226.ventasYYYYMM.txt donde MM es el mes y YYYY es el año
        Scanner sc = new Scanner(new File((filename)));
        sc.useDelimiter("\\s*,\\s*|\\s*=>\\s*|\\s*:\\s*| |\\s*\\n\\s*");
        while (sc.hasNext()) {

            int date = Integer.parseInt(sc.next());
            int hourOfDay = Integer.parseInt(sc.next());
            int minutes = Integer.parseInt(sc.next());
            String depotGeo = sc.next();
            String officeGeo = sc.next();
            int numPackages = Integer.parseInt(sc.next());
            String clientCode = sc.next();

            Vertex depot = findVertexByUbigeo(depotGeo, offices, depots);
            Vertex office = findVertexByUbigeo(officeGeo, offices, depots);

            Client client;
            client = clients.stream()
                    .filter(c -> clientCode.equals(c.getCode()))
                    .findAny()
                    .orElse(null);
            if (client == null) {
                client = new Client();
                client.setCode(clientCode);
                clients.add(client);
            }

            int year = Integer.parseInt(filename.substring(20, 24));
            int month = Integer.parseInt(filename.substring(24, 26)) - 1;
            Calendar c = Calendar.getInstance();
            c.set(year, month, date, hourOfDay, minutes);
            Date orderDate = c.getTime();
            Order order = new Order(depot, office, client, numPackages, orderDate, SimulationType.SEVEN);
            orders.add(order);
        }
        sc.close();
    }

    public static void readBlocks(String filename,
                                  List<Block> blocks,
                                  List<Edge> edges) throws FileNotFoundException {

        // Filename: inf226.bloqueo.XX.txt donde XX es el mes
        Scanner sc = new Scanner(new File((filename)));
        sc.useDelimiter("\\s*,\\s*|\\s*=>\\s*|\\s*:\\s*| |\\s*\\n\\s*|\\s*;\\s*|\\s*==\\s*");
        while (sc.hasNext()) {
            String ubigeo1 = sc.next();
            String ubigeo2 = sc.next();
            String startDate = sc.next();
            int startMonth = Integer.parseInt(startDate.substring(0, 2));
            int startDay = Integer.parseInt(startDate.substring(2, 4));
            int startHour = Integer.parseInt(sc.next());
            int startMinutes = Integer.parseInt(sc.next());
            String endDate = sc.next();
            int endMonth = Integer.parseInt(endDate.substring(0, 2));
            int endDay = Integer.parseInt(endDate.substring(2, 4));
            int endHour = Integer.parseInt(sc.next());
            int endMinutes = Integer.parseInt(sc.next());

            Calendar startCalendar = Calendar.getInstance();
            Calendar endCalendar = Calendar.getInstance();

            startCalendar.set(2022, startMonth - 1, startDay, startHour, startMinutes);
            endCalendar.set(2022, endMonth - 1, endDay, endHour, endMinutes);

            Block block = new Block();

            block.setStartDate(startCalendar.getTime());
            block.setEndDate(endCalendar.getTime());
            Edge edge = findEdgeByUbigeos(ubigeo1, ubigeo2, edges);

            block.setEdge(edge);

            blocks.add(block);
        }
        sc.close();
    }

    public static void readMaintenances(String filename,
                                        List<Maintenance> maintenances,
                                        List<Truck> trucks) throws FileNotFoundException {
        // Filename: inf226.plan.mant.trim.abr.may.jun.txt
        Scanner sc = new Scanner(new File((filename)));
        sc.useDelimiter("\\s*:\\s*|\\s*\\n\\s*");
        while (sc.hasNext()) {
            String date = sc.next();
            String truckCode = sc.next();

            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(4, 6));
            int day = Integer.parseInt(date.substring(6, 8));

            for (int i = 0; i < 4; i++) {

                int addedMonths = 3 * i - 3;

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, day);
                calendar.add(Calendar.MONTH, addedMonths);

                Truck truck = findTruckbyCode(truckCode, trucks);

                Maintenance maintenance = new Maintenance();
                maintenance.setDate(calendar.getTime());
                maintenance.setTruck(truck);

                maintenances.add(maintenance);
            }
        }
        sc.close();
        maintenances.sort(Maintenance.DateComparator);
        // maintenances.sort(Maintenance.TruckCodeComparator);
    }

    public static Vertex findVertexByUbigeo(String ubigeo,
                                            List<Vertex> offices,
                                            List<Vertex> depots) {
        Vertex v = offices.stream()
                .filter(office -> ubigeo.equals(office.getProvince().getUbiGeo()))
                .findAny()
                .orElse(null);
        if (v == null) {
            v = depots.stream()
                    .filter(depot -> ubigeo.equals(depot.getProvince().getUbiGeo()))
                    .findAny()
                    .orElse(null);
        }
        return v;
    }

    public static Vertex findDepotByProvinceName(String provinceName,
                                                 List<Vertex> depots) {
        Vertex d = depots.stream()
                .filter(depot -> provinceName.equals(depot.getProvince().getName()))
                .findAny()
                .orElse(null);
        return d;
    }

    public static Edge findEdgeByUbigeos(String ubigeo1, String ubigeo2,
                                         List<Edge> edges) {

        for (Edge edge : edges) {
            int matches = 0;
            for (Vertex vertex : edge.getVertexes()) {
                if (ubigeo1.contains(vertex.getProvince().getUbiGeo()) ||
                        ubigeo2.contains(vertex.getProvince().getUbiGeo()))
                    matches++;
                if (matches >= 2)
                    return edge;
            }
        }
        return null;
    }

    public static Truck findTruckbyCode(String truckCode, List<Truck> trucks) {
        Truck t = trucks.stream()
                .filter(truck -> truckCode.equals(truck.getCode()))
                .findAny()
                .orElse(null);
        return t;
    }
}
