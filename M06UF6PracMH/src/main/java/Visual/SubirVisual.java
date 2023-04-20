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
public class SubirVisual {

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
                Subir.SubirArchivoConForce(in, coleccio, ruta);
                continuar = false;
                System.out.println("Se ha subido el archivo al repositorio remoto!");
            } else if (force == 0) {
                Subir.SubirArchivoSinForce(in, coleccio, rutarchivo);
                continuar = false;
                System.out.println("Se ha subido el archivo al repositorio remoto!");
            } else {
                System.out.println("Error");
            }
        }
    }
}
