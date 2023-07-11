package utility;

import utility.TableSchema.Column;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



//CLASSE DI MAURO DA CAMBIARE
public class TableData {

    DbAccess dbAccess;



    public TableData(DbAccess db) {
        this.dbAccess=db;
    }

    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, DatabaseConnectionException {
        List<Example> transazioni = new ArrayList<>();

        ResultSet rs = null;
        try {
            // Ottieni la connessione al database
            dbAccess.initConnection();

            // Otteni lo schema della tabella specificata
            TableSchema schema = new TableSchema(dbAccess, table);

            // Esegui la query per estrarre le tuple distinte dalla tabella
            String query = "SELECT DISTINCT * FROM " + table;
            Statement stmt = dbAccess.getConnection().createStatement();
            try {
				rs = stmt.executeQuery(query);
			} catch (Exception e) {
				System.out.println("Tabella inesistente");
			}
            

            // Verifica se il resultset è vuoto
            if (!rs.next()) {
                throw new EmptySetException();
            }

            // Crea oggetti utility.Example per ogni tupla del resultset
            do {
                Example example = new Example();

                // Estrai i valori dei campi dal resultset e aggiungili all'oggetto utility.Example
                for (int i = 0; i < schema.getNumberOfAttributes(); i++) {
                    Column column = schema.getColumn(i);
                    String columnName = column.getColumnName();
                    Object value;

                    // Verifica se il campo è di tipo numerico o di tipo stringa
                    if (column.isNumber()) {
                        value = rs.getDouble(columnName);
                    } else {
                        value = rs.getString(columnName);
                    }

                    example.add(value);
                }

                transazioni.add(example);
            } while (rs.next());

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            dbAccess.closeConnection();
        } catch (DatabaseConnectionException e) {
            throw new DatabaseConnectionException();
        }

        return transazioni;
    }


    public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException, DatabaseConnectionException {
        Set<Object> distinctValues = new TreeSet<>();

        try {
            // Ottieni la connessione al database
            dbAccess.initConnection();

            // Esegui la query per estrarre i valori distinti ordinati della colonna specificata
            String query = "SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY " + column.getColumnName();
            Statement stmt = dbAccess.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Aggiungi i valori distinti all'insieme
            while (rs.next()) {
                Object value;

                // Verifica se il campo è di tipo numerico o di tipo stringa
                if (column.isNumber()) {
                    value = rs.getDouble(1);
                } else {
                    value = rs.getString(1);
                }

                distinctValues.add(value);
            }

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            dbAccess.closeConnection();
        } catch (DatabaseConnectionException e) {
            throw new DatabaseConnectionException();
        }

        return distinctValues;
    }

    public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
            throws SQLException, NoValueException {
        Object aggregateValue = null;

        try {
            // Ottieni la connessione al database
            dbAccess.initConnection();

            // Seleziona l'operatore di aggregazione corrispondente
            String aggregateOperator = "";
            if (aggregate == QUERY_TYPE.MIN) {
                aggregateOperator = "MIN";
            } else if (aggregate == QUERY_TYPE.MAX) {
                aggregateOperator = "MAX";
            }

            // Esegui la query per calcolare l'aggregato desiderato nella colonna specificata
            String query = "SELECT " + aggregateOperator + "(" + column.getColumnName() + ") FROM " + table;
            Statement stmt = dbAccess.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Se il resultset ha almeno una riga, ottieni il valore dell'aggregato
            if (rs.next()) {
                if (column.isNumber()) {
                    aggregateValue = rs.getDouble(1);
                } else {
                    aggregateValue = rs.getString(1);
                }
            }

            // Chiudi il resultset, lo statement e la connessione
            rs.close();
            stmt.close();
            dbAccess.closeConnection();

            // Solleva un'eccezione se il valore aggregato è null
            if (aggregateValue == null) {
                throw new NoValueException();
            }
        } catch (DatabaseConnectionException e) {
            // Gestisci l'eccezione o propagala, se necessario
        }

        return aggregateValue;
    }

}