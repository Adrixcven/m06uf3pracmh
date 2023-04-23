/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**
 *
 * Clase Bajar que permite descargar documentos de una colección de MongoDB.
 */
public class Bajar {

    /**
     *
     * Método que permite descargar un documento de una colección de MongoDB y
     * guardarlo en una ruta local.
     *
     * @param coleccion el nombre de la colección de MongoDB.
     *
     * @param db la base de datos de MongoDB.
     *
     * @param ruta la ruta donde se guardará el documento descargado.
     *
     * @throws IOException si ocurre un error al descargar el archivo.
     */
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
                
                String os = System.getProperty("os.name").toLowerCase();
                boolean isUbuntu = os.contains("ubuntu");
                if (isUbuntu) {
                } else {
                    rutacompleta = rutacompleta.replace("\\", "/");
                    rutacompleta = "/" + rutacompleta;
                }
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
                            } catch (Exception e) {
                                System.out.println("Error a la hora de descargar el archivo");
                            }
                        } else if (opcion == 0) {
                            System.out.println("La operación se ha cancelado.");
                        } else {
                            System.out.println("Opción inválida.");
                        }
                    } else {
                        System.out.println("El archivo es mas nuevo que el subido");
                    }

                } else {
                    // Descargar archivo
                    try ( OutputStream out = new FileOutputStream(archivoLocal)) {
                        String contenidoString = (String) documento.get("contenido");
                        out.write(contenidoString.getBytes());
                        System.out.println("El archivo se ha descargado correctamente.");
                    } catch (Exception e) {
                        System.out.println("Error a la hora de descargar el archivo");
                    }
                }
            }
        }
    }

    /**
     *
     * Método que adapta una ruta local a una colección de MongoDB.
     *
     * @param ruta la ruta local que se desea adaptar.
     * @param coleccion el nombre de la colección de MongoDB a la que se quiere
     * adaptar la ruta.
     * @return la ruta adaptada a la colección de MongoDB.
     */
    public static String adaptarRutaAColeccion(String ruta, String coleccion) {
        String[] partesRuta = ruta.split("\\\\");
        String[] partesColeccion = coleccion.split("_");

        // quitar la unidad de la ruta de Windows (por ejemplo, "C:")
        partesRuta[0] = "";

        // convertir las partes de la ruta en partes de la colección
        for (int i = 1; i < partesRuta.length; i++) {
            partesColeccion[i - 1] = partesRuta[i];
        }

        // unir las partes de la colección con "_"
        return String.join("_", partesColeccion);
    }
}
