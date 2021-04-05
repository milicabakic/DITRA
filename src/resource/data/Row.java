package resource.data;


import java.util.HashMap;
import java.util.Map;


public class Row {

    private String name;
    private Map<String, Object> fields;


    public Row() {
        this.fields = new HashMap<>();
    }

    public void addField(String fieldName, Object value) {
        this.fields.put(fieldName, value);
    }

    public void removeField(String fieldName) {
        this.fields.remove(fieldName);
    }
    
    public Map<String, Object> getFields() {
		return fields;
	}
    
    public void setName(String name) {
		this.name = name;
	}
    
    public String getName() {
		return name;
	}
    
    public Map<String,Object> getFieldsByName(String string){
    	if(string == this.name)
    		return this.fields;
    	return null;
    }
    
    public String toString() {
    	return name + fields;
    }
    
    

}
