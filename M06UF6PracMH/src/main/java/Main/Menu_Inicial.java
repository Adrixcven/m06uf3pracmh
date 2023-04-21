/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package Main;

import Visual.BajarVisual;
import Visual.ClonarVisual;
import Visual.CompararVisual;
import Visual.CrearVisual;
import Visual.EliminarVisual;
import Visual.SubirVisual;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.IOException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class Menu_Inicial {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Menu_Inicial.class);
    private static com.mongodb.client.MongoClient mongoClient;
    private static MongoDatabase bbdd;
    private static MongoCollection<Document> coleccio;

    public static void main(String[] args) throws NullPointerException, IOException {

        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenido a Los Repositorios.");
        System.out.println("Quieres crear un Repositorio Local?");
        System.out.println("1. Si");
        System.out.println("0. No");
        var opcion = in.nextInt();
        if (opcion == 1) {
            System.out.println("Se creara un Repositorio Local.");
            System.out.println("Donde quieres que se genere el directorio?");
            var directorio = in.nextLine();
            //metodo de generar directorio
            System.out.println("Se ha generado el directorio para el repositorio Local");
        }
        //Definir repositorio
        Boolean continuar = true;
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017");
            mongoClient = MongoClients.create(connectionString);
            bbdd = mongoClient.getDatabase("GETBD");
        } catch (Exception ex) { //TODO cambiar exception
            logger.error("Excepcion: " + ex.toString());
        }
        while (continuar == true) {
            System.out.println("Selecciona una función");
            System.out.println("1. Crear Repositorio Remoto");
            System.out.println("2. Eliminar Repositorio Remoto");
            System.out.println("3. Subir Archivo a Repositorio Remoto");
            System.out.println("4. Bajar Archivo de Repositorio Remoto");
            System.out.println("5. Comparar Archivos entre los Repositorio Remotos y Local");
            System.out.println("6. Clonar Repositorio Remoto");
            System.out.println("7. Salir");
            opcion = in.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Has elegido Crear el Repositorio Remoto.");
                    CrearVisual.crearRemot(coleccio, bbdd);
                    break;
                case 2:
                    System.out.println("Has elegido Eliminar Repositorio Remoto.");
                    EliminarVisual.eliminarRemot(coleccio, bbdd);
                    break;
                case 3:
                    System.out.println("Has elegido Subir Archivo a Repositorio Remoto");
                    SubirVisual.subirRemot(coleccio, bbdd);
                    break;
                case 4:
                    System.out.println("Has elegido Bajar Archivo de Repositorio Remoto");
                    BajarVisual.bajarRemot(in, coleccio, bbdd);
                    break;
                case 5:
                    System.out.println("Has elegido Comparar Archivos entre los Repositorio Remotos y Local");
                    CompararVisual.compararRemot(coleccio, bbdd);
                    break;
                case 6:
                    System.out.println("Has elegido Clonar Repositorio Remoto");
                    System.out.println("Dame el identificador del repositorio remoto que quieres usar");
                    var rep = in.nextLine();
                    rep = in.nextLine();
                    ClonarVisual.clonar(rep, bbdd);
                    break;
                case 7:
                    mongoClient.close();
                    System.out.println("Has elegido salir del Programa. Hasta la vista!");
                    continuar = false;
                    break;
            }
        }
    }
}
