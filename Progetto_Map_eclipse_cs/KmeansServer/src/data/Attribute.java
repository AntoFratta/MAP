package data;

import java.io.Serializable;

/**
 * La classe Attribute e' una classe astratta che rappresenta un attributo di <br>
 * un oggetto in un datatset. <br><br>
 *
 * Tale classe ha due attributi:<br>
 * - 'name' : che rappresenta il nome dell'attributo;<br>
 * - 'index' : il quale rappresenta l'indice dell'attributo nel dataset<br><br>
 *
 *
 *
 * @author Paola
 *
 */

public abstract class Attribute implements Serializable {

    private final String name;
    private final int index;


    /**
     *
     * Il metodo Attribute rappresenta il costruttore della classe<br>
     *  ed inizializza i seguenti valori :<br>
     * -nome<br>
     * -indice<br>
     *
     * @param name : rapprsenta il nome dell'attributo
     * @param index : rappresenta l'identificativo numerico dell'attributo
     */

    Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }


    /**
     *
     * Il metodo getName()<br>
     * rappresenta una funzione di lettura
     *
     * @return : 'name',  il nome dell'attributo
     */
    String getName() {
        return name;
    }


    /**
     * Il metodo getIndex()<br>
     * 	rappresenta una funzione di lettura
     *
     * @return : 'index', indichera' l'identificativo <br>
     *            numerico dell'attributo
     */
    int getIndex() {
        return index;
    }


    /**
     *
     * Il metodo String toString()<br>
     * restituira' name, sovrascrivendo il medoto ereditato <br>
     * dalla superclasse restituendo a sua volta la stringa che <br>
     * rappresenta lo stato dell'oggetto
     *
     */
    @Override
    public String toString() {
        return name;
    }

}
