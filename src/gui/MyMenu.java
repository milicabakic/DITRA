package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MyMenu extends JMenuBar{
	
	   MyMenu(MainFrame parent){
		   
			JMenu help=new JMenu("Help");
			
			JMenuItem about = new JMenuItem("About");
			
			about.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					AboutDialog dialog = new AboutDialog(parent,"About team",true);
					dialog.setVisible(true);
					
				}
			});
			
		
			help.add(about);
			add(help);
	   }


}
