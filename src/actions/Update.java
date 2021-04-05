package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gui.MainFrame;
import gui.Tree;
import gui.UpdateDialog;
import resource.DBNode;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public class Update extends AbstractDitra{
	
	public Update() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("gui_icons/update.png"));
		putValue(NAME, "Update");
		putValue(SHORT_DESCRIPTION, "Update");		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		ArrayList<String> values = new ArrayList<String>();
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		Entity entity = null;
		if (selected instanceof Entity) {
			entity = (Entity)selected;
			int i = MainFrame.getInstance().getEntityView().getTable().getSelectedRow();
			if (i > -1) {
			Row row = MainFrame.getInstance().getEntityView().gettModel().getRows().get(i);
			for (DBNode node : entity.getChildren()) {
				Attribute a = (Attribute)node;
				String s = (String) row.getFields().get(a.getName());
				if (s == null) values.add("");
				else values.add(s);
			}
			String s = (String) row.getFields().get(MainFrame.getInstance().getEntityView().getEntity().getPrimaryKey().getName());
			UpdateDialog ud = new UpdateDialog(entity,MainFrame.getInstance().getEntityView().getEntity().getPrimaryKey().getName(),s);
			for (int j = 0 ; j < values.size() ; j++) {
				ud.getTextFields().get(j).setText(values.get(j));
			}
				
			}else {
				JOptionPane.showMessageDialog(null, "You must choose row first!");
			}
		}
		
		
	}

}
