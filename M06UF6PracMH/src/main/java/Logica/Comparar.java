/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.bson.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Adrix
 */
public class Comparar {

    public static void compararConDetalles(String rep, String ruta, MongoDatabase bbdd) {
        MongoCollection<Document> coleccio = bbdd.getCollection(rep);
        Document doc = coleccio.find(new Document("nombre", rep)).first();
        if (doc == null) {
            System.out.println("No se encontró el repositorio remoto");
            return;
        }
        byte[] contenidoRemoto = (byte[]) doc.get("contenido");
        Path path = Paths.get(ruta);
        try {
            byte[] contenidoLocal = Files.readAllBytes(path);
            if (contenidoLocal.length != contenidoRemoto.length) {
                System.out.println("Los archivos no tienen el mismo tamaño");
                return;
            }
            boolean sonIguales = true;
            for (int i = 0; i < contenidoLocal.length; i++) {
                if (contenidoLocal[i] != contenidoRemoto[i]) {
                    System.out.printf("Los archivos son diferentes en la posición %d%n", i);
                    sonIguales = false;
                }
            }
            if (sonIguales) {
                System.out.println("Los archivos son iguales");
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo local");
        }
    }

    public static void compararSinDetalles(MongoDatabase database, String nombreArchivoRemoto, String rutaArchivoLocal) {
        MongoCollection<Document> collection = database.getCollection("archivos");

        File archivoLocal = new File(rutaArchivoLocal);

        if (!archivoLocal.exists()) {
            System.out.println("El archivo local no existe.");
            return;
        }

        Document archivoRemoto = collection.find(new Document("nombre", nombreArchivoRemoto)).first();

        if (archivoRemoto == null) {
            System.out.println("El archivo remoto no existe.");
            return;
        }

        long timestampRemoto = archivoRemoto.getLong("timestamp");
        long timestampLocal = archivoLocal.lastModified();

        if (timestampLocal == timestampRemoto) {
            String contenidoRemoto = archivoRemoto.getString("contenido");

            try {
                byte[] contenidoLocalBytes = Files.readAllBytes(Paths.get(rutaArchivoLocal));
                String contenidoLocal = new String(contenidoLocalBytes);

                if (Objects.equals(contenidoRemoto, contenidoLocal)) {
                    System.out.println("El local y el remoto tienen exactamente el mismo timestamp y son iguales.");
                } else {
                    System.out.println("El local y el remoto tienen exactamente el mismo timestamp, pero NO son iguales.");
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo local.");
                e.printStackTrace();
            }
        } else {
            System.out.println("El local y el remoto NO tienen el mismo timestamp o NO tienen el mismo contenido.");
        }
    }
}
