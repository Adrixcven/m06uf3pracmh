/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import Logica.Subir;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**
 *
 * Esta clase permite subir un archivo a un repositorio remoto a través de una
 * colección de MongoDB.
 *
 * Se debe proporcionar la colección y la base de datos donde se encuentra el
 * repositorio.
 *
 * Se solicita al usuario la ruta del repositorio y el archivo a subir, así como
 * si se desea hacer la subida con force.
 */
public class SubirVisual {

    /**
     *
     * Sube un archivo a un repositorio remoto a través de una colección de
     * MongoDB.
     *
     * @param coleccio la colección de MongoDB que representa el repositorio
     * remoto
     *
     * @param bbdd la base de datos donde se encuentra la colección
     */
    public static void subirRemot(MongoCollection<Document> coleccio, MongoDatabase bbdd) {
        Scanner in = new Scanner(System.in);
        Boolean continuar = true;
        int count = 0;
        String rep = "";
        String ruta = "";
        File rutarchivo = new File("archivo_vacio.txt");;
        while (continuar) {
            count = 0;
            System.out.println("Dame la ruta del repositorio remoto que quieres usar");
            rep = in.nextLine();
            //cambiamos el nombre del identificador para que sea.
            String repConvertida = rep.replace("\\", "/");
            repConvertida = repConvertida.replaceAll("^[a-zA-Z]:", "");
            String repositorio = repConvertida.replace("/", "_").substring(1);
            //Miramos si existe la colección y, si no existe, dice que no existe y vuelve a preguntarlo
            for (String name : bbdd.listCollectionNames()) {
                if (name.equals(repositorio)) {
                    count++;
                }
            }
            if (count == 0) {
                System.out.println("El repositorio remoto no existe.");
            } else {
                //Usamos la colección con el identificador que le ponemos
                coleccio = bbdd.getCollection(repositorio);
                continuar = false;
            }
        }
        continuar = true;
        while (continuar) {
            System.out.println("Dime el archivo que quieres subir");
            ruta = in.nextLine();
            ruta = rep + "\\" + ruta;
            rutarchivo = new File(ruta);
            //Miramos si la ruta del archivo existe y, si no existe, vuelve a preguntar.
            if (rutarchivo.exists()) {
                continuar = false;
            } else {
                System.out.println("El archivo no existe en la ruta del repositorio.");
                System.out.println("Ten en cuenta que tiene que estar en " + rep);
            }
        }
        continuar = true;
        while (continuar) {
            System.out.println("Quieres hacer force?");
            System.out.println("1. Si");
            System.out.println("0. No");
            int force = in.nextInt();
            if (force == 1) {
                Subir.esDirectorio(in, coleccio, ruta);
                continuar = false;
                System.out.println("Se ha subido el archivo al repositorio remoto!");
            } else if (force == 0) {
                Subir.esDirectorioNoForce(in, coleccio, ruta);
                continuar = false;
                System.out.println("Se ha subido el archivo al repositorio remoto!");
            } else {
                System.out.println("Error");
            }
        }
    }
}
