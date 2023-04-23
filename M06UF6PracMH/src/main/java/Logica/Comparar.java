/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entidades.Archivodata;
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

    /*public static void compararConDetalles(Path absolutePath, boolean showDetail, boolean recursive) {

        try {

            if (!absolutePath.toFile().exists()) {
                System.out.println("El fichero o directorio local no existe");
                return;
            }

            if (absolutePath.toFile().isDirectory()) {
                if (!recursive) {
                    System.out.println("Es un directorio, por favor indique el fichero a comparar o use la opción recursiva");
                    return;
                }

                Files.walkFileTree(absolutePath, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        compararConDetalles(file, showDetail, true);
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {

                Archivodata localFichero = filePathToFichero(absolutePath);
                Document remoteDocument = dataLayer.findDocumentByPath(absolutePath.toString());

                if (remoteDocument == null) {
                    System.out.println("El fichero " + absolutePath.getFileName().toString() + " remoto no existe");
                } else {

                    Archivodata remoteFichero = Mapper.convertDocumentToFichero(remoteDocument);

                    if (localFichero.getFechaUltimaModificacion().equals(remoteFichero.getTiempo()) || localFichero.getHash().equals(remoteFichero.getHash())) {
                        System.out.println("El fichero " + absolutePath.getFileName().toString() + " local y remoto son iguales");
                    } else {
                        System.out.println("El fichero " + absolutePath.getFileName().toString() + " local y remoto son diferentes");
                        if (showDetail) {
                            System.out.println("Comparando " + absolutePath.getFileName().toString() + "...");
                            compareLineByLineManelVersion(localFichero, remoteFichero);
                        }
                    }
                }
            }
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Error al intentar obtener el hash del documento, por favor contacte con su administrador: " + ex.getMessage());
        } catch (IOException e) {
            System.out.println("Error en la escritura o lectura del documento: " + e.getMessage());
        }
    }*/
    public static void compararSinDetalles(String rep, String rutaLocal, boolean detail, MongoDatabase bbdd) {
        File archivoLocal = new File(rutaLocal);
        if (!archivoLocal.exists()) {
            System.out.println("El archivo local no existe.");
            return;
        }
        // Obtener el contenido del archivo local
        String contenidoLocal = obtenerContenidoArchivo(archivoLocal);

        // Buscar el archivo remoto en la base de datos
        BasicDBObject query = new BasicDBObject();
        query.put("repositorio", rep);
        query.put("nombre", archivoLocal.getName());
        Document resultado = bbdd.getCollection("archivos").find(query).first();

        if (resultado == null) {
            System.out.println("El archivo remoto no existe.");
            return;
        }
        // Obtener el contenido del archivo remoto
        String contenidoRemoto = (String) resultado.get("contenido");

        // Comparar los timestamps
        long timestampLocal = archivoLocal.lastModified();
        long timestampRemoto = resultado.getLong("timestamp");
        if (timestampLocal == timestampRemoto) {
            System.out.println("El archivo local y el remoto tienen exactamente el mismo timestamp, son iguales.");
            return;
        }

        // Comparar los contenidos
        if (contenidoLocal.equals(contenidoRemoto)) {
            System.out.println("El archivo local y el remoto tienen distinto timestamp pero son iguales.");
            return;
        }

        System.out.println("El archivo local y el remoto NO tienen el mismo timestamp o NO tienen el mismo contenido.");

        // Si se activó el modo detalle, comparar línea a línea
        if (detail) {
            String[] lineasLocal = contenidoLocal.split("\\r?\\n");
            String[] lineasRemoto = contenidoRemoto.split("\\r?\\n");

            System.out.println("Diferencias de local a remoto:");
            for (int i = 0; i < lineasLocal.length; i++) {
                if (!buscarLineaEnArray(lineasLocal[i], lineasRemoto)) {
                    System.out.println("- Modificada o eliminada en línea " + (i + 1) + ": " + lineasLocal[i]);
                }
            }

            System.out.println("Diferencias de remoto a local:");
            for (int i = 0; i < lineasRemoto.length; i++) {
                if (!buscarLineaEnArray(lineasRemoto[i], lineasLocal)) {
                    System.out.println("- Agregada o modificada en línea " + (i + 1) + ": " + lineasRemoto[i]);
                }
            }
        }
    }

// Función auxiliar para obtener el contenido de un archivo
    public static String obtenerContenidoArchivo(File archivo) {
        StringBuilder contenido = new StringBuilder();
        try ( Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                contenido.append(scanner.nextLine()).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido.toString();
    }

// Función auxiliar para buscar una línea en un array de líneas
    public static boolean buscarLineaEnArray(String linea, String[] array) {
        for (String elemento : array) {
            if (linea.equals(elemento)) {
                return true;
            }
        }
        return false;
    }
}
