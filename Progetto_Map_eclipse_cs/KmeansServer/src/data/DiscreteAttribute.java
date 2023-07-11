package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * La classe DiscreteAttribute e' una sottoclasse della classe Attribute, che rappresenta<br>
 *  un attributo di tipo discreto all'interno di un dataset. <br>
 *  Questa classe possiede un insieme di valori discreti, rappresentati come una TreeSet<String>.<br> <br>
 *
 * La classe fornisce anche alcuni metodi, attraverso i quali e' possibile:<br>
 *   - ottenere il numero di valori distinti che puo' assumere l'attributo, <br>
 *   - contare il numero di volte che un certo valore specifico appare tra <br>
 *     gli oggetti del dataset.
 *
 *
 * @author Paola
 *
 */
public class DiscreteAttribute extends Attribute implements Iterable<String>{

    private final TreeSet<String> values;



    /**
     * Il metodo DiscreteAttribute;<br>
     * richiama il costruttore della classe madre, inizializzando a sua volta<br>
     * il membro values con il parametro in input.<br><br>
     *
     *
     * @param name : rappresenta il nome dell'attributo
     * @param index : rappresenta l'identificativo numerico dell'attributo
     * @param values : rappresenta il valore dell'attributo passato in input
     */
    public DiscreteAttribute(String name, int index, TreeSet<String> values) {
        super(name, index);
        this.values = values;
    }



    /**
     * Il metodo getNumberOfDistinctValues;<br>
     *restituisce il numero di valori distinti presenti <br>
     *nell'insieme di valori discreti dell'attributo
     *
     * @return : restituira' il numero dei valori discreti
     */
    int getNumberOfDistinctValues() {
        return values.size();
    }


    /**
     *
     * Il metodo frequency;<br>
     * restituisce il numero delle occorenze del valore discreto,<br>
     * dunque dovra' determinare quante volte il valore v appare in corrispondenza <br>
     * dell'attributo corrente, all'interno degli esempi (righe), indicizzate da idList.
     *
     *
     * @param data : rappresenta il dataset
     * @param idList : rappresenta l'insieme degli attributi, in cui andremo a cercare il valore
     * @param v :  rappresenta il valore dell'attributo per cui si vuole calcolare la frequenza.
     * @return : restituira' quante volte il valore v appare in corrispondenza <br>
     * 			degli attributi indicati da idList
     */
    int frequency(Data data, Set<Integer> idList, String v) {
        Object[] p = idList.toArray();
        int count=0;
        for(int i=0; i<p.length; i++) {
            if(v.equals(data.getAttributeValue((int) p[i], this.getIndex())))
                count++;
        }
        return count;
    }

    /**
     * Il metodo iterator<br>
     * fornisce un iteratore che permette di scorrere<br>
     * gli attributi in maniera sequenziale e ordinata.
     */
    public Iterator<String> iterator() {
        return values.iterator();
    }

}
