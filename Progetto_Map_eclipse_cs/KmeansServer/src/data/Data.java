package data;

import utility.*;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

/**
 * 
 * La classe Data, e' rappresentata da una lista di oggetti di tipo
 * utility.Example <br>
 * 'attributeSet' una lista di oggetti di tipo attribute, che descrivono le
 * colonne che in questo caso saranno :<br>
 * Outlook, Temperature, Humidity, Wind, PlayTennis.<br>
 * 'numberOfExample', un intero che rappresenta le righe che potrenno avere i
 * seguenti valori :
 * Sunny,Rain,Overcast,Hot,Mid,Cool,Normal,High,Weak,Strong,Yes,No
 *
 * @author Paola
 *
 */

public class Data {
	// Le visibilità di classi , attributi e metodi devono essere decise dagli
	// studenti
	private List<Example> data;
	private final int numberOfExamples;
	private List<Attribute> attributeSet = new LinkedList<>();

	public Data(String tableName) throws SQLException, DatabaseConnectionException, NoValueException {
		DbAccess dbAccess = new DbAccess();
		dbAccess.initConnection();

		TableData tableData = new TableData(dbAccess);
		TableSchema tableSchema = new TableSchema(dbAccess, tableName);

		try {
			data = tableData.getDistinctTransazioni(tableName);
		} catch (SQLSyntaxErrorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EmptySetException e) {
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			e.printStackTrace();
		}

		numberOfExamples = data.size();
		attributeSet = new ArrayList<>();
		for (int i = 0; i < tableSchema.getNumberOfAttributes(); i++) {
			try {
				if (!tableSchema.getColumn(i).isNumber()) {
					TreeSet<String> values = new TreeSet<>();
					for (Object obj : tableData.getDistinctColumnValues(tableName, tableSchema.getColumn(i))) {
						values.add((String) obj);
					}
					attributeSet.add(new DiscreteAttribute(tableSchema.getColumn(i).getColumnName(), i, values));
				} else {
					double min = (Double) tableData.getAggregateColumnValue(tableName, tableSchema.getColumn(i),
							QUERY_TYPE.MIN);
					double max = (Double) tableData.getAggregateColumnValue(tableName, tableSchema.getColumn(i),
							QUERY_TYPE.MAX);
					attributeSet.add(new ContinuousAttribute(tableSchema.getColumn(i).getColumnName(), i, min, max));
				}
			} catch (SQLException | DatabaseConnectionException e) {
				e.printStackTrace();
			}
		}
	}

	public int getNumberOfExamples() {

		return numberOfExamples;
	}

	public int getNumberOfAttributes() {

		return attributeSet.size();
	}

	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	Attribute getAttribute(int index) {
		return attributeSet.get(index);
	}

	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(attributeSet.size());
		for (int i = 0; i < attributeSet.size(); i++)
			if (attributeSet.get(i) instanceof ContinuousAttribute)
				tuple.add(
						new ContinuousItem((ContinuousAttribute) attributeSet.get(i), (Double) data.get(index).get(i)),
						i);
			else
				tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet.get(i), (String) data.get(index).get(i)),
						i);
		return tuple;
	}

	public int[] sampling(int k) throws OutOfRangeSampleSize {
		if (k <= 0 || k > data.size()) {
			throw new OutOfRangeSampleSize();
		}
		int[] centroidIndexes = new int[k]; // choose k random different centroids in data.
		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		for (int i = 0; i < k; i++) {
			boolean found;
			int c;
			do {
				found = false;
				c = rand.nextInt(getNumberOfExamples()); // verify that centroid[c] is not equal to a centroid already
															// stored in CentroidIndexes
				for (int j = 0; j < i; j++)
					if (compare(centroidIndexes[j], c)) {
						found = true;
						break;
					}
			} while (found);
			centroidIndexes[i] = c;
		}
		return centroidIndexes;
	}

	private boolean compare(int i, int j) {
		for (int k = 0; k < attributeSet.size(); k++)
			if (!data.get(i).get(k).equals(data.get(j).get(k)))
				return false;
		return true;
	}

	Object computePrototype(Set<Integer> idList, Attribute attribute) {
		if (attribute instanceof ContinuousAttribute) {
			return computePrototype(idList, (ContinuousAttribute) attribute);
		} else
			return computePrototype(idList, (DiscreteAttribute) attribute);
	}

	String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
		int maxocc = 0;
		String x = "";
		for (String str : attribute) {
			if (maxocc < attribute.frequency(this, (Set<Integer>) idList, str)) {
				maxocc = attribute.frequency(this, (Set<Integer>) idList, str);
				x = str;
			}
		}
		return x;
	}

	Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
		double somma = 0.0;
		double media = 0.0;
		for (int id : idList) {
			somma += (Double) data.get(id).get(attribute.getIndex());
		}
		media = somma / idList.size();
		return media;
	}

	public String toString() {
		String s = "";
		int count = 1;
		for (int i = 0; i < getNumberOfAttributes(); i++) {
			if (i == 4) {
				s += getAttribute(i).getName() + "\n\n";
			} else {
				s += getAttribute(i).getName() + ",";
			}
		}
		for (int j = 0; j < getNumberOfExamples(); j++) {
			s += count + ":";
			for (int k = 0; k < 5; k++) {
				s += getAttributeValue(j, k).toString() + " ";
			}
			s += "\n\n";
			count++;
		}

		return s;
	}

}
