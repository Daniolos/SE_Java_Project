package kassensystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Test extends JFrame {
	private JTextField cTextField;
	private JTextField fTextField;
	private JButton cfButton;
	private JButton fcButton;
	public Test(String s) {
	super(s);
	this.setSize(400, 200);
	this.setLocation(80, 80);
	this.getContentPane().setBackground(Color.white);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setLayout(new GridLayout(3, 2, 10, 10));
	JLabel cLabel = new JLabel("Temperature [C]");
	cTextField = new JTextField("0");
	JLabel fLabel = new JLabel("Temperature [F]");
	fTextField = new JTextField("0");
	cfButton = new JButton("Calculate [C] to [F]");
	fcButton = new JButton("Calculate [F] to [C]");
	this.getContentPane().add(cLabel);
	this.getContentPane().add(cTextField);
	this.getContentPane().add(fLabel);
	this.getContentPane().add(fTextField);
	this.getContentPane().add(cfButton);
	this.getContentPane().add(fcButton);
	cfButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int celsius = Integer.parseInt(cTextField.getText());
			int fahrenheit = (int) (9 / 5.0 * celsius + 32);
			fTextField.setText(fahrenheit + "");
			}
			});
			fcButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int fahrenheit = Integer.parseInt(fTextField.getText());
			int celsius = (int) (5 / 9.0 * (fahrenheit - 32));
			cTextField.setText(celsius + "");
			}
		});
	}
	
	
	public static void main(String[] args) {
	Test frame = new Test("Temperature calculation");
	frame.setVisible(true);
	}
}