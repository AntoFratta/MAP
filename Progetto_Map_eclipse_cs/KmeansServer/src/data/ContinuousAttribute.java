package data;
/**
 * La classe ContinuousAttribute estende la classe astratta 'Attribute',<br>
 * rappresenta un attributo dove il valore viene definito al suo <br>
 * interno in un intervallo compreso tra min e max. <br><br>
 *
 *   Dunque gli attributi continui dei dati di input vengono gestiti <br>
 *   attraverso questa classe, utilizzata per effettuare l'implementazione <br>
 *   dell'algoritmo k-means.
 *
 *
 * @author Paola
 *
 */
public class ContinuousAttribute extends Attribute{

    private final double max;
    private final double min; /* rappresentano gli estremi dell'intervallo di valori (dominio) che
	l'attributo può assumere.*/


    /**
     *
     * Il metodo ContinuousAttribute<br>
     * inizializza gli attributi : <br>
     * - min;<br>
     * - max; <br>
     * - mentre name ed index verranno inizializzati utilizzando il costruttore della classe madre.
     *
     * @param name : rapprsenta il nome dell'attributo
     * @param index : rappresenta l'identificativo numerico dell'attributo
     * @param min : rappresenta il valore minimo che un attributo puo' assumere
     * @param max : rappresenta il valore massimo che un attributo puo' assumere
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.max = max;
        this.min = min;
    }


    /**
     *
     * Il metodo getScaledValue<br>
     *  restituira' il valore in input normalizzato. <br><br>
     *
     * Il valore normalizzato, verra' calcolato attraverso la seguente formula <br>
     * in un dominio compreso tra [0,1] : <br><br>
     *
     * v'=(v-min)/(max-min)
     *
     * @param v : valore normalizzato passato come input
     * @return : restituira' il valore normalizzato
     */
    double getScaledValue(double v) {
        return (v-min)/(max-min);
    }

}
