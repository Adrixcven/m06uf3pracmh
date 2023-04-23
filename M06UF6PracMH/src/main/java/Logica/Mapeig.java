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
 * Esta clase proporciona métodos estáticos para convertir objetos Archivodata
 * en Documentos de MongoDB y viceversa, así como para actualizar los documentos
 * existentes.
 */
public class Mapeig {

    /**
     *
     * Convierte un objeto Archivodata en un Document de MongoDB.
     *
     * @param e el objeto Archivodata a convertir.
     *
     * @return el Documento de MongoDB creado a partir del objeto Archivodata.
     */
    public static Document setArchivoToDocument(Archivodata e) {

        Document ret = new Document("nom", e.getNom())
                .append("Fecha de modificación", e.getTiempo())
                .append("contenido", e.getContenido());

        return ret;
    }

    /**
     *
     * Convierte un Document de MongoDB en un objeto Archivodata.
     *
     * @param d el Documento de MongoDB a convertir.
     *
     * @return el objeto Archivodata creado a partir del Document de MongoDB.
     */
    public static Archivodata getArchivoFromDocument(Document d) {
        Archivodata ret = new Archivodata();

        ret.setNom(d.getString("nom"));
        ret.setTiempo(d.getDate("Fecha de modificación"));
        ret.setContenido(d.getString("contenido"));

        return ret;

    }

    /**
     *
     * Crea un Document de MongoDB para actualizar un registro existente con la
     * información de un objeto Archivodata.
     *
     * @param e el objeto Archivodata que contiene la información actualizada.
     *
     * @return el Documento de MongoDB creado para actualizar el registro
     * existente.
     */
    public static Document updateDocument(Archivodata e) {

        Document update = new Document("$set", new Document("Fecha de modificación", e.getTiempo())
                .append("contenido", e.getContenido()));

        return update;
    }
}
