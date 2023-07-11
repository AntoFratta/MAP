package utility;

public class DatabaseConnectionException extends Exception{
    public DatabaseConnectionException() {
        super("Connessione al database fallita.");
    }
}
