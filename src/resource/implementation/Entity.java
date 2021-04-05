package resource.implementation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;


public class Entity extends DBNodeComposite {
	
    private ArrayList<Entity> relations = new ArrayList<Entity>();
    
    private AttributeConstraint primaryKey;
    private ArrayList<AttributeConstraint> importedKeys = new ArrayList<AttributeConstraint>();
    
    public Entity(String name, DBNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }

    }
    
    public void setPrimaryKey(AttributeConstraint primaryKey) {
		this.primaryKey = primaryKey;
	}
    
    public AttributeConstraint getPrimaryKey() {
		return primaryKey;
	}
    
    public ArrayList<AttributeConstraint> getImportedKeys() {
		return importedKeys;
	}
    
    public ArrayList<Entity> getRelations() {
		return relations;
	}
    
    @Override
    public String toString() {
    	return super.getName();
    }

	@Override
	public Enumeration<Attribute> children() {
		return (Enumeration<Attribute>) this.getChildren();
	}

	@Override
	public boolean getAllowsChildren() {
		return true;
	}

	@Override
	public TreeNode getChildAt(int childIndex) {
		return this.getChildren().get(childIndex);
	}

	@Override
	public int getChildCount() {
		return this.getChildren().size();
	}

	@Override
	public int getIndex(TreeNode node) {
		return this.getChildren().indexOf(node);
	}

	@Override
	public boolean isLeaf() {
		return false;
	}
	
	public List<String> getForeignKeys() {
		List<String> list = new ArrayList<String>();
		
		for(int i=0; i<getChildCount(); i++) {
			for(int j=0; j<this.getChildAt(i).getChildCount(); j++) {
				if(((AttributeConstraint) this.getChildAt(i).getChildAt(j)).getConstraintType().equals(ConstraintType.FOREIGN_KEY)) {
					list.add(((Attribute) this.getChildAt(i)).getName());
				}
			}
		}
		
		return list;
	}
	
	public String getPrimaryKeyName() {
		String name = "";
		
		for(int i=0; i<getChildCount(); i++) {
			for(int j=0; j<this.getChildAt(i).getChildCount(); j++) {
				if(((AttributeConstraint) this.getChildAt(i).getChildAt(j)).getConstraintType().equals(ConstraintType.PRIMARY_KEY)) {
					name = ((Attribute) this.getChildAt(i)).getName();
				}
			}
		}
		
		return name;
	}
	
	
	public AttributeType getKeyAttributeType(String attributeName) {
		
		for(int i=0; i<getChildCount(); i++) {
			if(((Attribute) this.getChildAt(i)).getName().equals(attributeName))
				return ((Attribute) this.getChildAt(i)).getAttributeType();
		}
			
		return null;	
	}
	
}
