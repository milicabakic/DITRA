package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gui.MainFrame;
import gui.MyToolbar;
import gui.table.TableModel;
import resource.implementation.Entity;

public class EntityView extends JPanel{
	
	private Entity entity;
	private String name;
	private MyToolbar toolbar;
	
	private JTable table;
	private TableModel tModel;

	
	public EntityView(Entity entity) {
		setLayout(new BorderLayout());
		
/*		this.toolbar = new MyToolbar();
		add(toolbar, BorderLayout.NORTH);
*/		
        this.table = new JTable();
        this.tModel = new TableModel();
        tModel.setRows(MainFrame.getInstance().getAppCore().getDatabase().readDataFromTable(entity.getName()));
        this.table.setModel(tModel);
		//add(this.table, BorderLayout.CENTER);
        add(new JScrollPane(table), BorderLayout.CENTER);

		this.entity = entity;
		this.name = entity.getName();
		
		setVisible(true);		
	}
	
	public void addToolbar() {
    	this.toolbar = new MyToolbar();
		add(toolbar, BorderLayout.NORTH);	
	}

	public String getName() {
		return name;
	}
	
	public TableModel gettModel() {
		return tModel;
	}
	public JTable getTable() {
		return table;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	@Override
	public String toString() {
		return "Entity View " + entity;
	}

	
}
