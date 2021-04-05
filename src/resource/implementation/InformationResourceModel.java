package resource.implementation;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


public class InformationResourceModel extends DefaultTreeModel {

	public InformationResourceModel() {
		super(new InformationResource("DiTRa"));
	}
	
	public void addEntity(Entity entity){
		((InformationResource)getRoot()).addChild(entity);
	}
	
	

}
