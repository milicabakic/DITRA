package database;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface Database{

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);
    
    boolean addRowToTable(Entity tableName, ArrayList<String> fields);
    
    List<Row> sortDataInTable(Entity table, ArrayList<String> select, ArrayList<String> descending, ArrayList<String> ascending);
    
    boolean deleteRowInTable(Entity table, String namePrimary, String value, String type);
    
    boolean updateRowInTable(Entity table, ArrayList<String> fields, String primary, String value);

    Map<String,List<Row>> relateTables(Entity table, int selectedRow);
    
    List<Row> executeQuery(String query, Entity entity);

}
