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
    public static Document setArchivoToDocument(Archivodata e)
    {
        Document ret = new Document("nom", e.getNom())
                .append("tiempo", e.getTiempo())
                .append("contenido", e.getContenido())
                .append("uid",e.getUid());
        
        return ret;
    }
    public static Archivodata getAddrFromDocument(Document d)
    {
         Archivodata ret = new Archivodata();
         
         ret.setUid(d.getString("iud"));
         ret.setNom(d.getString("nom"));
         ret.setTiempo(d.getDate("tiempo"));
         ret.setContenido((StringBuilder) d.get("contenido"));
         
         return ret;
        
    }
}
