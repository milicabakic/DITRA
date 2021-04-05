package database;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseImplementation implements Database {

    private Repository repository;


    public DatabaseImplementation(Repository repository) {
		super();
		this.repository = repository;
	}

	@Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

	@Override
	public boolean addRowToTable(Entity table, ArrayList<String> fields) {
		return repository.addRow(table, fields);
	}

	@Override
	public List<Row> sortDataInTable(Entity table, ArrayList<String> select, ArrayList<String> descending,
			ArrayList<String> ascending) {
		return repository.sortRow(table, select, descending, ascending);
	}

	@Override
	public boolean deleteRowInTable(Entity table, String namePrimary, String value, String type) {
		return repository.deleteRow(table, namePrimary, value, type);
	}

	@Override
	public boolean updateRowInTable(Entity table, ArrayList<String> fields, String primary, String value) {
		return repository.updateRow(table, fields, primary, value);
	}
	
	@Override
	public Map<String,List<Row>> relateTables(Entity table, int selectedRow) {
		 return repository.relate(table, selectedRow);
	}

	@Override
	public List<Row> executeQuery(String query, Entity entity) {
		return repository.executeQuery(query, entity);
	}
	
}
