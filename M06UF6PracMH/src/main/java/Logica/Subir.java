/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Archivodata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**
 *
 * Clase encargada de subir archivos a una base de datos MongoDB.
 */
public class Subir {

    /**
     *
     * Método para subir un archivo a la base de datos, permitiendo actualizarlo
     * si ya existe.
     *
     * @param in Scanner para entrada de datos por teclado.
     * @param coleccio Colección de la base de datos en la que se desea guardar
     * el archivo.
     * @param ruta Ruta del archivo que se desea subir.
     */
    public static void SubirArchivoSinForce(Scanner in, MongoCollection<Document> coleccio, String ruta) {
        try {
            File file = new File(ruta);
            BufferedReader lector = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String linea;
            if (file.getName().endsWith(".java") || file.getName().endsWith(".txt")
                    || file.getName().endsWith(".xml") || file.getName().endsWith(".html")) {
                while ((linea = lector.readLine()) != null) {
                    sb.append(linea);
                }
                lector.close();
                String string = sb.toString();
                //Guardamos la ultima fecha de modificación del archivo.
                Date fechaLocal = new Date(file.lastModified());
                //Busca en la coleccion el archivo.
                Document query = new Document("nom", file.getName());
                Document archivo = coleccio.find(query).first();

                //Si el archivo existe en la colección
                if (archivo != null) {
                    //coje la fecha del archivo remoto.
                    Date fechaRemota = archivo.getDate("fecha_modificacion");
                    //Mira si la fecha local es mas actualizada que la fecha del archivo subido.
                    if (fechaLocal.after(fechaRemota)) {
                        // Actualiza el archivo en la colección con el contenido de la nueva versión
                        Archivodata archivo1 = new Archivodata(file.getName(), fechaLocal, string);
                        coleccio.updateOne(query, Mapeig.updateDocument(archivo1));
                        System.out.println("Archivo actualizado.");
                    } else {
                        System.out.println("El archivo está actualizado.");
                    }
                } else {
                    // Inserta el archivo a la colección
                    Archivodata archivo1 = new Archivodata(file.getName(), fechaLocal, string);
                    coleccio.insertOne(Mapeig.setArchivoToDocument(archivo1));
                    System.out.println("Archivo insertado en el Repositorio Remoto.");
                }
            } else {
                System.out.println("El archivo no es de tipos .java, .txt, .xml o .html.");
            }
        } catch (Exception ex) {

        }
    }

    /**
     *
     * Método para subir un archivo a la base de datos, sin permitir
     * actualizaciones si ya existe.
     *
     * @param in Scanner para entrada de datos por teclado.
     *
     * @param coleccio Colección de la base de datos en la que se desea guardar
     * el archivo.
     *
     * @param rutarchivo Archivo que se desea subir.
     */
    public static void SubirArchivoConForce(Scanner in, MongoCollection<Document> coleccio, String ruta) {
        try {
            File rutarchivo = new File(ruta);
            BufferedReader lector = new BufferedReader(new FileReader(rutarchivo));
            StringBuilder sb = new StringBuilder();
            String linea;
            if (rutarchivo.getName().endsWith(".java") || rutarchivo.getName().endsWith(".txt")
                    || rutarchivo.getName().endsWith(".xml") || rutarchivo.getName().endsWith(".html")) {
                while ((linea = lector.readLine()) != null) {
                    sb.append(linea);
                }
                lector.close();
                String string = sb.toString();
                Date fechaLocal = new Date(rutarchivo.lastModified());

                Document query = new Document("nom", rutarchivo.getName());
                Document archivo = coleccio.find(query).first();
                if (archivo != null) {
                    // Actualiza el archivo en la colección con el contenido de la nueva versión
                    Archivodata archivo1 = new Archivodata(rutarchivo.getName(), fechaLocal, string);
                    coleccio.updateOne(query, Mapeig.updateDocument(archivo1));
                    System.out.println("Archivo actualizado en la base de datos.");
                } else {
                    // Inserta el archivo a la colección
                    Archivodata archivo1 = new Archivodata(rutarchivo.getName(), fechaLocal, string);
                    coleccio.insertOne(Mapeig.setArchivoToDocument(archivo1));
                    System.out.println("Archivo insertado en la base de datos.");
                }
            } else {
                System.out.println("El archivo no es de tipos .java, .txt, .xml o .html.");
            }

        } catch (IOException ex) {

        }
    }

    public static void esDirectorio(Scanner in, MongoCollection<Document> coleccio, String ruta) {
        File file = new File(ruta);
        if (file.isDirectory()) {
            File[] archivos = file.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    // hacer algo con el archivo
                    SubirArchivoConForce(in, coleccio, ruta);
                }
            }
        }
    }

    public static void esDirectorioNoForce(Scanner in, MongoCollection<Document> coleccio, String rutarchivo) {
        File file = new File(rutarchivo);
        if (file.isDirectory()) {
            File[] archivos = file.listFiles();
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    // hacer algo con el archivo
                    SubirArchivoSinForce(in, coleccio, rutarchivo);
                }
            }
        } else {
            SubirArchivoSinForce(in, coleccio, rutarchivo);
        }
    }
}
