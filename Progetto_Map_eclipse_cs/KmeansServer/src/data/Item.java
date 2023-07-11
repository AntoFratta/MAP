package data;

import java.io.Serializable;
import java.util.Set;

/**
 *La classe astratta Item rappresenta un oggetto generico all'interno di un dataset. <br>
 * Ha due membri dati, attribute e value, rispettivamente l'attributo dell'oggetto e <br> il suo valore discreto<br><br>
 *
 *
 * I metodi che vengono utilizzati all'interno di questa classe sono:<br>
 * - la distanza astratta che dev'essere implementata dalle sue sottoclassi<br>
 *   la quale verra' utilizzata per calcolare la distanza tra due oggetti Item;<br>
 * - aggiornamento, viene utilizzata per per aggiornare il valore dell'oggetto<br>
 *   in base alla media dei valori dei membri dei cluster.
 *
 *
 * @author Paola
 *
 */
public abstract class Item implements Serializable {

    private final Attribute attribute;
    private Object value;


    /**
     * Il costruttore Item;<br>
     * inizializza i valori dei membri attributi
     *
     * @param attribute : rappresenta il valore da assegnare
     * @param value : rappresenta il valore discreto
     */
    Item(Attribute attribute, Object value){
        this.attribute = attribute;
        this.value = value;
    }


    /**
     * Il metodo getAttribute;<br>
     * rappresenta una funzione di lettura
     *
     * @return :restituisce attribute.
     */
    Attribute getAttribute() {
        return attribute;
    }


    /**
     * Il metodo getValue;<br>
     * rappresenta una funzione di lettura
     *
     * @return : restituisce il valore di attribute
     */
    Object getValue() {
        return value;
    }

    /**
     * Il metodo String toString;<br>
     *  restituisce i valori di attribute
     */
    @Override
    public String toString() {

        return ""+value.toString();
    }


    abstract double distance(Object a);

    /**
     * Il metodo update;<br>
     * che aggiorna il valore dell'oggetto in base ai dati<br>
     * del dataset e ai dati del cluster corrente.
     *
     * @param data : rappresenta il dataset
     * @param clusteredData : rappresenta gli oggetti assegnati al cluster corrente.
     */
    public void update(Data data, Set<Integer> clusteredData) {
        value = data.computePrototype(clusteredData, attribute);
    }

}
