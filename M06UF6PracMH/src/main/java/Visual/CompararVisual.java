/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import Logica.Comparar;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**

Esta clase se utiliza para comparar un archivo local con un repositorio remoto.
*/
public class CompararVisual {
/**

Compara un archivo local con un repositorio remoto.

@param coleccio la colección del repositorio remoto.

@param bbdd la base de datos del repositorio remoto.

@throws IOException si ocurre un error durante la lectura de archivos.
*/
    public static void compararRemot(MongoCollection<Document> coleccio, MongoDatabase bbdd) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Dame la id del repositorio remoto que quieres usar");
        var rep = in.nextLine();
        System.out.println("Dime la ruta del archivo Local que quieres comparar");
        var nameRep = in.nextLine();
        System.out.println("Dime el nombre del archivo");
        var nombre = in.nextLine();
        var continuar = true;
        while (continuar) {
            System.out.println("Quieres hacer que tenga detalles?");
            System.out.println("1. Si");
            System.out.println("0. No");
            var detalles = in.nextInt();
            if (detalles == 1) {
                System.out.println("No está codificado aún.");
                //Comparar.compararConDetalles(rep, rutaLocal, bbdd);
                continuar = false;
            } else if (detalles == 0) {
                coleccio = bbdd.getCollection(rep);
                String ruta = "";
                if (nombre != "") {
                    ruta = nameRep + "\\" + nombre;
                } else {
                    ruta = nameRep;
                }

                Comparar.esDirectorio(rep, ruta, false, coleccio);
                continuar = false;
            } else {
                System.out.println("Error");
            }
        }
    }
}
