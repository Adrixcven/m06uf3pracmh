/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import Logica.Bajar;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;
import org.bson.Document;
import org.bson.types.Binary;

/**
 *
 * @author Adrix
 */
public class BajarVisual {

    public static void bajarRemot(String coleccion, MongoDatabase db) throws IOException {
        Scanner in = new Scanner(System.in);
        // Comprobar si existe la colección
        if (!db.listCollectionNames().into(new ArrayList<>()).contains(coleccion)) {
            System.out.println("La colección no existe.");
            return;
        }

        //comprobar que el repositorio local concuerde con el remoto
        String ruta = "";
        String rutaAdaptada = "";
        Path rutaFinal = null;

        boolean carpetaCorrecta = false;

        while (!carpetaCorrecta) {
            System.out.println("Dame la ruta del repositorio local");
            ruta = in.nextLine();

            rutaAdaptada = Bajar.adaptarRutaAColeccion(ruta, coleccion);
            rutaFinal = Paths.get(rutaAdaptada);
            Path colecci = Paths.get(coleccion);
            File carpeta = new File(ruta);

            if (!carpeta.exists()) {
                System.out.println("La carpeta no existe");
            } else if (!carpeta.isDirectory()) {
                System.out.println("La ruta no corresponde a una carpeta");
            } else if (!rutaFinal.equals(colecci)) {
                System.out.println("El nombre de la carpeta no coincide con el del repositorio remoto");
            } else {
                carpetaCorrecta = true;
                Pull(coleccion, db, ruta);
            }

        }

    }

    public static void Pull(String coleccion, MongoDatabase db, String ruta) throws IOException {
        Scanner in = new Scanner(System.in);
        Path rutaFinal = Paths.get(ruta);

        // Seleccionar documentos a bajar por nombre
        MongoCollection<Document> coleccionMongo = db.getCollection(coleccion);

        Document documento = new Document();
        boolean nombreCorrecto = false;

        while (!nombreCorrecto) {
            System.out.println("Dame el nombre del documento que quieres bajar:");
            String nombreDocumento = in.nextLine();

            documento = coleccionMongo.find(new Document("nom", nombreDocumento)).first();

            if (documento == null) {
                System.out.println("El documento no existe en la colección.");
            } else {
                nombreCorrecto = true;

                // Comprobar si el archivo existe en la ruta local
                String rutacompleta = rutaFinal.toString() + "\\" + nombreDocumento;
                File archivoLocal = new File(rutacompleta);
                if (archivoLocal.exists()) {
                    // Comprobar si la fecha de creación del archivo local es anterior a la fecha de modificación del remoto
                    Date fechaRemoto = documento.getDate("Fecha de modificación");
                    Date fechaLocal = new Date(archivoLocal.lastModified());

                    if (fechaLocal.before(fechaRemoto)) {
                        System.out.println("El archivo local es anterior al archivo remoto. ¿Desea hacer force?");
                        System.out.println("1. Sí");
                        System.out.println("0. No");

                        int opcion = in.nextInt();
                        in.nextLine();

                        if (opcion == 1) {
                            // Sobrescribir archivo local
                            try ( OutputStream out = new FileOutputStream(archivoLocal)) {
                                String contenidoString = (String) documento.get("contenido");
                                out.write(contenidoString.getBytes());
                                System.out.println("El archivo se ha sobrescrito correctamente.");
                            }catch(Exception e){
                                System.out.println("Error a la hora de descargar el archivo");
                            }
                        } else if (opcion == 0) {
                            System.out.println("La operación se ha cancelado.");
                        } else {
                            System.out.println("Opción inválida.");
                        }
                    }else{
                        System.out.println("El archivo es mas nuevo que el subido");
                    }
                    
                } else {
                    // Descargar archivo
                    try ( OutputStream out = new FileOutputStream(archivoLocal)) {
                        String contenidoString = (String) documento.get("contenido");
                        out.write(contenidoString.getBytes());
                        System.out.println("El archivo se ha descargado correctamente.");
                    }catch(Exception e){
                        System.out.println("Error a la hora de descargar el archivo");
                    }
                }
            }
        }
    }



}