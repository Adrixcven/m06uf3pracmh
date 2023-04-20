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
 * Clase que contiene un método estático para eliminar un repositorio de una
 * base de datos MongoDB.
 */
public class Eliminar {

    /**
     *
     * Método que elimina un repositorio de una base de datos MongoDB.
     *
     * @param in Objeto de la clase Scanner para leer datos desde la consola.
     * @param coleccio Objeto de la clase MongoCollection que representa la
     * colección a la que pertenece el repositorio.
     * @param bbdd Objeto de la clase MongoDatabase que representa la base de
     * datos donde se encuentra el repositorio.
     * @param repositorio Nombre del repositorio a eliminar.
     */
    public static void eliminarRepositorio(Scanner in, MongoCollection<Document> coleccio, MongoDatabase bbdd, String repositorio) {
        boolean repetir = true;
        coleccio = bbdd.getCollection(repositorio);
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
