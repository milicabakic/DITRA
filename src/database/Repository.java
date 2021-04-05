package database;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);
    
	boolean addRow(Entity table, ArrayList<String> fields);
	
	List<Row> sortRow(Entity table, ArrayList<String> select, ArrayList<String> descending, ArrayList<String> ascending);
	
	boolean deleteRow(Entity table, String namePrimary, String value, String type);
	
	boolean updateRow(Entity table, ArrayList<String> fields, String primary, String value);

	Map<String,List<Row>> relate(Entity table, int selectedRow);
	
	List<Row> executeQuery(String query, Entity entity);

}
