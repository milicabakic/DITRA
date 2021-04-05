package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import gui.AddDialog;
import gui.MainFrame;
import gui.Tree;
import resource.implementation.Entity;

public class Add extends AbstractDitra{
	
	public Add() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("gui_icons/add.png"));
		putValue(NAME, "Add");
		putValue(SHORT_DESCRIPTION, "Add");
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		if (selected instanceof Entity) {
			Entity entity = (Entity)selected;
		    new AddDialog(entity);
		}

		
	}

}
