/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class Clonar {

/**
     *
     * Método que recorre todos los documentos de la colección especificada,
     * selecciona aquellos que han sido modificados después de una fecha dada
     * por el usuario, y los almacena en archivos dentro del directorio creado
     * por el método 'clonar'.
     *
     * @param coleccion nombre de la colección que se desea clonar.
     * @param db base de datos MongoDB que contiene la colección a clonar.
     * @param directorio ruta del directorio local donde se almacenarán los
     * archivos clonados.
     */
    public static void clonado(String coleccion, MongoDatabase db, Path directorio) {

        //2. Seleccionar documentos por fecha
        Scanner sc = new Scanner(System.in);
        LocalDateTime fechaModificacion = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.print("Introduce la fecha de modificación (yyyy-MM-dd HH:mm:ss): ");
            String fechaModificacionStr = sc.nextLine();
            if (fechaModificacionStr.isEmpty()) {
                System.out.println("Debes introducir una fecha.");
                continue;
            }
            try {
                fechaModificacion = LocalDateTime.parse(fechaModificacionStr, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Fecha introducida incorrecta. Debe estar en formato yyyy-MM-dd HH:mm:ss");
            }
        }

        MongoCollection<Document> coleccionMongo = db.getCollection(coleccion);
        MongoCursor<Document> cursor = coleccionMongo.find().iterator();

        boolean creadoCorrectamente = false;
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            LocalDateTime fechaDoc = doc.getDate("Fecha de modificación").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            //Comprobar si el documento está dentro del rango de fechas
            if (fechaDoc.isBefore(fechaModificacion) || fechaDoc.isEqual(fechaModificacion)) {

                //3. Crear archivo y escribir contenido
                String nombreArchivo = doc.getString("nom");

                try {
                    Path archivo = directorio.resolve(nombreArchivo);
                    Files.createFile(archivo);
                    Files.writeString(archivo, doc.getString("contenido"));
                    creadoCorrectamente = true;
                } catch (IOException | NullPointerException e) {
                    creadoCorrectamente = false;
                    break;
                }
            }
        }
        if (creadoCorrectamente == true) {
            System.out.println("Se ha clonado correctamente el directorio.");
        } else {
            System.out.println("Ha habido un problema clonando el repositorio.");
            try {
                Files.delete(directorio);
            } catch (IOException e) {
            }
        }
    }
}

