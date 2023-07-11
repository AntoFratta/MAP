package data;
/**
 * La classe OutOfRangeSampleSize viene utilizzata per gestire l'eccezione<br>
 * di un valore k non valido all'interno dell'algoritmo K-means, in modo da fornire<br>
 * all'utente un feedback piu' specifico e comprensibile rispetto all'eccezione standard.
 *
 * @author Paola
 *
 */
public class OutOfRangeSampleSize extends Exception{
    public OutOfRangeSampleSize() {
        super("il numero k di cluster inserito da tastiera è <=0 o è maggiore rispetto al numero di centroidi generabili dall'insieme di transazioni.");
    }
}
