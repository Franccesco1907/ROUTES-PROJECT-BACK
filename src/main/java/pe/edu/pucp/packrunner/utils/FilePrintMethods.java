package pe.edu.pucp.packrunner.utils;

import pe.edu.pucp.packrunner.models.Vertex;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilePrintMethods {
    public static void writeInCsv(List<Vertex> depots, List<Vertex> offices) {
        try {
            File file = new File("datosMapa.csv");
            file.createNewFile();
            FileWriter Escritor = new FileWriter(file.getName());
            PrintWriter out = new PrintWriter(Escritor, true);
            List<Vertex> vertexes = new ArrayList<>();
            for (int i = 0; i < depots.size(); i++) {
                vertexes.add(depots.get(i));
            }
            for (int i = 0; i < offices.size(); i++) {
                vertexes.add(offices.get(i));
            }
            for (int i = 0; i < vertexes.size(); i++) {
                String linea = String.format("{x:%f,y:%f,nombreProv:'%s'},",
                        vertexes.get(i).getX(), vertexes.get(i).getY(), vertexes.get(i).getProvince().getName());
                out.println(linea);
            }
            String linea = String.format("{top:%f,bot:%f,left:%f,right:%f}",
                    Vertex.getY_LIMIT_TOP(), Vertex.getY_LIMIT_BOT(), Vertex.getX_LIMIT_LEFT(),
                    Vertex.getX_LIMIT_RIGHT());
            out.println(linea);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static File WriteStringToFile(String data) {
        File file = new File("files/data1.txt");

        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            //convert string to byte array
            byte[] bytes = data.getBytes();
            //write byte array to file
            bos.write(bytes);
            bos.close();
            fos.close();
            System.out.print("Data written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
