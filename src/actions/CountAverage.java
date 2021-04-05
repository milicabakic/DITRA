package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import gui.AddDialog;
import gui.ReportDialog;
import gui.MainFrame;
import gui.Tree;
import resource.implementation.Entity;

public class CountAverage extends AbstractDitra{
	
	public CountAverage() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		//putValue(SMALL_ICON, loadIcon("gui_icons/delete.png"));
		putValue(NAME, "Report");
		putValue(SHORT_DESCRIPTION, "Report");		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		if (selected instanceof Entity) {
			Entity entity = (Entity)selected;
		    new ReportDialog(entity);
		}
		
	}
	
	

}
