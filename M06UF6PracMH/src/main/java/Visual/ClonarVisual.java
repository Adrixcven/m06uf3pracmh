/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;
import org.bson.Document;


/**
 *
 * @author Adrix
 */
public class ClonarVisual {

    public static void compararRemot(MongoCollection<Document> coleccio, MongoDatabase bbdd) {
        Scanner in = new Scanner(System.in);
        System.out.println("Dame el identificador del repositorio remoto que quieres usar");
        var rep = in.nextLine();
        System.out.println("Dime el timestamp que quieres usar. Ej: 2023-04-19T12:00:00Z");
        var timestamp = in.nextLine();
        coleccio = bbdd.getCollection(rep);
        Document filtro = new Document("timestamp", new Document("$lte", timestamp));
        
        FindIterable<Document> documentos = coleccio.find(filtro);
        
        MongoCursor<Document> cursor = documentos.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next().toJson());
        }
        //esto es nuevo otra vez
        cursor.close();
        
    }
}
