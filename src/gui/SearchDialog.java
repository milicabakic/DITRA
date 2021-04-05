package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import resource.DBNode;
import resource.data.Row;
import resource.implementation.Attribute;
import resource.implementation.Entity;

public class SearchDialog extends JDialog{
	
	private Entity entity;
	private JLabel label;
	private JButton searchButton;
	private JButton andButton;
	private JButton orButton;
	private JComboBox<Attribute> comboBox;
	private JComboBox<String> operations;
	private JLabel resultLabel;
	private StringBuilder sb;
	private JTextField field;
	private JLabel selectLabel;
	private List<JCheckBox> checkedColumns;
	private JLabel printLabel;
	private int countButtonPressed = 1;
	
	public SearchDialog(Entity entity) {
		this.entity = entity;
		
		setTitle("Search...");
		setSize(600,300);
		setLayout(new GridLayout(0,1));		
		setLocationRelativeTo(null);
		setBackground(Color.decode("#d2e6f1"));
			
		
		this.label = new JLabel("Search where");
		this.selectLabel = new JLabel("Choose columns ");		
		this.resultLabel = new JLabel();
		this.printLabel = new JLabel("You have searched for: ");
				
	    JPanel panel1 = new JPanel();
	    panel1.setBackground(Color.decode("#e6f9ff"));
        panel1.add(selectLabel);
        this.checkedColumns = new ArrayList<JCheckBox>();
 		this.comboBox = new JComboBox<Attribute>();
       	this.operations = new JComboBox<String>();
  
		for(DBNode column : entity.getChildren()) {
			comboBox.addItem((Attribute) column);
			
			JCheckBox checkBox = new JCheckBox(column.getName());
			checkBox.setName(column.getName());
			panel1.add(checkBox);
			checkedColumns.add(checkBox);
		}
       	
		
    	this.field = new JTextField();
    	field.setPreferredSize(new Dimension(100,30));  
    	field.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				Attribute a = (Attribute) comboBox.getSelectedItem();
				char c = e.getKeyChar();
				
				if(a.isAttributeNumber()) {
					if (c!='0' && c!='1' && c!='2' && c!='3' && c!='4' && 
							c!='5' && c!='6' && c!='7' && c!='8' && c!='9' ){
							JOptionPane.showMessageDialog(null, "You can only enter numbers!");
							field.setText("");
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});    	

    	JPanel panel2 = new JPanel();
    	panel2.setBackground(Color.decode("#e6f9ff"));
    	panel2.add(label);
    	panel2.add(comboBox);
    	panel2.add(operations);
    	panel2.add(field);

    	
		
		this.sb = new StringBuilder();
		sb.append("SELECT ");
       	
        this.searchButton = new JButton("End Search");
        searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(countButtonPressed == 1) {
					StringBuilder columns = new StringBuilder();
					
					int countSelected = 0;
					for(int i=0; i<checkedColumns.size(); i++) {
						if(checkedColumns.get(i).isSelected()) {
							if(countSelected > 0)
								columns.append(", ");
							
							columns.append(checkedColumns.get(i).getName());
							countSelected++;
						}
					}
					
					if(countSelected == 0) {
						JOptionPane.showMessageDialog(null, "You must choose columns first!");
						return;
					}	
					else if(countSelected == checkedColumns.size())
						sb.append("*");
					else
						sb.append(columns);
					
					sb.append(" FROM ");
					sb.append(entity.getName());
					sb.append(" WHERE ");					
				}
				
				if(field.getText().isEmpty()) {
					wrongQuery();
					return;
				}
					
				
				sb.append(" ");
				sb.append(((Attribute) comboBox.getSelectedItem()).getName());
				sb.append(" ");
				sb.append(operations.getSelectedItem());
				sb.append(" ");
				
				if(((Attribute) comboBox.getSelectedItem()).isAttributeNumber())
				     sb.append(field.getText());
				else if(((Attribute) comboBox.getSelectedItem()).isAttributeString()) {
					sb.append("'");					
					sb.append(field.getText());
					sb.append("'");
				}

				sb.append(" ");

				resultLabel.setText(sb.toString());
				field.setText("");
				
				countButtonPressed++;
				
				System.out.println(sb.toString());
				
    			List<Row> rows = MainFrame.getInstance().getAppCore().getDatabase().executeQuery(sb.toString(), entity);
    			if (rows.size() > 0) MainFrame.getInstance().getEntityView().gettModel().setRows(rows);
    			setVisible(false);
				
			}
		});
        
        this.andButton = new JButton("AND");
        andButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(countButtonPressed == 1) {
					StringBuilder columns = new StringBuilder();
					
					int countSelected = 0;
					for(int i=0; i<checkedColumns.size(); i++) {
						if(checkedColumns.get(i).isSelected()) {
							if(countSelected > 0)
								columns.append(", ");
							
							columns.append(checkedColumns.get(i).getName());
							countSelected++;
						}
					}
					
					if(countSelected == 0) {
						JOptionPane.showMessageDialog(null, "You must choose columns first!");
						return;
					}						
					else if(countSelected == checkedColumns.size() || countSelected==0)
						sb.append("*");
					else
						sb.append(columns);
					
					sb.append(" FROM ");
					sb.append(entity.getName());
					sb.append(" WHERE ");					
				}				
				
				sb.append(" ");
				sb.append(((Attribute) comboBox.getSelectedItem()).getName());
				sb.append(" ");
				sb.append(operations.getSelectedItem());
				sb.append(" ");
				
				if(((Attribute) comboBox.getSelectedItem()).isAttributeNumber())
				     sb.append(field.getText());
				else if(((Attribute) comboBox.getSelectedItem()).isAttributeString()) {
					sb.append("'");
					sb.append(field.getText());
					sb.append("'");
				}

				sb.append(" AND");
				
				countButtonPressed++;

				resultLabel.setText(sb.toString());
				field.setText("");

			}
		});
        
        this.orButton = new JButton("OR");
        orButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(countButtonPressed == 1) {
					StringBuilder columns = new StringBuilder();
					
					int countSelected = 0;
					for(int i=0; i<checkedColumns.size(); i++) {
						if(checkedColumns.get(i).isSelected()) {
							if(countSelected > 0)
								columns.append(", ");
							
							columns.append(checkedColumns.get(i).getName());
							countSelected++;
						}
					}
					
					
					if(countSelected == 0) {
						JOptionPane.showMessageDialog(null, "You must choose columns first!");
						return;
					}						
					else if(countSelected == checkedColumns.size() || countSelected==0)
						sb.append("*");
					else
						sb.append(columns);
					
					sb.append(" FROM ");
					sb.append(entity.getName());
					sb.append(" WHERE ");					
				}
				
				sb.append(" ");
				sb.append(((Attribute) comboBox.getSelectedItem()).getName());
				sb.append(" ");
				sb.append(operations.getSelectedItem());
				sb.append(" ");
				
				if(((Attribute) comboBox.getSelectedItem()).isAttributeNumber())
				     sb.append(field.getText());
				else if(((Attribute) comboBox.getSelectedItem()).isAttributeString()) {
					sb.append("'");
					sb.append(field.getText());
					sb.append("'");
				}

				sb.append(" OR");
				
				countButtonPressed++;
				
				resultLabel.setText(sb.toString());
				field.setText("");
			}
		});
        
        
        
        comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	        	Attribute selected = (Attribute) comboBox.getSelectedItem();
	        	operations.removeAllItems();
	        	
	        	if(selected.isAttributeString()) {
	        		operations.addItem("LIKE");
	        	}
	        	
	        	if(selected.isAttributeNumber()) {
	        		operations.addItem("=");
	        		operations.addItem("<");
	        		operations.addItem(">");        		
	        	}				
			}
		});
                   
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.decode("#e6f9ff"));
        
        searchButton.setBackground(Color.decode("#9fbfdf"));
        andButton.setBackground(Color.decode("#9fbfdf"));
        orButton.setBackground(Color.decode("#9fbfdf"));
        panel3.add(searchButton);
        panel3.add(andButton);
        panel3.add(orButton);
        
		JPanel panel4 = new JPanel();
		panel4.setBackground(Color.decode("#e6f9ff"));
		panel4.add(printLabel);
		panel4.add(resultLabel);


        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
  
		
		setVisible(true);
	}
	
	public void wrongInput() {
		JOptionPane.showMessageDialog(null, "You entered invalid characters!");		
	}
	
	public void wrongQuery() {
		JOptionPane.showMessageDialog(null, "You must finish your query first!");
	}
	
	
	
	

}
