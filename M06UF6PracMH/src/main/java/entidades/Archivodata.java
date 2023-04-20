/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 *
 * @author Adrix
 */
public class Archivodata {
    // identificador aleatori únic
    private String uid;
    
    private String nom;
    private Date tiempo;
    private StringBuilder contenido;

    public Archivodata() {
    }

    public Archivodata(String uid, String nom, Date tiempo, StringBuilder contenido) {
        this.uid = uid;
        this.nom = nom;
        this.tiempo = tiempo;
        this.contenido = contenido;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getTiempo() {
        return tiempo;
    }

    public void setTiempo(Date ttiempo) {
        this.tiempo = tiempo;
    }

    public StringBuilder getContenido() {
        return contenido;
    }

    public void setContenido(StringBuilder contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Archivo{uid=").append(uid);
        sb.append(", Nom=").append(nom);
        sb.append(", Tiempo de subida=").append(tiempo);
        sb.append(", Contenido=").append(contenido);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
