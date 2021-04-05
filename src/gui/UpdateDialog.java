package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;

public class UpdateDialog extends JDialog{
	
	JLabel label;
	JButton button;
	Entity entity;
	String primaryName;
	String primaryValue;
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	ArrayList<String> fields = new ArrayList<String>();
	
	public UpdateDialog(Entity entity, String primaryName, String primaryValue) {
		this.entity = entity;
		this.primaryName=primaryName;
		this.primaryValue=primaryValue;
		setLayout(new GridLayout(0,1));
		setSize(400,360);
		setLocationRelativeTo(null);
		setTitle("Update");
		for (DBNode a : entity.getChildren()) {
			Attribute at = (Attribute)a;
			/*boolean primary = false;
			for (DBNode node : at.getChildren()) {
				AttributeConstraint ac = (AttributeConstraint) node;
				if (ac.getConstraintType() == ConstraintType.PRIMARY_KEY) {
					primary = true;
					break;
				}
			}*/
			//if (!primary) {
			StringBuilder sb = new StringBuilder();
			if (at.getAttributeType() == AttributeType.DATETIME || at.getAttributeType() == AttributeType.DATE)
				sb.append(" " + at.getName() + " (dd-MON-yyyy)");
			else sb.append(" " + at.getName());
			this.label = new JLabel(sb.toString());
			label.setPreferredSize(new Dimension(100,20));
			JPanel panel = new JPanel();
			panel.setBackground(Color.decode("#e6f9ff"));
			panel.add(label);
			
			JTextField field = new JTextField();
		    field.setPreferredSize(new Dimension(90,20));;
		    panel.add(field);
		    textFields.add(field);
		    add(panel);
		    
	        //}
		}
		
		this.button = new JButton("Update");
	    this.button.setSize(new Dimension(50,50));
		this.button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (JTextField tf : textFields)
					fields.add(tf.getText());
			    MainFrame.getInstance().getAppCore().getDatabase().updateRowInTable(entity, fields, primaryName,primaryValue);
				List<Row> rows = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(entity.getName());
				MainFrame.getInstance().getEntityView().gettModel().setRows(rows);
				setVisible(false);
			}
			
		});	    
	    JPanel pnl = new JPanel();
	    pnl.setBackground(Color.decode("#e6f9ff"));
	    button.setBackground(Color.decode("#9fbfdf"));
	    pnl.add(button);
	    add(pnl);
	    setVisible(true);
		
	}
	
	public ArrayList<JTextField> getTextFields() {
		return textFields;
	}

}
