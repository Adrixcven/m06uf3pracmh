/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import static Logica.Clonar.clonado;
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
/**
 *
 * Clase que contiene dos métodos para clonar una colección de MongoDB y
 * almacenarla en un directorio local.
 */
public class ClonarVisual {

    /**
     *
     * Método que comprueba si existe la colección especificada en la base de
     * datos MongoDB, y si es así, crea un directorio con el nombre de la
     * colección en el directorio raíz del usuario actual. Luego, llama al
     * método 'clonado' para realizar la clonación de los documentos.
     *
     * @param coleccion nombre de la colección que se desea clonar.
     * @param db base de datos MongoDB que contiene la colección a clonar.
     */
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
}
