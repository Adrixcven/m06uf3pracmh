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

    /*public static void compararSinDetalles(MongoDatabase bbdd, String repositoryPath, String filePathString, boolean lineDetail) {
        Scanner str = new Scanner(System.in);
        int option = -1;

        while (option != 0) {
            try {
                System.out.println("1. Comparar archivo");
                System.out.println("2. Comparar repositorio");
                System.out.println("3. Activar/desactivar detalle de línea/s");
                System.out.println("0. Atrás");
                option = str.nextInt();

                if (option == 1) {
                    //Concatena la ruta del archivo con la del repositorio.
                    Path absolutePath = Paths.get(repositoryPath, filePathString);
                    //Aplica la lógica del compare
                    compare(absolutePath, lineDetail, false);
                } else if (option == 2) {
                    System.out.println("Introduce la ruta del directorio partiendo del repositorio e incluyendo la primera barra: ");
                    System.out.println("Ejemplo: \\directorio, para subir todo el repositorio de manera recursiva, pulsar enter.");
                    String directoryPathString = str.nextLine();

                    //Concatena la ruta del archivo con la del repositorio.
                    Path absolutePath = Paths.get(repositoryPath, directoryPathString);
                    //Aplica la lógica del compare
                    compare(absolutePath, lineDetail, true);
                } else if (option == 3) {
                    if (!lineDetail) {
                        lineDetail = true;
                        System.out.println("Detalle de línea activado.");
                    } else {
                        lineDetail = false;
                        System.out.println("Detalle de línea desactivado.");
                    }
                } else if (option == 0) {
                    // Salir del bucle
                } else {
                    System.out.println("Opción introducida no válida.");
                }
            } catch (InputMismatchException ex) {
                str.nextLine();
                System.out.println("El valor debe ser numérico.");
            }
        }
    }*/
}
