/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class Subir {

    public static void SubirArchivoConForce(Scanner in, MongoCollection<Document> coleccio, String ruta) {
        try {
            File file = new File(ruta);
            byte[] data = Files.readAllBytes(file.toPath());
            //Guardamos la ultima fecha de modificación del archivo.
            Date fechaLocal = new Date(file.lastModified());
            //Busca en la coleccion el archivo.
            Document query = new Document("nombre", file.getName());
            Document archivo = coleccio.find(query).first();
            
            //Si el archivo existe en la colección
            if (archivo != null) {
                //coje la fecha del archivo remoto.
                Date fechaRemota = archivo.getDate("fecha_modificacion");
                //Mira si la fecha local es mas actualizada que la fecha del archivo subido.
                if (fechaLocal.after(fechaRemota)) {
                    // Actualiza el archivo en la colección con el contenido de la nueva versión
                    Document update = new Document("$set", new Document("contenido", data)
                            .append("fecha_modificacion", fechaLocal));
                    coleccio.updateOne(query, update);
                    System.out.println("Archivo actualizado.");
                } else {
                    System.out.println("El archivo está actualizado.");
                }
            } else {
                // Inserta el archivo a la colección
                Document document = new Document("nombre", file.getName())
                        .append("contenido", data)
                        .append("fecha_modificacion", fechaLocal);
                coleccio.insertOne(document);
                System.out.println("Archivo insertado en el Repositorio Remoto.");
            }

        } catch (Exception ex) {

        }
    }

    public static void SubirArchivoSinForce(Scanner in, MongoCollection<Document> coleccio, File rutarchivo) {
        try {
            byte[] data = Files.readAllBytes(rutarchivo.toPath());
            Date fechaLocal = new Date(rutarchivo.lastModified());

            Document query = new Document("nombre", rutarchivo.getName());
            Document archivo = coleccio.find(query).first();
            if (archivo != null) {
                // Actualiza el archivo en la colección con el contenido de la nueva versión
                Document update = new Document("$set", new Document("contenido", data));
                coleccio.updateOne(query, update);
                System.out.println("Archivo actualizado en la base de datos.");
            } else {
                // Inserta el archivo a la colección
                Document document = new Document("nombre", rutarchivo.getName())
                        .append("contenido", data);
                coleccio.insertOne(document);
                System.out.println("Archivo insertado en la base de datos.");
            }

        } catch (Exception ex) {

        }
    }
}
