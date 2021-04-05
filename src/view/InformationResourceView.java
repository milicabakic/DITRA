package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import resource.implementation.InformationResource;

public class InformationResourceView extends JPanel{
	
	private JTabbedPane tabPane;
	ArrayList<String> entityNames = new ArrayList<String>();
	ArrayList<EntityView> entityViews = new ArrayList<EntityView>();

	
	public InformationResourceView() {
		setLayout(new GridLayout());
		this.tabPane = new JTabbedPane();
		add(tabPane);
/*		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.BLACK);
		panel1.add(new JButton("Press me"));
		tabPane.addTab("tab1", panel1);
		JPanel panel2 = new JPanel();
		tabPane.addTab("tab2", panel2);
*/		
		validate();		
		setVisible(true);
	}
	
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}
	
	public void removeTabPane(EntityView entityView) {
		tabPane.remove(entityView);
	}
	
	public void addTab1(EntityView entityView) {
	    tabPane.addTab(entityView.getName(), entityView);        
	    validate();
	    
	    entityViews.clear();
	    entityViews.add(entityView);		

	}
	
	public void addTab2(EntityView entityView) {
	    tabPane.addTab(entityView.getName(), entityView);

	    validate();

	}
	
	
	public EntityView getTabWithName(String name) {
		int tabSz = tabPane.getTabCount();
		
		for(int i=0; i<tabSz; i++) {
			EntityView view = (EntityView) tabPane.getComponent(i);
			if(view.getName().equals(name))
				return view;
		}
		
		return null;
	}

	public EntityView getActiveTab() {
		return entityViews.get(0);
	}
	

}
