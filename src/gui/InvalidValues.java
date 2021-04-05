package gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InvalidValues extends JDialog {
	
	JButton btn;
	
	public InvalidValues() {
		
		setLayout(new GridLayout(0,1));
		setSize(300,150);
		setLocationRelativeTo(null);
		setTitle("InvalidValues");
		this.btn = new JButton("OK");
		btn.setSize(new Dimension(50,50));
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Operation is not valid!");
		panel.add(label);
		JPanel pnl = new JPanel();
		pnl.add(btn);
        this.btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});	
		add(panel);
		add(pnl);
		
		
		setVisible(true);
		
	}

}
