/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class Eliminar {

    public static void eliminarRepositorio(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd, String repositorio) {
        boolean repetir = true;
        while (repetir == true) {
            try {
                coleccio.drop();
                repetir = false;
            } catch (MongoCommandException e) {
                System.out.println("El repositorio no existe");
            }
        }
    }
}

