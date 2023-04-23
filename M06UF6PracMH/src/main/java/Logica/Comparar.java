/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
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
    public static void compare(String dir_base, String fitxer, boolean detail, MongoCollection<Document> collection) throws IOException {
        File baseDir = new File(dir_base);
        if (!baseDir.isDirectory()) {
            throw new IllegalArgumentException("El directori base no existeix.");
        }

        if (fitxer == null) {
            // Comparem tot el directori de forma recursiva.
            compareDirRecursive(baseDir, "", detail, collection);
        } else {
            // Comparem només un fitxer.
            File localFile = new File(baseDir, fitxer);
            if (!localFile.exists()) {
                System.out.println("El fitxer local no existeix.");
                return;
            }

            String remotePath = "/" + localFile.getAbsolutePath().replace(baseDir.getAbsolutePath(), "").replace("\\", "/");
            Document remoteDoc = collection.find(Filters.eq("path", remotePath)).first();
            if (remoteDoc == null) {
                System.out.println("El fitxer remot no existeix.");
                return;
            }

            long localTimestamp = localFile.lastModified();
            long remoteTimestamp = remoteDoc.getLong("timestamp");
            if (localTimestamp == remoteTimestamp) {
                System.out.println("El local i el remot tenen exactament el mateix timestamp, són iguals.");
                return;
            }

            String localContent = new String(Files.readAllBytes(localFile.toPath()));
            String remoteContent = remoteDoc.getString("content");
            if (localContent.equals(remoteContent)) {
                System.out.println("El local i el remot NO tenen el mateix timestamp, però tenen el mateix contingut.");
                return;
            }

            System.out.println("El local i el remot NO tenen el mateix timestamp o bé NO tenen el mateix contingut. Són diferents.");
            if (detail) {
                String[] localLines = localContent.split("\n");
                String[] remoteLines = remoteContent.split("\n");

                System.out.println("Diferències de local a remot:");
                for (int i = 0; i < localLines.length; i++) {
                    int remoteIndex = indexOfLine(remoteLines, localLines[i]);
                    if (remoteIndex == -1) {
                        System.out.println("[MODIFICADA o ELIMINADA] Línia " + (i + 1) + ": " + localLines[i]);
                    }
                }

                System.out.println("Diferències de remot a local:");
                for (int i = 0; i < remoteLines.length; i++) {
                    int localIndex = indexOfLine(localLines, remoteLines[i]);
                    if (localIndex == -1) {
                        System.out.println("[MODIFICADA o ELIMINADA] Línia " + (i + 1) + ": " + remoteLines[i]);
                    }
                }
            }
        }
    }

    private static void compareDirRecursive(File dir, String relativePath, boolean detail, MongoCollection<Document> collection) throws IOException {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                String subPath = relativePath + "/" + file.getName();
                compareDirRecursive(file, subPath, detail, collection);
            } else {
                String filePath = relativePath + "/" + file.getName();
                compare(filePath, null, detail, collection);
            }
        }
    }

    private static int indexOfLine(String[] lines, String line) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals(line)) {
                return i;
            }
        }
        return -1;
    }
}
