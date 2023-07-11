package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

/**
 *
 * La classe KMeansMiner rappresenta l'implementazione dell'algoritmo<br>
 * K-means per la creazione di cluster.<br>
 * <br>
 *
 * Inizializza i centroidi iniziali, assegna gli esempi ai cluster
 * corrispondenti<br>
 * e calcola i nuovi centroidi in modo iterativo fino alla convergenza.<br>
 * <br>
 *
 * All'interno nel metodo 'kmeans', viene eseguita dalla classe KMeansMiner<br>
 * l'algoritmo K-Means suddivisa in tre fasi:<br>
 * <br>
 *
 * - nella prima fase, vengono iniziaizzati i cluster<br>
 * - nella seconda fase, viene eseguita l'assegnazione delle tuple ai <br>
 * cluster più vicini, effettuando il controllo del cambiamento <br>
 * dei cluster; se avviene inoltre un cambiamento, viene rimosso il<br>
 * nodo vecchio e viene aggiunto il nuovo cluster <br>
 * - nella terza ed ultima fase, vengono aggiornati i centroidi dei cluster <br>
 * inserendo i nuovi dati
 *
 *
 *
 * @author Paola
 *
 */

public class KMeansMiner {

	private final ClusterSet C;

	/**
	 * Il costruttore KMeansMiner<br>
	 * crea un oggetto ClusterSet con un numero specificato di cluster k.
	 *
	 * @param k : rappresenta i k cluster da generare
	 */
	public KMeansMiner(int k) throws OutOfRangeSampleSize {
		C = new ClusterSet(k);
	}

	/*
	 * public KMeansMiner(String fileName) throws FileNotFoundException,
	 * IOException, ClassNotFoundException { try (ObjectInputStream inputStream =
	 * new ObjectInputStream(new FileInputStream(fileName))) { C = (ClusterSet)
	 * inputStream.readObject(); } }
	 * 
	 * public void salva(String fileName) throws FileNotFoundException, IOException
	 * { try (ObjectOutputStream outputStream = new ObjectOutputStream(new
	 * FileOutputStream(fileName))) { outputStream.writeObject(C); } }
	 */

	public KMeansMiner(String fileName) throws IOException, ClassNotFoundException {
		try {
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			C = (ClusterSet) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();
		} catch (IOException e) {
			throw new IOException("Errore di I/O");
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException("Classe non trovata");
		}
	}

	public void salva(String fileName) throws IOException {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this.C);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			throw new IOException("Errore di I/O");
		}
	}

	/**
	 * Il metodo ClusterSet<br>
	 * rappresenta la funzione di lettura
	 *
	 * @return : restituira' l'oggetto ClusterSet associato a KMeansMiner.
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Il metodo kmeans;<br>
	 * dovendo ottenere il numero delle iterazioni eseguite, <br>
	 * dovra' eseguire l'algoritmo k-means eseguendo delle fasi:<br>
	 * 1- come prima fase sara' quella di scegliere in maniera casuale i
	 * centroidi<br>
	 * per k clusters;<br>
	 * 2- successivamente dovra' assegnera ogni riga della matrice in data al
	 * cluster <br>
	 * avente un centroide piu' vicino all'esempio;<br>
	 * 3- verranno calcolati dei nuovi centroidi per ciascun cluster;<br>
	 * 4- come ultima fase ci sara' il ritorno a due fasi precedentemente elencate
	 * (2 e 3)<br>
	 * finche' due iterazioni consecutive non restituiranno due centroidi uguali.
	 *
	 * @param data : rappresenta dataset
	 * @return : restituira' il numero delle iterazioni eseguite
	 * @throws OutOfRangeSampleSize : rappresenta le eccezioni che un metodo puo'
	 *                              lanciare.
	 */
	public int kmeans(Data data) throws OutOfRangeSampleSize {
		int numberOfIterations = 0;
		// STEP 1
		C.initializeCentroids(data);
		boolean changedCluster = false;
		do {
			numberOfIterations++;
			// STEP 2
			changedCluster = false;
			for (int i = 0; i < data.getNumberOfExamples(); i++) {
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
				Cluster oldCluster = C.currentCluster(i);
				boolean currentChange = nearestCluster.addData(i);
				if (currentChange)
					changedCluster = true;
				// rimuovo la tupla dal vecchio cluster
				if (currentChange && oldCluster != null)
					// il nodo va rimosso dal suo vecchio cluster
					oldCluster.removeTuple(i);
			}
			// STEP 3
			C.updateCentroids(data);
		} while (changedCluster);
		return numberOfIterations;
	}

}
