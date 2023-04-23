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
/**
 *
 * La clase Archivodata representa un archivo con un identificador único
 * aleatorio,
 *
 * nombre, tiempo de subida y contenido.
 */
public class Archivodata {
    // identificador aleatori únic

    /**
     *
     * Identificador aleatorio único del archivo.
     */
    private String nom;
    /**
     *
     * Tiempo de subida del archivo.
     */
    private Date tiempo;
    /**
     *
     * Contenido del archivo.
     */
    private String contenido;

    /**
     *
     * Constructor por defecto de la clase Archivodata.
     */
    public Archivodata() {
    }

    /**
     *
     * Constructor de la clase Archivodata que inicializa todos los atributos de
     * la clase.
     *
     * @param nom El nombre del archivo.
     * @param tiempo El tiempo de subida del archivo.
     * @param contenido El contenido del archivo.
     */
    public Archivodata(String nom, Date tiempo, String contenido) {
        this.nom = nom;
        this.tiempo = tiempo;
        this.contenido = contenido;
    }

    /**
     *
     * Método getter para el nombre del archivo.
     *
     * @return El nombre del archivo.
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * Método setter para el nombre del archivo.
     *
     * @param nom El nuevo nombre del archivo.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * Método getter para el tiempo de subida del archivo.
     *
     * @return El tiempo de subida del archivo.
     */
    public Date getTiempo() {
        return tiempo;
    }

    /**
     *
     * Método setter para el tiempo de subida del archivo.
     *
     * @param tiempo El nuevo tiempo de subida del archivo.
     */
    public void setTiempo(Date ttiempo) {
        this.tiempo = tiempo;
    }

    /**
     *
     * Método getter para el contenido del archivo.
     *
     * @return El contenido del archivo.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     *
     * Método setter para el contenido del archivo.
     *
     * @param contenido El nuevo contenido del archivo.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     *
     * Devuelve una cadena de caracteres que representa el objeto Archivodata.
     *
     * @return Una representación en cadena de caracteres del objeto
     * Archivodata.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Archivo{Nom=").append(nom);
        sb.append(", Tiempo de subida=").append(tiempo);
        sb.append(", Contenido=").append(contenido);
        sb.append('}');
        return sb.toString();
    }

}
