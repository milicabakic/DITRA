package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

public class AddDialog extends JDialog {
	
	JLabel label;
	JButton button;
	Entity entity;
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	ArrayList<String> fields = new ArrayList<String>();
	
	public AddDialog(Entity entity) {		
		setLayout(new FlowLayout());
		setSize(900,130);
		setTitle("Add");
		setLocationRelativeTo(null);
		setBackground(Color.decode("#e6f9ff"));
        this.entity = entity;
        for (DBNode a : entity.getChildren()) {
		Attribute at = (Attribute)a;
		boolean nullFlag = true;
		for (DBNode node : at.getChildren()) {
			AttributeConstraint ac = (AttributeConstraint) node;
			if (ac.getConstraintType() == ConstraintType.NOT_NULL) {
				nullFlag = false;
				break;
			}
		}
		StringBuilder sb = new StringBuilder();
		if (at.getAttributeType() == AttributeType.DATETIME || at.getAttributeType() == AttributeType.DATE)
			sb.append(" " + at.getName() + " (dd-MON-yyyy)");
		else sb.append(" " + at.getName());
		if (nullFlag) sb.append(" (optional)");
		this.label = new JLabel(sb.toString());
		label.setSize(new Dimension(70,20));
		
		JTextField field = new JTextField();
	    field.setPreferredSize(new Dimension(70,20));
	    textFields.add(field);
		add(label);
	    add(field);
        
	    
        }
	    
	    this.button = new JButton("Add");
	    this.button.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    this.button.setSize(new Dimension(50,50));
		this.button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (JTextField tf : textFields)
					fields.add(tf.getText());
				MainFrame.getInstance().getAppCore().getDatabase().addRowToTable(entity,fields);
				List<Row> rows = MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(entity.getName());
				MainFrame.getInstance().getEntityView().gettModel().setRows(rows);
				setVisible(false);
			}
			
		});	    
		button.setBackground(Color.decode("#9fbfdf"));
	    add(button);
	    setVisible(true);

	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public ArrayList<JTextField> getTextFields() {
		return textFields;
	}

	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}
	

}
