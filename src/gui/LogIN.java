package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.AppCore;

public class LogIN extends JDialog {
	
	private static JTextField txt1;
	private static JTextField txt2;
	private static JTextField txt3;
	private static JPasswordField txt4;
	
	public LogIN() {
		
		setLayout(new GridLayout(0,1));
		setSize(400, 250);
		setLocationRelativeTo(null);
		setTitle("Connect to Server");
				
		JLabel label1 = new JLabel("MSSQL_IP");
		JLabel label2 = new JLabel("MSSQL_DATABASE");
		JLabel label3 = new JLabel("MSSQL_USERNAME");
		JLabel label4 = new JLabel("MSSQL_PASSWORD");
		
		label1.setPreferredSize(new Dimension(150,20));
		label2.setPreferredSize(new Dimension(150,20));
		label3.setPreferredSize(new Dimension(150,20));
		label4.setPreferredSize(new Dimension(150,20));
	
		txt1 = new JTextField();
		txt2 = new JTextField();
		txt3 = new JTextField();
		txt4 = new JPasswordField();
		
		txt1.setPreferredSize(new Dimension(100,20));
		txt2.setPreferredSize(new Dimension(100,20));
		txt3.setPreferredSize(new Dimension(100,20));
		txt4.setPreferredSize(new Dimension(100,20));
		
		txt1.setText("147.91.175.155");
		txt2.setText("tim_11_bp2020");
		txt3.setText("tim_11_bp2020");
		txt4.setText("hheTaM4z");
		
		JButton login = new JButton("Connect");
		login.setBackground(Color.decode("#e6f5ff"));
		login.setCursor(new Cursor(Cursor.HAND_CURSOR));
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
		        MainFrame.getInstance().setCursor(new Cursor(Cursor.WAIT_CURSOR));
				AppCore appCore = new AppCore();
		        MainFrame.getInstance().setAppCore(appCore);
		        MainFrame.getInstance().getAppCore().loadResource();
			}
		});
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		
		panel1.add(label1);
		panel1.add(txt1);
		panel2.add(label2);
		panel2.add(txt2);
		panel3.add(label3);
		panel3.add(txt3);
		panel4.add(label4);
		panel4.add(txt4);
		
		panel5.add(login);
		
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		
		panel1.setBackground(Color.decode("#e6f5ff"));
		panel2.setBackground(Color.decode("#ccebff"));
		panel3.setBackground(Color.decode("#b3e0ff"));
		panel4.setBackground(Color.decode("#99d6ff"));
		panel5.setBackground(Color.decode("#80ccff"));		
		
		setVisible(true);
	}
	
	public static String ip() {
		return txt1.getText();
	}
	public static String database() {
		return txt2.getText();
	}
	public static String username() {
		return txt3.getText();
	}
	public static String password() {
		return txt4.getText();
	}

}


