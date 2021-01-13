

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TwoButtonFlowEvent extends JFrame implements ActionListener {
	private JButton b1;
	private JButton b2;
	public TwoButtonFlowEvent(String s) {
		super(s);
		this.setSize(400,200);
		this.setLocation(80,80);
		this.getContentPane().setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		b1 = new JButton("red");
		b2 = new JButton("green");
		b1.addActionListener(this);
		b2.addActionListener(this);
		this.getContentPane().add(b1);
		this.getContentPane().add(b2);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1)
		this.getContentPane().setBackground(Color.red);
		else
		this.getContentPane().setBackground(Color.green);
		this.validate();
	}
	
	public static void main(String[] args) {
		TwoButtonFlowEvent frame = new TwoButtonFlowEvent("Two Buttons");
		frame.setVisible(true);
	}
}