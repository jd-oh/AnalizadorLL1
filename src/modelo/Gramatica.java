/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Juan David
 */
public class Gramatica {

    private ArrayList<Produccion> conjuntoProduccion;

    public Gramatica(ArrayList<Produccion> conjuntoProduccion) {
        this.conjuntoProduccion = conjuntoProduccion;
    }

    public ArrayList<Produccion> getConjuntoProduccion() {
        return conjuntoProduccion;
    }

    public void setConjuntoProduccion(ArrayList<Produccion> conjuntoProduccion) {
        this.conjuntoProduccion = conjuntoProduccion;
    }

    /**
     * Método que busca una producción por su símbolo a la izquierda (antes de
     * la flecha) para saber si existe o no.
     *
     * @param simbolo el símbolo a buscar en las izquierdas del conjunto
     * producción
     * @param produccionOrigen la instacia de la producción que contiene el
     * símbolo a buscar
     * @return la producción (si la encuentra) o null si no existe dentro del
     * conjunto producción.
     */
    public Produccion buscarCaracterEnConjuntoProduccion(Character simbolo, Produccion produccionOrigen) {
        String simboloStr = simbolo.toString();
        for (Produccion produccion : conjuntoProduccion) {

            if (produccion.getIzquierda().equals(simboloStr) && !produccionOrigen.equals(produccion)) {

                return produccion;
            }
        }
        return null;
    }

    public Produccion buscarCaracterEnConjuntoProduccion(Character simbolo) {
        String simboloStr = simbolo.toString();
        for (Produccion produccion : conjuntoProduccion) {

            if (produccion.getIzquierda().equals(simboloStr)) {

                return produccion;
            }
        }
        return null;
    }

    /**
     * Método que recorre una producción, verifica todos sus primeros y
     * finalmente los retorna
     *
     * @param produccion la producción de la cual se obtendrán sus primeros.
     * @return arreglo de String con los primeros de la producción.
     */
    public ArrayList<String> primerosProduccion(Produccion produccion, ArrayList<String> primeros) {

        String[] derecha = produccion.getDerecha().split("\\|");

        for (int i = 0; i < derecha.length; i++) {

            char[] letrasPalabra = derecha[i].toCharArray();

            String unirPalabra = "";

            unirPalabra = verificarPalabraPrimeros(letrasPalabra, produccion, unirPalabra, primeros);

            if (!unirPalabra.equals("") && !primeros.contains(unirPalabra)) {
                primeros.add(unirPalabra);
            }

        }

        return primeros;
    }

    /**
     * Método que recorre los símbolos de una palabra que pertenece a una
     * producción Usa la fórmula general para sacar los primeros de una
     * producción específica.
     *
     * @param letrasPalabra split de una palabra.
     * @param produccion la instancia de la producción que se está verificando
     * @param unirPalabra son los primeros de cada palabra de la producción.
     * @return después de terminar la recursión, retorna los primeros de la cada
     * palabra de la producción.
     */
    private String verificarPalabraPrimeros(char[] letrasPalabra, Produccion produccion, String unirPalabra, ArrayList<String> primeros) {
        for (int j = 0; j < letrasPalabra.length; j++) {

            if (Character.isUpperCase(letrasPalabra[j]) && j == 0) {

                Produccion nuevaProduccion = buscarCaracterEnConjuntoProduccion(letrasPalabra[j], produccion);

                if (nuevaProduccion != null) {

                    primerosProduccion(nuevaProduccion, primeros);
                    break;

                } else {

                    break;
                }

            } else if (Character.isUpperCase(letrasPalabra[j])) {

                break;

            } else {

                unirPalabra += letrasPalabra[j];
                continue;
            }

        }
        return unirPalabra;
    }

    /**
     * Método que muestra los primeros de cada producción de toda la gramática
     */
    public void primerosGramatica() {

        for (Produccion produccion : conjuntoProduccion) {
            ArrayList<String> primeros = new ArrayList<>();
            primeros = primerosProduccion(produccion, primeros);

            System.out.println("Prim(" + produccion.getIzquierda() + "):" + primeros);

        }
    }

    /**
     * Método que muestra los siguientes de cada producción de toda la gramática
     */
    public void siguientesGramatica() {

        for (Produccion produccion : conjuntoProduccion) {
            ArrayList<String> siguientes = new ArrayList<>();
            siguientes = siguientesProduccion(produccion, siguientes);
            System.out.println("Sig(" + produccion.getIzquierda() + "):" + siguientes);
        }
    }
    
    


    /**
     * Método que toma una produccion, y busca en dónde se hace referencia a
     * ella dentro de todo el conjunto produccion. Las guarda en una variable
     * temporal y después de buscar los siguientes, los retorna
     *
     * @param produccion la producción de la cual se obtendrán sus siguientes.
     * @return lista de String con los siguientes de la producción.
     */
    public ArrayList<String> siguientesProduccion(Produccion produccion, ArrayList<String> siguientes) {
        String simbolo = produccion.getIzquierda();
        ArrayList<Produccion> produccionesTemporal = new ArrayList<>();

        for (int i = 0; i < conjuntoProduccion.size(); i++) {

            if (conjuntoProduccion.get(i).getDerecha().contains(simbolo)) {
                produccionesTemporal.add(conjuntoProduccion.get(i));
            }

        }
        if (produccion.equals(conjuntoProduccion.get(0)) && !siguientes.contains("$")) {
            siguientes.add("$");
        }
        for (Produccion prodTemp : produccionesTemporal) {

            String[] derecha = prodTemp.getDerecha().split("\\|");
            for (String palabra : derecha) {

                char[] letrasPalabra = palabra.toCharArray();
                //String unirPalabra = "";
                //unirPalabra = verificarPalabraSegundos(letrasPalabra, simbolo, prodTemp, siguientes, unirPalabra);
                siguientes = verificarPalabraSegundos(letrasPalabra, simbolo, prodTemp, siguientes);
            }

        }

        return siguientes;
    }

