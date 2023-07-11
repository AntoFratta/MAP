package mining;

import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;

/**
 * La classe ClusterSet rappresenta l'insieme di cluster dell'algoritmo
 * k-means<br>
 * <br>
 *
 * Contiene un array di oggetti di tipo 'Cluster' e fornisce metodi per <br>
 * gestire l'insieme di cluster e eseguire operazioni su di essi,<br>
 * i metodi che questa classe fornisce sono :<br>
 * - l'aggiunta di un nuovo cluster;<br>
 * - ottenimento di un determinato cluster dell'array;<br>
 * - inizializzazione dei centroidi;<br>
 * - avendo una determinata tupla, ricercare il cluster piu' vicino;<br>
 * - aggiornare i centroidi contenuti nel cluster, conventendo
 * successivamente<br>
 * l'intero insieme dei cluster in una stringa<br>
 *
 * @author Paola
 *
 */

public class ClusterSet implements Serializable {

	private final Cluster C[];
	private int i = 0;

	/**
	 * Il costruttore ClusterSet<br>
	 * dovra' creare un nuovo insieme di cluster con una dimensione <br>
	 * specificata da 'k', lanciando un'eccezione 'OutOfRangeSampleSize' <br>
	 * se 'k' e' inferiore o uguale a zero.
	 *
	 * @param k : rappresenta i k valori dei cluster
	 */
	ClusterSet(int k) throws OutOfRangeSampleSize {
		if (k <= 0) {
			throw new OutOfRangeSampleSize();
		}
		C = new Cluster[k];
	}

	/**
	 * Il medoto add<br>
	 * Aggiunge un nuovo cluster all'insieme di cluster. <br>
	 * Il cluster viene inserito nella posizione corrente 'i' dell'array <br>
	 * e 'i' viene incrementato.
	 *
	 * @param c : rappresenta il cluster da inserire nell'i-esima posizone
	 */
	void add(Cluster c) {
		C[i] = c;
		i++;
	}

	/**
	 * Il metodo get<br>
	 * rappresenta la funzione di lettura per avere il cluster <br>
	 * nella posizione i-esima dell'array
	 *
	 * @param i : rappresenta il valore da inserire nel vettore C
	 * @return : restituisce il cluster in posizione 'i' dell'array
	 */
	Cluster get(int i) {
		return C[i];
	}

	/**
	 * Il metodo initializeCentroids;<br>
	 * scegliera' casualmente dei centroidi, creando un cluster per ogni centroide e
	 * <br>
	 * lo memorizza nel vettore C
	 *
	 * @param data : rappresenta il dataset, che viene utilizzata per creare nuovi
	 *             oggetti di<br>
	 *             tipo Cluster con i centroidi corrispondenti.
	 * @throws OutOfRangeSampleSize : rappresenta le eccezioni che un metodo puo'
	 *                              lanciare.
	 */

	void initializeCentroids(Data data) throws OutOfRangeSampleSize {
		int centroidIndexes[] = data.sampling(C.length);
		for (int centroidIndex : centroidIndexes) {
			Tuple centroidI = data.getItemSet(centroidIndex);
			add(new Cluster(centroidI));
		}
	}

	/**
	 * Il metodo nearestCluster<br>
	 * Trova il cluster piu' vicino alla tupla specificata calcolando la
	 * distanza<br>
	 * tra la tupla e i centroidi di ciascun cluster nell'insieme di cluster. <br>
	 *
	 * @param tuple :rappresenta la tupla passata in inout
	 * @return : restituira' il cluster piu' vicino
	 */
	Cluster nearestCluster(Tuple tuple) {
		double minocc = C[0].getCentroid().getDistance(tuple);
		Cluster t = this.get(0);
		for (int i = 1; i < C.length; i++) {
			if (minocc > C[i].getCentroid().getDistance(tuple)) {
				minocc = C[i].getCentroid().getDistance(tuple);
				t = this.get(i);
			}
		}
		return t;
	}

	/**
	 * Il metodo currentCluster<br>
	 * identifica e restituisce il cluster a cui appartiene la tupla con <br>
	 * l'ID specificato.
	 *
	 * @param id : rappresenta l'indice di cluster
	 * @return : restituisce null se la tupla non e' inclusa in nessun cluster
	 */

	Cluster currentCluster(int id) {
		for (Cluster c : C) {
			if (c.contain(id))
				return c;
		}
		return null;
	}

	/**
	 * Il metodo updateCentroids;<br>
	 * calcola il nuovo centroide per ciascun cluster nel vettore C.
	 *
	 * @param data : rappresenta dataset
	 */
	void updateCentroids(Data data) {
		for (Cluster c : C) {
			c.computeCentroid(data);
		}
	}

	/**
	 * Il metodo String toString<br>
	 * restituira' una stringa composta da ciascun centroide dell’insieme dei
	 * cluster<br>
	 * includendo solo i centroidi.
	 */

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < C.length; i++) {
			s += this.C[i].toString() + "\n";
		}
		return s;
	}

	/**
	 * Il metodo String toString<br>
	 * utilizzato per ottenere una stringa che descrive l'insieme di cluster <br>
	 * e i relativi centroidi, con una lista di esempi assegnati a ciascun
	 * cluster,<br>
	 * la distanza media tra gli esempi e il centroide e l'indice del cluster.
	 *
	 * @param data : rappresneta i singoli esempi della tabella
	 * @return : restituira' una stringa in cui descrive lo stato di ciascun cluster
	 *         nel vettore C,<br>
	 *         include anche la distanza media tra gli esempi e il centroide e
	 *         l'indice del cluster.
	 */
	public String toString(Data data) {
		String str = "";
		for (int i = 0; i < C.length; i++) {
			if (C[i] != null) {
				str += i + ":" + C[i].toString(data) + "\n";
			}
		}
		return str;
	}

}
