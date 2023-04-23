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
/**
 *
 * Clase para bajar documentos desde una colección de MongoDB a un repositorio
 * local.
 */
public class BajarVisual {

    /**
     *
     * Método para bajar un documento desde una colección de MongoDB a un
     * repositorio local.
     *
     * @param coleccion La colección de MongoDB de la que se quiere bajar un
     * documento.
     *
     * @param db La base de datos de MongoDB en la que se encuentra la
     * colección.
     *
     * @throws IOException Si se produce un error al realizar la operación de
     * bajada.
     */
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
                Bajar.pull(coleccion, db, ruta);
            }

        }

    }
}
