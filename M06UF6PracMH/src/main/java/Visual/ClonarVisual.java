/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class ClonarVisual {

    public static void clonar(String coleccion, MongoDatabase db) {
        Scanner sc = new Scanner(System.in);

        // Comprobar si existe la colección
        if (!db.listCollectionNames().into(new ArrayList<>()).contains(coleccion)) {
            System.out.println("La colección no existe.");
            return;
        }

        // 1. Crear directorio con el nombre de la colección
        Path directorio = Paths.get(System.getProperty("user.home"), coleccion);

        boolean creado = false;
        while (!creado) {
            try {
                Files.createDirectory(directorio);
                clonado(coleccion, db, directorio);
                creado = true;
            } catch (FileAlreadyExistsException e) {
                System.out.println("El directorio ya existe.");
                creado = true; // Salimos del bucle ya que el directorio ya existe
            } catch (IOException e) {
                System.out.println("Se ha producido un error al crear el directorio.");
                return;
            }
        }

    }

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
                Path archivo = directorio.resolve(nombreArchivo);

                try {
                    Files.createFile(archivo);
                    Files.writeString(archivo, doc.getString("contenido"));
                    creadoCorrectamente = true;
                } catch (IOException e) {
                    System.out.println("Error al clonar el repositorio ");
                    creadoCorrectamente = false;
                    break;
                }
            }
        }
        if (creadoCorrectamente == true) {
            System.out.println("Se ha clonado correctamente el directorio.");
        } else {
            System.out.println("Ha habido un problema clonando el repositorio.");
            try{
            Files.delete(directorio);
            } catch (IOException e){
            }
        }
    }

}
