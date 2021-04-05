package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resource.DBNode;
import resource.data.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public class ReportDialog extends JDialog{
	
	private Entity entity;
	ArrayList<JCheckBox> groupBy = new ArrayList<JCheckBox>();
	
	public ReportDialog(Entity entity) {
		this.entity = entity;
		setLayout(new GridLayout(0,1));
		setSize(600, 300);
		setLocationRelativeTo(null);
		setTitle("Report");
		
		ArrayList<String> c = new ArrayList<String>();
		c.add("COUNT");
		c.add("AVERAGE");
		JComboBox ca = new JComboBox(c.toArray());
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.decode("#e6f9ff"));
		JLabel lbl = new JLabel("CHOOSE: ");
		panel1.add(lbl);
		panel1.add(ca);
		
		//JPanel panel2 = new JPanel();
		//panel2.setBackground(Color.decode("#e6f9ff"));
		JComboBox combo = new JComboBox();
		JLabel calabel = new JLabel("OF");
		panel1.add(calabel);
		panel1.add(combo);
		
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.decode("#e6f9ff"));
		JLabel label = new JLabel("GROUP BY:");
		panel3.add(label);
		for (DBNode node : entity.getChildren()) {
			JCheckBox cbg = new JCheckBox(node.getName());
			cbg.setName(node.getName());
			groupBy.add(cbg);
			panel3.add(cbg);
		}
		
		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.decode("#e6f9ff"));
		JButton button = new JButton("REPORT");
		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		//button.setBackground(Color.GREEN);
		panel4.add(button);
		
		add(panel1);
		//add(panel2);
        add(panel3);
        add(panel4);
        
		ca.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				if (ca.getSelectedItem().equals("COUNT")) {
					//calabel.setText("COUNT OF: ");
					ArrayList<String> s = new ArrayList<String>();
				    combo.removeAllItems();
					for (DBNode node : entity.getChildren()) {
						s.add(node.getName());
						combo.addItem(node.getName());
					}
					
				}else {
					//calabel.setText("AVERAGE OF: ");
					ArrayList<String> s = new ArrayList<String>();
				    combo.removeAllItems();
					for (DBNode node : entity.getChildren()) {
						Attribute at = (Attribute)node;
						if (at.getAttributeType() == AttributeType.NUMERIC || at.getAttributeType() == AttributeType.FLOAT ||
						    at.getAttributeType() == AttributeType.INT || at.getAttributeType() == AttributeType.DECIMAL) {
						    s.add(node.getName());
						    combo.addItem(node.getName());
						}
					}
				}
				
			}
		});
		
	    button.setBackground(Color.decode("#9fbfdf"));

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				StringBuilder sb = new StringBuilder();
				sb.append("SELECT ");
				if (ca.getSelectedItem().equals("AVERAGE"))
				    sb.append("AVG" + "(");
				else sb.append("COUNT" + "(");
				sb.append(combo.getSelectedItem() + "), ");
				for (JCheckBox cb : groupBy)
					if (cb.isSelected()) sb.append(cb.getName() + ", ");
				if (sb.charAt(sb.length()-2) == ',') sb.deleteCharAt(sb.length()-2);
				
				boolean selected = false;
				for (JCheckBox cb : groupBy) {
					if (cb.isSelected()) {
						selected = true;
						break;
					}
				}
				
				if (selected) {
				   sb.append("FROM " + entity.getName() + " GROUP BY ");
				   for (JCheckBox cb : groupBy)
					   if (cb.isSelected()) sb.append(cb.getName() + ", ");
				   if (sb.charAt(sb.length()-2) == ',') sb.deleteCharAt(sb.length()-2);
				} else {
					sb.append("FROM " + entity.getName());
				}
    			System.out.println(sb.toString());	
    			
    			List<Row> rows = MainFrame.getInstance().getAppCore().getDatabase().executeQuery(sb.toString(), entity);
    			if (rows.size() > 0) MainFrame.getInstance().getEntityView().gettModel().setRows(rows);
    			setVisible(false);
			}
		});
		
	

		setVisible(true);
	}
	
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
