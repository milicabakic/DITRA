package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import gui.AddDialog;
import gui.MainFrame;
import gui.FilterSortDialog;
import gui.Tree;
import resource.implementation.Entity;

public class FilterSort extends AbstractDitra {
	
	public FilterSort() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("gui_icons/filter.png"));
		putValue(NAME, "Filter&Sort");
		putValue(SHORT_DESCRIPTION, "Filter&Sort");		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		if (selected instanceof Entity) {
			Entity entity = (Entity)selected;
		    new FilterSortDialog(entity);
		}
		
	}

}
