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
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
public class ClonarVisual {

    public static void compararRemot(String fecha, String rep, MongoDatabase bbdd) throws FileAlreadyExistsException, NullPointerException, IOException {
        Scanner in = new Scanner(System.in);

        if (rep.isEmpty()) {
            throw new NullPointerException("El nombre del repositorio está vacio.");
        }

        MongoCollection<Document> coleccio = null;
        if (!repositoryExists(rep, bbdd, coleccio)) {
            throw new NullPointerException("El repositorio no existe");
        }

        MongoCursor<Document> cursor = coleccio.find().iterator();

        while (cursor.hasNext()) {
            Document document = cursor.next();

            Path path = Paths.get(document.getString("pathString"));

            Path padre = path.getParent();

            if (Files.exists(padre)) {
                throw new FileAlreadyExistsException("El directorio ya existe. Por favor eliminelo antes de continuar");
            }

            try {

                if (!Files.exists(padre)) {
                    Files.createDirectories(padre);
                }

                try ( BufferedWriter write = Files.newBufferedWriter(path)) {
                    write.write(document.getString("fileContent"));
                    write.close();
                }

                System.out.println("El fichero ha sido generado correctamente");

            } catch (FileAlreadyExistsException e) {
                System.out.println("El directorio ya existe. Por favor eliminelo antes de continuar");
            } catch (IOException e) {
                throw new IOException("Se ha producido un error durante la lectura o escritura del fichero");
            }
        }

    }

    public static boolean repositoryExists(String collectionName, MongoDatabase bbdd, MongoCollection<Document> coleccio) {
        boolean collectionFound = false;

        for (String mongoCollection : bbdd.listCollectionNames()) {
            if (mongoCollection.contentEquals(collectionName)) {
                collectionFound = true;
                coleccio = bbdd.getCollection(collectionName);
                break;
            }
        }
        return collectionFound;
    }

}
