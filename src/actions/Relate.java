package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

import gui.InvalidValues;
import gui.MainFrame;
import gui.Tree;
import resource.data.Row;
import resource.implementation.Entity;

public class Relate extends AbstractDitra{

	public Relate() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		//putValue(SMALL_ICON, loadIcon("gui_icons/relations.png"));
		putValue(NAME, "Relations");
		putValue(SHORT_DESCRIPTION, "Relation");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		
		JTable table = MainFrame.getInstance().getIr1().getActiveTab().getTable();
        
		if(table.getSelectionModel().isSelectionEmpty()) {
			JOptionPane.showMessageDialog(null, "You must choose row first!");
			return;
		}
		
		int selectedRow = table.getSelectedRow();
		
		if (selected instanceof Entity) {
			Entity entity = (Entity)selected;
	        Map<String, List<Row>> mapa = MainFrame.getInstance().getAppCore().getDatabase().relateTables(entity, selectedRow);
            
	        if(mapa == null) {
	        	JOptionPane.showMessageDialog(null, "This row doesn't have any relation or you didn't choose required columns!");
	        	return;
	        }
	        	
	        int tabSz = MainFrame.getInstance().getIr2().getTabPane().getTabCount();
	        for(int i=0; i<tabSz; i++) {
	        	String foundTable = MainFrame.getInstance().getIr2().getTabPane().getComponentAt(i).getName();
	        	if (mapa.get(foundTable).size() > 0)
	        	MainFrame.getInstance().getIr2().getTabWithName(foundTable).gettModel().setRows(mapa.get(foundTable));
	        }
		}
		
		
		
	}

}
