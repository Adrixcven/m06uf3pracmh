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
/**
 *
 * La clase Crear contiene un método estático para crear un repositorio en una
 * base de datos MongoDB.
 */
public class Crear {

    /**
     *
     * Este método estático crea un repositorio en una base de datos MongoDB
     * especificada.
     *
     * @param in un objeto Scanner para leer la entrada del usuario
     * @param coleccio una instancia de MongoCollection<Document> donde se
     * almacenará el repositorio
     * @param bbdd una instancia de MongoDatabase donde se creará el repositorio
     * @param repositorio un String que especifica el nombre del repositorio a
     * crear
     */
    public static void crearRepositorio(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd, String repositorio) {
        boolean repetir = true;
        //Si no existe la colección, la crea.
        while (repetir == true) {
            try {
                bbdd.createCollection(repositorio);
                repetir = false;
            } catch (MongoCommandException e) {
                System.out.println("Ya el repositorio ya existe");
            }
        }
        System.out.println("Tu repositorio Remoto " + repositorio + " se ha creado correctamente!");

    }
}
