/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import entidades.Archivodata;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.bson.Document;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Adrix
 */
public class Comparar {

    public static void compare(String dir_base, String ruta, boolean detail, MongoCollection<Document> collection) throws IOException {
        try {
            File file = new File(ruta);
            if (file.exists()) {
                BufferedReader lector = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String linea;
                if (file.getName().endsWith(".java") || file.getName().endsWith(".txt")
                        || file.getName().endsWith(".xml") || file.getName().endsWith(".html")) {
                    long tamano = file.length();
                    if (tamano < 10485760) {
                        while ((linea = lector.readLine()) != null) {
                            sb.append(linea);
                        }
                        lector.close();
                        String string = sb.toString();
                        //Guardamos la ultima fecha de modificación del archivo.
                        Date fechaLocal = new Date(file.lastModified());
                        //Busca en la coleccion el archivo.
                        Document query = new Document("nom", file.getName());
                        MongoCursor<Document> cursor = collection.find(query).iterator();
                        while (cursor.hasNext()) {
                            Document result = cursor.next();
                            if (result.containsKey("Fecha de modificación") && result.containsKey("contenido")) {
                                if (result != null) {
                                    Archivodata remoto = new Archivodata(result.getString("nom"), result.getDate("Fecha de modificación"), result.getString("contenido"));
                                    Archivodata local = new Archivodata(file.getName(), fechaLocal, string);
                                    if (remoto.getTiempo().equals(local.getTiempo()) && remoto.getContenido().equals(local.getContenido())) {
                                        System.out.println("El local i el remot tenen exactament el mateix timestamp, són iguals.");
                                    } else if (remoto.getTiempo() != local.getTiempo()) {
                                        System.out.println("El local i el remot NO tenen el mateix timestamp o bé NO tenen el mateix contingut. És a dir, són diferents.");
                                    } else {
                                        System.out.println("Error=");
                                    }
                                } else {
                                    System.out.println("El archivo remoto no existe");
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("No es un .java, .html, .txt o .xml");
                }
            } else {
                System.out.println("El archivo no existe");
            }

        } catch (IOException e) {
            System.out.println("error de escritura");
        }
    }

    public static void esDirectorio(String dir_base, String ruta, boolean detail, MongoCollection<Document> collection) {

        File file = new File(ruta);
        if (file.isDirectory()) {
            File[] archivos = file.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    // hacer algo con el archivo
                    try {
                        String rutaArchivo = ruta + "\\" + archivo.getName();
                        Comparar.compare(dir_base, rutaArchivo, false, collection);
                    } catch (Exception e) {

                    }
                }
            }
        } else {
            try {
                Comparar.compare(dir_base, ruta, false, collection);
            } catch (Exception e) {
            }

        }
    }
}
