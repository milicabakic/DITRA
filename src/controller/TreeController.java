package controller;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import gui.MainFrame;
import resource.implementation.Entity;
import view.EntityView;

public class TreeController implements TreeSelectionListener {

	@Override
	public void valueChanged(TreeSelectionEvent e) {

		TreePath path = e.getPath();
		Object selectedComponent = path.getLastPathComponent();
		
		
		if(selectedComponent instanceof Entity) {
			focusTable((Entity) selectedComponent);
		}
	}
	
	public void focusTable(Entity table) {
		
		MainFrame.getInstance().getIr1().getTabPane().removeAll();
		
		EntityView view = new EntityView(table);
		MainFrame.getInstance().getIr1().addTab1(view);
        MainFrame.getInstance().setEntityView(view);
        MainFrame.getInstance().getEntityView().addToolbar();

		
		MainFrame.getInstance().getIr2().getTabPane().removeAll();
		
		for (Entity relation : table.getRelations()) {
			EntityView ev = new EntityView(relation);
			MainFrame.getInstance().getIr2().addTab2(ev);
		}
	}

}
