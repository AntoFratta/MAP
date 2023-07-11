package mining;

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * La classe Cluster rappresenta un cluster in un algoritmo di clustering.<br>
 * Un cluster e' un gruppo di oggetti simili o correlati che vengono raggruppati
 * <br>
 * insieme sulla base di determinate caratteristiche o attributi. <br>
 * <br>
 *
 *
 * La classe Cluster, inoltre presenta :<br>
 * - riferimento al centroide;<br>
 * - un insieme di transazioni, come (clusteredData);<br>
 * - metodi per calcolare il centroide;<br>
 * - l'aggiunta di transazioni dall'insieme di transazioni del cluster;<br>
 * - la cancellazione di transazioni dall'insieme di transazioni del
 * cluster;<br>
 * - la verifica che tale transazione e' presente all'interno del cluster<br>
 * - un metodo, il quale rappresenta il cluster come stringa dove sono
 * contenute<br>
 * le informazioni sul centroide e sulle transazioni presenti nel cluster;<br>
 * - la distanza media delle transazioni del centroide <br>
 *
 *
 * @author Paola
 *
 */

public class Cluster implements Serializable {
	private final Tuple centroid;

	private final Set<Integer> clusteredData;

	/**
	 * Il costruttore Cluster<br>
	 * Inizializza un nuovo cluster con il centroide specificato.
	 *
	 * @param centroid : rappresenta i centroidi inizializzati
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
		clusteredData = new HashSet<Integer>();
	}

	/**
	 * Il metodo Tuple<br>
	 * rappresneta una funzione di lettura
	 *
	 * @return : restituisce il centroide del cluster.
	 */
	Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Il metodo computeCentroid <br>
	 * aggiorna i valori del centroide in base <br>
	 * agli esempi appartenenti al cluster.
	 *
	 * @param data : rappresenta il dataset in cui aggiungere il centroide
	 */
	void computeCentroid(Data data) {
		for (int i = 0; i < centroid.getLength(); i++) {
			centroid.get(i).update(data, clusteredData);

		}

	}

	/**
	 * Il metodo addData<br>
	 * questo metodo indica l'aggiunta di un nuovo dato al cluster <br>
	 * specificando l'ID
	 *
	 * @param id : rappresenta il dato da aggiungere al cluster
	 * @return : restituira' true se il dato cambia cluster
	 */
	boolean addData(int id) {
		return clusteredData.add(id);

	}

	/**
	 * Il metodo contain<br>
	 * verifica se un dato e' clusterizzato nel cluster corrente.
	 *
	 * @param id : rappresenta il dato da verificare nel cluster
	 * @return : restituira' true se la verifica e' corretta
	 */
	boolean contain(int id) {

		return clusteredData.contains(id);
	}

	/**
	 * Il matedo removeTuple<br>
	 * rimuove un dato dal cluster specificando l'ID.
	 *
	 * @param id : rappresenta il dato da rimuovere dal cluster
	 */
	void removeTuple(int id) {

		clusteredData.remove(id);
	}

	/**
	 * Il metodo toString<br>
	 * restituisce una stringa che rappresenta il centroide del cluster.
	 *
	 */
	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++)
			str += centroid.get(i) + " ";
		str += ")";
		return str;
	}

	/**
	 * Il metodo toString(Data data)<br>
	 * restituisce una stringa che rappresenta il cluster completo,<br>
	 * includendo il centroide e gli esempi che appartengono al cluster.<br>
	 * La stringa include anche la distanza media tra gli esempi e il centroide.
	 *
	 * @param data : Questo parametro viene utilizzato per ottenere i valori <br>
	 *             degli attributi degli esempi che appartengono al cluster.
	 * @return : restituisce la stringa che rappresenta il cluster completo
	 */
	public String toString(Data data) {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}
		str += ")\nExamples:\n";

		for (int i : clusteredData) {
			str += "[";
			for (int j = 0; j < data.getNumberOfAttributes(); j++) {
				str += data.getAttributeValue(i, j) + " ";
			}
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(i)) + "\n";
		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
		return str;
	}

}
