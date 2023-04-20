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
public class Mapeig {
    public static Document setArchivoToDocument(Archivodata e){
        
        Document ret = new Document("nom", e.getNom())
                .append("tiempo", e.getTiempo())
                .append("contenido", e.getContenido());
        
        return ret;
    }
    public static Archivodata getArchivoFromDocument(Document d)
    {
         Archivodata ret = new Archivodata();
         
         ret.setNom(d.getString("nom"));
         ret.setTiempo(d.getDate("tiempo"));
         ret.setContenido(d.getString("contenido"));
         
         return ret;
        
    }
    public static Document updateDocument(Archivodata e) {

        
        Document update = new Document("$set", new Document("tiempo", e.getTiempo())
                .append("contenido", e.getContenido()));

        return update;
    }
}
