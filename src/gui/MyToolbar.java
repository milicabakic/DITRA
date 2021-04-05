package gui;

import javax.swing.JToolBar;

public class MyToolbar extends JToolBar{

	public MyToolbar() {
		
		super(JToolBar.HORIZONTAL);
		
		add(MainFrame.getInstance().getActionManager().getAdd());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getDelete());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getUpdate());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getSort());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getRelate());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getCountAaverage());
		addSeparator();
		add(MainFrame.getInstance().getActionManager().getSearch());
		
		setFloatable(false);
	}
}
