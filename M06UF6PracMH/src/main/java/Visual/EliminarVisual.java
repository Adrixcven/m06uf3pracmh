/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class EliminarVisual {
    public static void eliminarRemot(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd) {
        System.out.println("Dame el identificador del repositorio remoto que quieres eliminar");
        String repositorio = in.nextLine();
        //metodo eliminar
        System.out.println("Repositorio remoto eliminado");
    }
}