    /**
     * Método que recorre los símbolos de una palabra que pertenece a una
     * producción y usa la fórmula general para sacar los siguientes de una
     * producción específica.
     *
     * @param letrasPalabra split de una palabra.
     * @param produccionTemp la instancia de la producción que se está
     * verificando
     * @param simboloABuscar El simbolo que se buscará dentro de cada producción
     * @param siguientes la lista de los siguientes de una producción específica
     * @return después de terminar la recursión, retorna la lista con los
     * siguientes de una producción
     */
    private ArrayList<String> verificarPalabraSegundos(char[] letrasPalabra, String simboloABuscar, Produccion produccionTemp, ArrayList<String> siguientes) {
        for (int j = 0; j < letrasPalabra.length; j++) {
            String simboloPalabra = Character.toString(letrasPalabra[j]);
            if (simboloPalabra.equals(simboloABuscar) && j != letrasPalabra.length - 1) {

                if (Character.isUpperCase(letrasPalabra[j + 1])) {
                    Produccion produccion = buscarCaracterEnConjuntoProduccion(letrasPalabra[j + 1]);
                    if (produccion != null) {

                        ArrayList<String> primeros = new ArrayList<>();
                        primeros = primerosProduccion(produccion, primeros);
                        for (String primero : primeros) {
                            if (!siguientes.contains(primero)) {
                                siguientes.add(primero);
                            }
                        }
                        if (primeros.contains("ø")) {
                            siguientes.remove("ø");
                            siguientesProduccion(produccionTemp, siguientes);
                        }

                    }
                } else {
                    if (!siguientes.contains(Character.toString(letrasPalabra[j + 1]))) {
                        siguientes.add(Character.toString(letrasPalabra[j + 1]));
                    }

                }

            } else if (simboloPalabra.equals(simboloABuscar) && j == letrasPalabra.length - 1) {
                if (!produccionTemp.getIzquierda().equals(simboloABuscar)) {
                    siguientesProduccion(produccionTemp, siguientes);
                }

            } else {
                continue;
            }

        }
        return siguientes;
    }
    
        /**
     * Método que muestra los conjuntos  de cada producción de toda la gramática
     */
    public void conjuntoPrediccionGramatica() {
        
        for (Produccion produccion : conjuntoProduccion) {
            ArrayList<String> conjuntoPrediccion = new ArrayList<>();
            conjuntoPrediccion = conjuntoPrediccionProduccion(produccion);
            System.out.println(conjuntoPrediccion);

        }
    }


    public ArrayList<String> conjuntoPrediccionProduccion(Produccion produccion) {
        ArrayList<String> conjuntoPrediccion = new ArrayList<>();
        String[] derechaProduccionOrigen = produccion.getDerecha().split("\\|");
        ArrayList<Produccion> produccionesDeLaProduccion = new ArrayList<>();
        for (String palabra : derechaProduccionOrigen) {
            Produccion nuevaProduccion = new Produccion(produccion.getIzquierda(), palabra);
            produccionesDeLaProduccion.add(nuevaProduccion);
        }

        for (int i = 0; i < produccionesDeLaProduccion.size(); i++) {


            Produccion produccionTemporal = produccionesDeLaProduccion.get(i);

            verificarConjuntoProduccion(produccionTemporal, conjuntoPrediccion);

        }

        return conjuntoPrediccion;
    }

    private void verificarConjuntoProduccion(Produccion produccionTemporal, ArrayList<String> conjuntoPrediccion) {
        ArrayList<String> primerosTemporal=new ArrayList<>();
        String primerosStr="";
        ArrayList<String> siguientesTemporal=new ArrayList<>();
        String siguientesStr="";
        
        if (!produccionTemporal.getDerecha().equals("ø")) {
            primerosTemporal = primerosProduccion(produccionTemporal, primerosTemporal);
            if (primerosTemporal != null) {
                primerosStr = primerosTemporal.toString();
                // "CP(" + produccionTemporal + "): " + "Prim(" + produccionTemporal.getDerecha() + ")=" + 
                conjuntoPrediccion.add(primerosStr);
            }
            
        } else {
            
            siguientesTemporal = siguientesProduccion(produccionTemporal, siguientesTemporal);
            if (siguientesTemporal != null) {
                siguientesStr = siguientesTemporal.toString();
                // "CP(" + produccionTemporal + "): " + "Sig(" + produccionTemporal.getDerecha() + ")=" + 
                conjuntoPrediccion.add(siguientesStr);
            }
        } 
        
    }

    @Override
    public String toString() {
        return "Gramatica{" + "conjuntoProduccion=" + conjuntoProduccion + '}';
    }
    
    

   

}
