/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package Visual;

import java.util.Scanner;

/**
 *
 * @author Adrix
 */
public class Menu_Inicial {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Bienvenido a Los Repositorios.");
        System.out.println("Donde quieres que este el repositorio local?");
        //Definir repositorio
        Boolean continuar = true;
        while (continuar == true) {
            System.out.println("Selecciona una función");
            System.out.println("1. Crear Repositorio Remoto");
            System.out.println("2. Eliminar Repositorio Remoto");
            System.out.println("3. Subir Archivo a Repositorio Remoto");
            System.out.println("4. Bajar Archivo de Repositorio Remoto");
            System.out.println("5. Comparar Archivos entre los Repositorio Remotos y Local");
            System.out.println("6. Clonar Repositorio Remoto");
            System.out.println("7. Salir");
            var opcion = in.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Has elegido Crear el Repositorio Remoto.");
                    CrearVisual.crearRemot(in);
                    break;
                case 2:
                    System.out.println("Has elegido Eliminar Repositorio Remoto.");
                    EliminarVisual.eliminarRemot(in);
                    break;
                case 3:
                    System.out.println("Has elegido Subir Archivo a Repositorio Remoto");
                    SubirVisual.subirRemot(in);
                    break;
                case 4:
                    System.out.println("Has elegido Bajar Archivo de Repositorio Remoto");
                    BajarVisual.bajarRemot(in);
                    break;
                case 5:
                    System.out.println("Has elegido Comparar Archivos entre los Repositorio Remotos y Local");
                    CompararVisual.compararRemot(in);
                    break;
                case 6:
                    System.out.println("Has elegido Clonar Repositorio Remoto");
                    ClonarVisual.compararRemot(in);
                    break;
                case 7:
                    System.out.println("Has elegido salir del Programa. Hasta la vista!");
                    continuar = false;
                    break;
            }
        }
    }
}
