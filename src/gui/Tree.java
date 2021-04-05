package gui;

import javax.swing.JTree;
import javax.swing.SwingUtilities;

import controller.TreeController;
import resource.implementation.Entity;
import resource.implementation.InformationResourceModel;
import view.TreeRenderer;


public class Tree extends JTree {
	
public Tree() {
		
		addTreeSelectionListener(new TreeController());
	    //setCellEditor(new TreeEditor(this,new DefaultTreeCellRenderer()));
	    setCellRenderer(new TreeRenderer());
	    setEditable(false);
	}
	
	
	public void addEntity(Entity entity){
		((InformationResourceModel)getModel()).addEntity(entity);
		SwingUtilities.updateComponentTreeUI(this);
	}

}
