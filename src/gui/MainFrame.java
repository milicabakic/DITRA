package gui;

import app.AppCore;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import resource.implementation.InformationResourceModel;
import view.EntityView;
import view.InformationResourceView;


import javax.swing.*;
import javax.swing.table.TableModel;

import actions.ActionManager;

import java.awt.*;



public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    
    private Tree tree;
	private InformationResourceModel treeModel;
	private InformationResourceView  ir1;
	private InformationResourceView  ir2;
	private EntityView entityView;
	private ActionManager actionManager;

    private MainFrame() {

    }
    
    public AppCore getAppCore() {
		return appCore;
	}

    public static MainFrame getInstance(){
        if (instance==null){
            instance=new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {
    	this.actionManager = new ActionManager();
        
    	setSize(1000,550);
    	//setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setTitle("DiTRa");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
						
		JPanel top = new JPanel(new BorderLayout());
		MyMenu menuBar = new MyMenu(this);
		top.add(menuBar, BorderLayout.NORTH);
			
		tree=new Tree();
		treeModel = new InformationResourceModel();
		tree.setModel(treeModel);
		
		ir1 = new  InformationResourceView();
		ir1.setPreferredSize(new Dimension(300,280));
		ir2 = new InformationResourceView();
		
		JSplitPane twins = new JSplitPane(JSplitPane.VERTICAL_SPLIT,ir1, ir2);
				
		JScrollPane scroll=new JScrollPane(tree);
		scroll.setMinimumSize(new Dimension(170,150));
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll, twins);
		
		add(top,BorderLayout.NORTH);
		add(split,BorderLayout.CENTER);
		
		validate();
	
		setVisible(true);
		
		new LogIN();

    }

    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        //this.jTable.setModel(appCore.getTableModel());
    }


    @Override
    public void update(Notification notification) {

        if (notification.getCode() == NotificationCode.RESOURCE_LOADED){
        	SwingUtilities.updateComponentTreeUI(this.tree);
        	this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        	
        }else if (notification.getCode() == NotificationCode.RESOURCE_NOT_LOADED) {
        	JOptionPane.showMessageDialog(new JFrame(), "Cannot connect!", "Connect to Server", JOptionPane.ERROR_MESSAGE);
        	this.setVisible(false);
        	
        }else{
            jTable.setModel((TableModel) notification.getData());
        }

    }
    
    
    public Tree getTree() {
		return tree;
	}
    
    public ActionManager getActionManager() {
		return actionManager;
	}
    
    
    public InformationResourceView getIr1() {
		return ir1;
	}
    
    public InformationResourceView getIr2() {
		return ir2;
	}
    
    public JTable getjTable() {
		return jTable;
	}
    
    public void setEntityView(EntityView entityView) {
		this.entityView = entityView;
	}
    public EntityView getEntityView() {
		return entityView;
	}
    
}
