/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author Adrix
 */
public class Bajar {

    public static String adaptarRutaAColeccion(String ruta, String coleccion) {
        String[] partesRuta = ruta.split("\\\\");
        String[] partesColeccion = coleccion.split("_");

        // quitar la unidad de la ruta de Windows (por ejemplo, "C:")
        partesRuta[0] = "";

        // convertir las partes de la ruta en partes de la colección
        for (int i = 1; i < partesRuta.length; i++) {
            partesColeccion[i - 1] = partesRuta[i];
        }

        // unir las partes de la colección con "_"
        return String.join("_", partesColeccion);
    }
}
