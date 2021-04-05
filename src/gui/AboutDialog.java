package gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutDialog extends JDialog{

	public AboutDialog(Frame parent,String title, boolean modal) {
		super(parent,title,modal);
		
		setSize(400,500);
		setLocationRelativeTo(parent);

        
	    JPanel panel = new JPanel(new GridLayout(2,2));
	    add(panel);
	    
	    JLabel luka = new JLabel();
	    URL imageURL = getClass().getResource("gui_icons/luka.jpg");
        Icon icon = null;
        if (imageURL != null)                       
         icon = new ImageIcon(imageURL);
	    luka.setIcon(icon);
	    luka.setSize(50,50);
	    panel.add(luka);
	
	    JLabel milica = new JLabel();
	    URL imageURL2 = getClass().getResource("gui_icons/milica.JPG");
        Icon icon2 = null;
        if (imageURL2 != null)                       
         icon2 = new ImageIcon(imageURL2);
	    milica.setIcon(icon2);
	    milica.setSize(50,50);
	    panel.add(milica);
	
	    JLabel lblLuka = new JLabel("Luka Mitrovic RN29/18");
		panel.add(lblLuka);
	    
	    
	    JLabel lblMili = new JLabel("Milica Bakic RN34/18");
        panel.add(lblMili);
	    
	}

/*	
	class ImageDialog extends JPanel {
		
		private Image image;
		
		public ImageDialog(String stringImg) {
			this(new ImageIcon(stringImg).getImage());
		}
		
		public ImageDialog(Image image) {
			this.image = image;
		}
		
		
	}
*/	
}
