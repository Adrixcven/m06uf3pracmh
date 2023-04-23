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
public class CompararVisual {

    public static void compararRemot(MongoCollection<Document> coleccio, MongoDatabase bbdd) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Dame la ruta del archivo remoto que quieres usar");
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
                //Comparar.compararConDetalles(rep, rutaLocal, bbdd);
                continuar = false;
            } else if (detalles == 0) {
                coleccio = bbdd.getCollection(rep);
                Comparar.compare(rep, nameRep, nombre, false, coleccio);
                continuar = false;
            } else {
                System.out.println("Error");
            }
        }
    }
}
