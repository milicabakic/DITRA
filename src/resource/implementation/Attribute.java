package resource.implementation;


import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;


public class Attribute extends DBNodeComposite {


    private AttributeType attributeType;
    private int length;
    private Attribute inRelationWith;

    public Attribute(String name, DBNode parent) {
        super(name, parent);
    }

    public Attribute(String name, DBNode parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType = attributeType;
        this.length = length;
    }
    
    public AttributeType getAttributeType() {
		return attributeType;
	}
    public int getLength() {
		return length;
	}
    
    public Attribute getInRelationWith() {
		return inRelationWith;
	}
    
    @Override
    public String toString() {
    	return super.getName();
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof AttributeConstraint){
            AttributeConstraint attributeConstraint = (AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }

	@Override
	public Enumeration<AttributeConstraint> children() {
		return (Enumeration<AttributeConstraint>) this.getChildren();
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
	
	public boolean isAttributeString() {
		if(this.getAttributeType().equals(AttributeType.CHAR) || this.getAttributeType().equals(AttributeType.NVARCHAR))
			return true;
		
		if(this.getAttributeType().equals(AttributeType.VARCHAR))
			return true;
		
		return false;
	}
	
	public boolean isAttributeNumber() {
		if(this.getAttributeType().equals(AttributeType.INT))
			return true;
		
		if(this.getAttributeType().equals(AttributeType.DECIMAL))
			return true;
		
		if(this.getAttributeType().equals(AttributeType.FLOAT))
			return true;

		if(this.getAttributeType().equals(AttributeType.NUMERIC))
			return true;
		
		
		return false;
	}
	
	
}
