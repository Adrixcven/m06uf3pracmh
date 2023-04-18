/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import Logica.Crear;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class CrearVisual {

    public static void crearRemot(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd) {
        System.out.println("Dame la ruta absoluta del repositorio que quieres crear.");
        System.out.println("Ej. c:\\home\\user\\getrepo2");
        String repositorio = in.nextLine();
        //Cambia el String de la ruta del repositorio al que sera el identificador del repositorio.
        String repConvertida = repositorio.replace("\\", "/");
        repConvertida = repConvertida.replaceAll("^[a-zA-Z]:", "");
        repositorio = repConvertida.replace("/", "_").substring(3);
        Crear.crearRepositorio(in, coleccio, bbdd, repositorio);
        System.out.println("Repositorio remoto creado");
    }
}
