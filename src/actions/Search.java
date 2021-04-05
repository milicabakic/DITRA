package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.MainFrame;
import gui.SearchDialog;
import gui.Tree;
import resource.implementation.Entity;

public class Search extends AbstractDitra{

	
	public Search() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
	//	putValue(SMALL_ICON, loadIcon("gui_icons/relations.png"));
		putValue(NAME, "Search");
		putValue(SHORT_DESCRIPTION, "Search");
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		
		if(selected instanceof Entity) {
			Entity entity = (Entity) selected;
			new SearchDialog(entity);
		}
	}

}
