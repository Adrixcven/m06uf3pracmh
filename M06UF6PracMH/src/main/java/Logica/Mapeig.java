/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

import entidades.Archivodata;
import java.util.Arrays;
import org.bson.Document;

/**
 *
 * @author Adrix
 */
/**
 *
 * La clase Mapeig proporciona métodos para convertir datos entre objetos
 * Archivodata y objetos Document de MongoDB.
 */
public class Mapeig {

    /**
     *
     * Convierte un objeto Archivodata en un objeto Document.
     *
     * @param e Archivodata que se desea convertir.
     *
     * @return Document con los datos del Archivodata.
     */
    public static Document setArchivoToDocument(Archivodata e) {

        Document ret = new Document("nom", e.getNom())
                .append("tiempo", e.getTiempo())
                .append("contenido", e.getContenido());

        return ret;
    }

    /**
     *
     * Convierte un objeto Document de MongoDB en un objeto Archivodata.
     *
     * @param d Document de MongoDB que se desea convertir.
     *
     * @return Archivodata con los datos del Document.
     */
    public static Archivodata getArchivoFromDocument(Document d) {
        Archivodata ret = new Archivodata();

        ret.setNom(d.getString("nom"));
        ret.setTiempo(d.getDate("tiempo"));
        ret.setContenido(d.getString("contenido"));

        return ret;

    }

    /**
     *
     * Actualiza los campos de tiempo y contenido de un objeto Document de
     * MongoDB a partir de un objeto Archivodata.
     *
     * @param e Archivodata con los datos a actualizar.
     *
     * @return Document con los datos actualizados.
     */
    public static Document updateDocument(Archivodata e) {

        Document update = new Document("$set", new Document("tiempo", e.getTiempo())
                .append("contenido", e.getContenido()));

        return update;
    }
}
