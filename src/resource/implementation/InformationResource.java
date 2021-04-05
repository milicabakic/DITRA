package resource.implementation;

import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import resource.DBNode;
import resource.DBNodeComposite;


public class InformationResource extends DBNodeComposite {

	
    public InformationResource(String name) {
        super(name, null);
    }
    
    @Override
    public String toString() {
    	return super.getName();
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Entity){
            Entity entity = (Entity) child;
            this.getChildren().add(entity);
        }
    }
    

	@Override
	public Enumeration<Entity> children() {
		return (Enumeration<Entity>) this.getChildren();
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
}
