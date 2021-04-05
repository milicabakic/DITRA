package actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import gui.MainFrame;
import gui.Tree;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public class Delete extends AbstractDitra{

	public Delete() {
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		putValue(SMALL_ICON, loadIcon("gui_icons/delete.png"));
		putValue(NAME, "Delete");
		putValue(SHORT_DESCRIPTION, "Delete");		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Tree tree = MainFrame.getInstance().getTree();
		Object selected = tree.getLastSelectedPathComponent();
		Entity entity = null;
		if (selected instanceof Entity) {
			entity = (Entity)selected;
		}
		int i = MainFrame.getInstance().getEntityView().getTable().getSelectedRow();
		if (i > -1) {
		Row row = MainFrame.getInstance().getEntityView().gettModel().getRows().get(i);
		String s = (String) row.getFields().get(MainFrame.getInstance().getEntityView().getEntity().getPrimaryKey().getName());
		String type = "char";
	
		Attribute at = (Attribute)(MainFrame.getInstance().getEntityView().getEntity().getPrimaryKey().getParent());
		if (at.getAttributeType() == AttributeType.FLOAT) type = "number";
		else if (at.getAttributeType() == AttributeType.DECIMAL) type = "number";
		else if (at.getAttributeType() == AttributeType.NUMERIC) type = "number";
		else if (at.getAttributeType() == AttributeType.INT) type = "number";
		else if (at.getAttributeType() == AttributeType.DATETIME) type = "date";
		else if (at.getAttributeType() == AttributeType.DATE) type = "date";
		
		MainFrame.getInstance().getAppCore().getDatabase().deleteRowInTable(entity, 
				MainFrame.getInstance().getEntityView().getEntity().getPrimaryKey().getName(), s, type);
		List<Row> rows = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(entity.getName());
		MainFrame.getInstance().getEntityView().gettModel().setRows(rows);
		}else {
			JOptionPane.showMessageDialog(null, "You must choose row first!");
		}
	}

}
