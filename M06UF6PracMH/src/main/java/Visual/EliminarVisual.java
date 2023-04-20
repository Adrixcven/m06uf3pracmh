/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import Logica.Eliminar;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**
 *
 * La clase EliminarVisual contiene un método público estático llamado
 * eliminarRemot,
 *
 * que elimina un repositorio remoto dado su identificador.
 */
public class EliminarVisual {

    /**
     *
     * Este método elimina un repositorio remoto dada su colección y base de
     * datos MongoDB.
     *
     * @param coleccio la colección MongoDB que contiene el repositorio remoto.
     * @param bbdd la base de datos MongoDB que contiene la colección del
     * repositorio remoto.
     */
    public static void eliminarRemot(MongoCollection<Document> coleccio, MongoDatabase bbdd) {
        Scanner in = new Scanner(System.in);
        System.out.println("Dame el identificador del repositorio remoto.");
        String repositorio = in.nextLine();
        //Cambia el String de la ruta del repositorio al que sera el identificador del repositorio.
        Eliminar.eliminarRepositorio(in, coleccio, bbdd, repositorio);
        System.out.println("Repositorio remoto eliminado");
    }
}
