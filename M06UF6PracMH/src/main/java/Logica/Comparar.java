/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import org.bson.Document;

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

    public static void compararSinDetalles(String nombreRepo, String rutaArchivo, MongoDatabase db) {
        MongoCollection<Document> coleccion = db.getCollection(nombreRepo);

        Document query = new Document();
        query.append("ruta", rutaArchivo);

        Document resultado = coleccion.find(query).first();

        if (resultado == null) {
            System.out.println("El archivo no se encuentra en el repositorio");
        } else {
            System.out.println("El archivo " + rutaArchivo + " está presente en el repositorio " + nombreRepo);
        }
    }
}
