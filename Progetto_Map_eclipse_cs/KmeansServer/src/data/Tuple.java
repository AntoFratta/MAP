package data;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * La classe Tuple rappresenta una tupla di oggetti Item. <br>
 * Un oggetto Tuple contiene un array di oggetti Item e fornisce metodi<br>
 * per accedere agli elementi dell'array, per calcolare la distanza tra <br>
 * due tuple e per calcolare la media di una certa distanza tra due elementi.
 * <br>
 * La classe e' utilizzata all'interno di un'implementazione di clustering <br>
 * basata su prototipi, dove ogni tupla rappresenta un'istanza del dataset.
 * 
 * @author Paola
 *
 */
public class Tuple implements Serializable {

	private final Item[] tuple;

	/**
	 * Il costruttore Tuple;<br>
	 * costruira' l'oggetto riferito da tuple, avendo come input il numero di item
	 * da <br>
	 * cui e' possibile costruire l'oggetto .
	 *
	 * @param size : rappresenta la lunghezza della tupla
	 */
	Tuple(int size) {
		this.tuple = new Item[size];
	}

	/**
	 * Il metodo getLength;<br>
	 * rappresenta la funzione di lettura
	 *
	 * @return : restituisce la lunghezza della tupla
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Il metodo get;<br>
	 * rappresenta la funzione di lettura
	 *
	 * @param i : rappresenta l'indice dela cella di cui voglio il valore
	 * @return : restituisce il valore della cella i-esima
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Il metodo add;<br>
	 * aggiunge c nella posizione i-esima di tuple
	 *
	 * @param c : rappresenta l'Item da inserire in tuple
	 * @param i : rappresenta la posizione i-esima dove inserire l'Item
	 */
	void add(Item c, int i) {
		tuple[i] = c;
	}

	/**
	 * Il metodo getDistance;<br>
	 * calcola la distanza ottenuta tra la tupla riferita da obj e la tupla
	 * corrente.<br>
	 * Tale distanza e' ottenuta come la somma delle distanze tra gli Item in
	 * posizioni <br>
	 * uguali nelle due tuple.
	 *
	 * @param obj : rappresenta la tupla di cui voglio calcolare la distanza
	 * @return : distanze tra gli Item in posizioni uguali nelle due tuple.
	 */
	public double getDistance(Tuple obj) {
		double d = 0;
		for (int i = 0; i < tuple.length; i++) {
			d += this.get(i).distance(obj.get(i).getValue());
		}
		return d;
	}

	/**
	 *
	 * La funzione 'avgDistance' della classe 'Tuple' calcola la media delle
	 * distanze <br>
	 * tra la tupla corrente e un insieme di tuple, rappresentate dai valori
	 * indicizzati <br>
	 * nel set 'clusteredData', provenienti dalla matrice di dati 'data'.
	 *
	 * @param data          : rappresenta una tabella di dati contenente un insieme
	 *                      di oggetti Tuple.
	 * @param clusteredData : un insieme di interi che rappresenta gli indici delle
	 *                      tuple presenti nella tabella data che appartengono allo
	 *                      stesso cluster.
	 * @return : he rappresenta la media delle distanze tra la tupla corrente e le
	 *         tuple della matrice di dati data che sono indicate dal Set
	 *         clusteredData.
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double sumD = 0.0;
		for (int index : clusteredData) {
			Tuple tuple = data.getItemSet(index);
			double d = getDistance(tuple);
			sumD += d;
		}
		double p = sumD / clusteredData.size();
		return p;
	}

}
