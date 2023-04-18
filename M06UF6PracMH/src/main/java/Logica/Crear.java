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
public class Crear {

    public static void crearRepositorio(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd, String repositorio) {
        boolean repetir = true;
        //Si no existe la colección, la crea.
        while (repetir == true) {
            try {
                bbdd.createCollection(repositorio);
                repetir= false;
            } catch (MongoCommandException e) {
                System.out.println("Ya el repositorio ya existe");
            }
        }

    }
}
