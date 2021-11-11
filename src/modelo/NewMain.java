/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

//import control.ControlJSON;
import control.ControlJSON;
import java.util.ArrayList;

/**
 *
 * @author Juan David
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ControlJSON j = new ControlJSON();
        Gramatica gra = j.leerGramatica("C:\\Gramatica.json");

        gra.primerosGramatica();
        gra.siguientesGramatica();
        //gra.conjuntoPrediccionGramatica();
        


    }

}
