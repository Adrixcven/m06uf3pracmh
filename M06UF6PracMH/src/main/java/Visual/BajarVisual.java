
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Visual;

import java.util.Scanner;

/**
 *
 * @author Adrix
 */
public class BajarVisual {

    public static void bajarRemot(Scanner in) {
        System.out.println("Dame el identificador del repositorio remoto que quieres usar");
        var rep = in.nextLine();
        System.out.println("Dime la ruta del archivo que quieres bajar");
        var ruta = in.nextLine();
        var continuar = true;
        while (continuar) {
            System.out.println("Quieres hacer force?");
            System.out.println("1. Si");
            System.out.println("0. No");
            var force = in.nextInt();
            if (force == 1) {
                //metodo de bajada con force
                continuar = false;
                System.out.println("Se ha bajado el archivo al repositorio remoto!");
            } else if (force == 0) {
                //metodo de bajada sin force
                continuar = false;
                System.out.println("Se ha bajado el archivo al repositorio remoto!");
            } else {
                System.out.println("Error");
            }
        }

    }
}
