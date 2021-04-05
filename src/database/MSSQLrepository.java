package database;

import database.settings.Settings;
import gui.InvalidValues;
import gui.MainFrame;
import observer.Notification;
import observer.enums.NotificationCode;
import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


public class MSSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;
    private Map<String, String> mapa = new HashMap<String, String>();  //prvi string ime pk, drugi ime tabele
    
    public MSSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
    	Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (Exception e){
          //  e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }


    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("tim_11_bp2020");
            
            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()){
                String tableName = tables.getString("TABLE_NAME");
                Entity newTable = new Entity(tableName, ir);
                if (!newTable.getName().contains("trace")) {
                    ir.addChild(newTable);
                    ((InformationResource)(MainFrame.getInstance().getTree().getModel().getRoot())).addChild(newTable);
                }

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                
                while (columns.next()){

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    String isNullable = columns.getString("IS_NULLABLE");

                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    newTable.addChild(attribute);
                    
                    if (isNullable.equals("NO"))
                    	attribute.addChild(new AttributeConstraint(isNullable, attribute, ConstraintType.NOT_NULL));
                    
                    //dodavanje primarnih kljuceva
                    ResultSet pk = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                    String primary="";
                    while(pk.next()) {
                    	primary = pk.getString("COLUMN_NAME");
                    }
                    if (attribute.getName().equals(primary)) {
                    	AttributeConstraint a = new AttributeConstraint(primary,attribute,ConstraintType.PRIMARY_KEY);
                    	attribute.addChild(a);
                    	newTable.setPrimaryKey(a);
                    	
                    	mapa.put(primary, newTable.getName());
                    }
                    
                    
                    //dodavanje stranih kljuceva
                    ResultSet fk = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
                    String imported="";
                    while(fk.next()) {
                    	  imported = fk.getString("FKCOLUMN_NAME");
                    	  if (attribute.getName().equals(imported)) {
                          	  AttributeConstraint a = new AttributeConstraint(imported,attribute,ConstraintType.FOREIGN_KEY);
                          	  attribute.addChild(a);
                          	  newTable.getImportedKeys().add(a);
                    	  }
                    }
                  
                }

            }
            
            ArrayList<Entity> entitys = new ArrayList<Entity>();
            for (DBNode e : ir.getChildren())
            	entitys.add((Entity)e);
            
            for (Entity entity : entitys) {
            	for (AttributeConstraint ac : entity.getImportedKeys()) {
            		for (Entity other : entitys) {
            			if (!other.equals(entity)) {
            				if (other.getPrimaryKey().getName().equals(ac.getName())) {
            					entity.getRelations().add(other);
            					other.getRelations().add(entity);
            				}
            			}
            		}
            	}
            }

            return ir;

        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();
        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }
    
    @Override
    public boolean addRow(Entity table, ArrayList<String> fields) {
        
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO " + table.getName() + " VALUES (");
        for (int i = 0 ; i < table.getChildren().size() ; i++) {
        	if (fields.get(i).equals("")) sb.append("NULL, ");
        	else {
        	Attribute attribute = (Attribute)(table.getChildren().get(i));
        	if (attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR ||
        	    attribute.getAttributeType() == AttributeType.NVARCHAR) {
        		if (i != table.getChildren().size()-1)
        			sb.append("'" + fields.get(i) + "'" + ", ");
        		else
        			sb.append("'" + fields.get(i) + "'" + ")");
        	}else if (attribute.getAttributeType() == AttributeType.DATETIME || attribute.getAttributeType() == AttributeType.DATE) {
        		
        		if (i != table.getChildren().size()-1)
        			sb.append(" CONVERT(DATE, '" + fields.get(i) + "', 106), ");
        		else
        			sb.append(" CONVERT(DATE, '" + fields.get(i) + "', 106))");
        		
        	}else {
        		
        		if (i != table.getChildren().size()-1)
        			sb.append( fields.get(i) + ", ");
        		else
        			sb.append(fields.get(i) + ")");
        		
        	}
        	}
        }
        if (sb.charAt(sb.length()-2) == ',') sb.deleteCharAt(sb.length()-2);
        if (sb.charAt(sb.length()-1) != ')') sb.append(")");
        System.out.println(sb.toString());
        try{
            this.initConnection();

            String query = sb.toString();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
        catch (Exception e) {
            new InvalidValues();
            return false;
        }
        finally {
            this.closeConnection();
        }
        return true;
    }

	@Override
	public List<Row> sortRow(Entity table, ArrayList<String> select, ArrayList<String> descending, ArrayList<String> ascending) {
		List<Row> rows = new ArrayList<Row>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		for (int i = 0 ; i < select.size() ; i++) {
			String s = select.get(i);
			if (i != select.size()-1) sb.append(s + ", ");
			else sb.append(s + " ");
		}
		if (select.size()==0) sb.append("* ");
		sb.append("FROM " + table.getName());
		if (descending.size() > 0 || ascending.size() > 0) sb.append(" ORDER BY ");
		for (int i = 0 ; i < descending.size() ; i++) {
			String s = descending.get(i);
			if (i != descending.size()-1 || ascending.size()>0)
				sb.append(s + " desc, ");
			else
				sb.append(s + " desc");
		}
		for (int i = 0 ; i < ascending.size(); i++) {
			String s = ascending.get(i);
			if (i != ascending.size()-1)
				sb.append(s + " asc, ");
			else
				sb.append(s + " asc");
		}
		System.out.println(sb.toString());
		try{
            this.initConnection();

            String query = sb.toString();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()){

                Row row = new Row();
                row.setName(table.getName());

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            new InvalidValues();
        }
        finally {
            this.closeConnection();
        }
		return rows;
	}

	@Override
	public boolean deleteRow(Entity table, String namePrimary, String value, String type) {
		String query="";
		if (type.equals("number")) query = "DELETE FROM " + table.getName() + " WHERE " + namePrimary + " = " + value;
		else if (type.equals("char")) query = "DELETE FROM " + table.getName() + " WHERE " + namePrimary + " LIKE '" + value + "'";
		else query = "DELETE FROM " + table.getName() + " WHERE " + namePrimary + " = '" + value + "'";
		System.out.println(query);
		try{
            this.initConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();

        }
        catch (Exception e) {
            new InvalidValues();
            return false;
        }
        finally {
            this.closeConnection();
        }
		return true;
	}

	@Override
	public boolean updateRow(Entity table, ArrayList<String> fields, String primary, String value) {
		
		    StringBuilder sb = new StringBuilder();
		    
	        sb.append("UPDATE " + table.getName() + " SET ");
	        
	        for (int i = 0 ; i < table.getChildren().size() ; i++) {
	        	
	        	if (fields.get(i).equals("")) continue;
	        	
	        	Attribute attribute = (Attribute)(table.getChildren().get(i));
	        	if (attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR ||
	        	    attribute.getAttributeType() == AttributeType.NVARCHAR) {
	        		      sb.append(attribute.getName() + " = '" + fields.get(i) + "'" + ", ");
	        		      
	        	}else if (attribute.getAttributeType() == AttributeType.DATETIME || attribute.getAttributeType() == AttributeType.DATE) {
	        			  sb.append(attribute.getName() + " = CONVERT(DATE, '" + fields.get(i) + "', 106), ");
	        			  
	        	}else {
	        			  sb.append(attribute.getName() + " = " + fields.get(i) + ", ");
	        	}
	        }
	        if (sb.charAt(sb.length()-2) == ',') sb.deleteCharAt(sb.length()-2);

	        //primarni kljuc
	        Attribute attribute = (Attribute)(table.getChildren().get(0));	        
	        sb.append(" WHERE " + primary + " = ");
	        if (attribute.getAttributeType() == AttributeType.CHAR || attribute.getAttributeType() == AttributeType.VARCHAR ||
	        	    attribute.getAttributeType() == AttributeType.NVARCHAR) {
	        	sb.append("'" + value + "'");
	        }else {
	        	sb.append(value);
	        }
	        System.out.println(sb.toString());
	        
	        
	        try{
	            this.initConnection();

	            PreparedStatement preparedStatement = connection.prepareStatement(sb.toString());
	            preparedStatement.execute();

	        }
	        catch (Exception e) {
	            new InvalidValues();
	            return false;
	        }
	        finally {
	            this.closeConnection();
	        }
	        
		return false;
	}
	
	@Override
	public Map<String,List<Row>> relate(Entity table, int selectedRow) {
        Map<String,List<Row>> toReturn = new HashMap<String, List<Row>>();   //String naziv tabele, red koji vracamo
		
        try{
            this.initConnection();
            
            List<String> fkeys = table.getForeignKeys();
            List<Entity> relations = table.getRelations();
            
            for(int i=0; i<fkeys.size(); i++) {
            	if(mapa.get(fkeys.get(i))== null)
            		continue;
    /*        	
                String query = "SELECT "+ fkeys.get(i) +" FROM " + table.getName();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();
      */                 
                String keyValue = "";
                
                List<Row> rowsInTable = MainFrame.getInstance().getIr1().getTabWithName(table.getName()).gettModel().getRows();
        
                for(int k=0; k<rowsInTable.size(); k++) {
                	if(k == selectedRow) {
                		Row selected = rowsInTable.get(k);                		
                		keyValue = (String) selected.getFields().get(fkeys.get(i));
                		
                		if(keyValue == null) {
                			break;
                		}
                	}
                }
                

                if(keyValue == null) 
        			return null;                 
                
      /*          int cnt = 1;
                while(rs.next()) {
                	if(cnt == selectedRow + 1) {
                		keyValue = rs.getString(1);
                		break;
                	}
                	cnt++;
                }*/
           
                //prolazak kroz tabele dole
                String query1;
                
                if(((Attribute) table.getChildByName(fkeys.get(i))).isAttributeString())
                    query1 = "SELECT * FROM " + mapa.get(fkeys.get(i))+" WHERE "+fkeys.get(i)+" LIKE "+"'" +keyValue +"'";
                
                else
                    query1 = "SELECT * FROM " + mapa.get(fkeys.get(i))+" WHERE "+fkeys.get(i)+" = "+keyValue;
                	
               
                
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                ResultSet rs1 = preparedStatement1.executeQuery();
                List<Row> rows = new ArrayList<>();
                           
                while (rs1.next()){

                    Row row = new Row();
                    row.setName(mapa.get(fkeys.get(i)));

                    ResultSetMetaData resultSetMetaData = rs1.getMetaData();
                    for (int j = 1; j<=resultSetMetaData.getColumnCount(); j++){
                        row.addField(resultSetMetaData.getColumnName(j), rs1.getString(j));
                    }
                                       
                    rows.add(row);

                }
                
                toReturn.put(mapa.get(fkeys.get(i)), rows);
            }
            
            
            for(int i=0; i<relations.size(); i++) {
            	if(toReturn.containsKey(relations.get(i).getName()))  //ako smo vec nasli red za tu relaciju
            		continue;                                         //nastavljamo dalje
            	
            	String pkName = table.getPrimaryKeyName();
            	String primaryKeyValue = "";          //u prvom delu metode, pretraga po stranim kljucevima
                                                      //sada po primarnom koji ce biti strani u relacijama
                List<Row> rowsInTable = MainFrame.getInstance().getIr1().getTabWithName(table.getName()).gettModel().getRows();
                
                for(int k=0; k<rowsInTable.size(); k++) {
                	if(k == selectedRow) {
                		Row selected = rowsInTable.get(k);                		
                		primaryKeyValue = (String) selected.getFields().get(pkName);		
                	}
                }
                
                String query1;
                
                if(((Attribute) table.getChildByName(pkName)).isAttributeString())
                    query1 = "SELECT * FROM " + relations.get(i).getName() +" WHERE "+pkName+" LIKE "+"'" +primaryKeyValue +"'";
                
                else
                    query1 = "SELECT * FROM " + relations.get(i).getName()+" WHERE "+pkName+" = "+primaryKeyValue;
                	
               System.out.println(query1);
                
                PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
                ResultSet rs1 = preparedStatement1.executeQuery();
                List<Row> rows = new ArrayList<>();
                
                
                           
                while (rs1.next()){

                    Row row = new Row();
                    row.setName(relations.get(i).getName());

                    ResultSetMetaData resultSetMetaData = rs1.getMetaData();
                    for (int j = 1; j<=resultSetMetaData.getColumnCount(); j++){
                        row.addField(resultSetMetaData.getColumnName(j), rs1.getString(j));
                    }
                                       
                    rows.add(row);

                }
                
                if(rows.size() == 0) {
                	Row newRow = new Row();
                	rows.add(newRow);
                }
                
                toReturn.put(relations.get(i).getName(), rows);
            }

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			this.closeConnection();
		}
		
		return toReturn;
	}

	@Override
	public List<Row> executeQuery(String query, Entity entity) {
		List<Row> rows = new ArrayList<Row>();
		try{
            this.initConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){

                Row row = new Row();
                row.setName(entity.getName());

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);

            }
        }
        catch (Exception e) {
            new InvalidValues();
        }
        finally {
            this.closeConnection();
        }
		return rows;
	}
	

	
	
}
