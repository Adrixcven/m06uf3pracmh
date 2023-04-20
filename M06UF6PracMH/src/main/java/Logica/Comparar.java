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

    /*public static void compararSinDetalles(MongoDatabase db, String nombreArchivoRemoto, String rutaArchivoLocal) {

        File archivoLocal = new File(rutaArchivoLocal);

        // Si el archivo local no existe, informamos al usuario y salimos de la función
        if (!archivoLocal.exists()) {
            System.out.println("El archivo local " + rutaArchivoLocal + " no existe.");
            return;
        }

        MongoCollection<Document> coleccion = db.getCollection("archivos");
        Document archivoRemoto = coleccion.find(new Document("nombre", nombreArchivoRemoto)).first();

        // Si el archivo remoto no existe, informamos al usuario y salimos de la función
        if (archivoRemoto == null) {
            System.out.println("El archivo remoto " + nombreArchivoRemoto + " no existe.");
            return;
        }

        Date timestampLocal = new Date(archivoLocal.lastModified());
        Date timestampRemoto = archivoRemoto.getDate("timestamp");

        // Si los timestamps son iguales, comparamos los contenidos
        if (Objects.equals(timestampLocal, timestampRemoto)) {
            try ( FileInputStream fisLocal = new FileInputStream(archivoLocal)) {
                byte[] contenidoLocal = fisLocal.readAllBytes();
                byte[] contenidoRemoto = archivoRemoto.getBinary("contenido").getData();
                if (Objects.equals(contenidoLocal, contenidoRemoto)) {
                    System.out.println("El archivo local " + rutaArchivoLocal + " y el remoto " + nombreArchivoRemoto + " son iguales.");
                } else {
                    System.out.println("El archivo local " + rutaArchivoLocal + " y el remoto " + nombreArchivoRemoto + " son distintos.");
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo local: " + e.getMessage());
            }
        } else {
            System.out.println("El archivo local " + rutaArchivoLocal + " y el remoto " + nombreArchivoRemoto + " tienen timestamps distintos.");
        }
    }*/
}
