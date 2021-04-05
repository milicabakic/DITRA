package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import resource.DBNode;
import resource.implementation.Entity;

public class FilterSortDialog extends JDialog {
	
	JButton button;
	Entity entity;
	ArrayList<String> selected = new ArrayList<String>();
	ArrayList<String> ascending = new ArrayList<String>();
	ArrayList<String> descending = new ArrayList<String>();
	
	ArrayList<JCheckBox> select = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> asc = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> desc = new ArrayList<JCheckBox>();
	
	public FilterSortDialog(Entity entity) {
		
		setLayout(new GridLayout(0,1));
		setSize(600,300);
		setLocationRelativeTo(null);
		setTitle("Filter&Sort");
		JPanel panel = new JPanel();
        this.entity = entity;
       // panel.setBackground(Color.decode("#fff5cc"));
        JLabel label = new JLabel("Select attribute:");
        panel.add(label);
        for (DBNode a : entity.getChildren()) {
        
        	JCheckBox cb1 = new JCheckBox(a.getName());
        	cb1.setName(a.getName());
        	panel.add(cb1);
        	select.add(cb1);
        }
        
		JPanel pnl = new JPanel();
		pnl.setBackground(Color.decode("#e6f9ff"));
		JLabel lbl = new JLabel("Sort descending: ");
		pnl.add(lbl);
		for (DBNode a : entity.getChildren()) {
	        
        	JCheckBox cb1 = new JCheckBox(a.getName());
        	cb1.setName(a.getName());
        	pnl.add(cb1);
            desc.add(cb1); 
        }
		
		JPanel pnl1 = new JPanel();
		JLabel lbl1 = new JLabel("Sort ascending: ");
		//pnl1.setBackground(Color.decode("#e6eeff"));
		pnl1.add(lbl1);
		for (DBNode a : entity.getChildren()) {
	        
        	JCheckBox cb1 = new JCheckBox(a.getName());
        	cb1.setName(a.getName());
        	pnl1.add(cb1);
            asc.add(cb1);
        }
		
		
	    this.button = new JButton("APPLY");
	    button.setBackground(Color.decode("#9fbfdf"));
	    this.button.setSize(new Dimension(80,50));
		this.button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for (JCheckBox cb : select)
					if (cb.isSelected()) selected.add(cb.getName());
				for (JCheckBox cb : desc)
					if (cb.isSelected()) descending.add(cb.getName());
				for (JCheckBox cb : asc)
					if (cb.isSelected()) ascending.add(cb.getName());
				
				MainFrame.getInstance().getEntityView().gettModel().setRows(
				MainFrame.getInstance().getAppCore().getDatabase().sortDataInTable(entity, selected, descending,ascending));
				setVisible(false);
			}
		});	 
		JPanel btnpanel = new JPanel();
		btnpanel.setBackground(Color.decode("#e6f9ff"));
		btnpanel.add(button);
	    add(panel);
	    add(pnl);
	    add(pnl1);
	    add(btnpanel);
	    setVisible(true);

		
	}
	
	public ArrayList<String> getAscending() {
		return ascending;
	}
	public ArrayList<String> getDescending() {
		return descending;
	}

}
