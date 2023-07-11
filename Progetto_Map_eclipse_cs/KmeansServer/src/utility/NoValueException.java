package utility;

public class NoValueException extends Exception{
    public NoValueException() {
        super("Questo valore non è presente nel resultset");
    }

}

