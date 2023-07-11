package data;
/**
 * La classe DiscreteItem<br>
 * rappresenta un oggetto di un <br>
 * attributo discreto e il suo valore. <br><br>
 *
 *
 * Tale classe estende la classe astratta Item;<br>
 * Il metodo che viene implementato da tale classe e':<br>
 * - distance che calcola la distanza tra due valori <br>
 * 	dell'attributo discreto.
 *
 * @author Paola
 *
 */


public class DiscreteItem extends Item{

    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }



    /**
     * Il metodo distance;<br>
     * restituira':<br>
     * Se i due valori sono uguali,<br>
     * la distanza sara' 0, altrimenti sara' 1.
     */
    double distance(Object a) {
        if(getValue().equals(a)) {
            return 0;
        } else
            return 1;
    }

}
