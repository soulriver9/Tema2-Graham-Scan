package convexHull;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

public class CVHGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public static int isLeftTurn(Point p1, Point p2, Point p3) {
		 return (p2.getX() - p1.getX())*(p3.getY() - p1.getY()) -
		 (p2.getY() - p1.getY())*(p3.getX() - p1.getX());
		 }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CVHGUI frame = new CVHGUI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public CVHGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		textField = new JTextField();
		textField.setBounds(99, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(99, 42, 86, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("X coords:");
		lblNewLabel.setBounds(31, 14, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Y coords:");
		lblNewLabel_1.setBounds(31, 45, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		
		Panel panel = new Panel();
		panel.setBounds(314, 10, 260, 303);
		contentPane.add(panel);
		panel.setBackground(Color.WHITE);
		
		
		JButton btnNewButton = new JButton("Genereaza Convex Hull");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Preiau datele si le convertesc la sir de intregi
				
				String x = textField.getText();
				String y = textField_1.getText();
				String[] bucx = x.split(" ");
				int i=0, xval[] = new int[100], yval[] = new int[100];
				for(i=0;i<bucx.length;i++)
					xval[i] = Integer.parseInt(bucx[i]);
				String[] bucy = y.split(" ");
				for(i=0;i<bucy.length;i++)
					yval[i] = Integer.parseInt(bucy[i]);
				
				//Creez un sir cu toate punctele
				Point[] puncte = new Point[bucx.length+2];
				for(i=0;i<bucx.length;i++)
				{
					puncte[i] = new Point(xval[i],yval[i]);
				}
				
				Stack<Point> stiva = new Stack<Point>();
				
				//Gases punctul cel mai de jos si il pun pe prima pozitie din sir
				int ymin = puncte[0].getY(), PozMin = 0;
				for(i=0;i<bucx.length;i++)
				{
					if(puncte[i].getY()<ymin)
					{
						ymin = puncte[i].getY();
						PozMin = i;
					}
				}
				Point aux = new Point(0,0);
				aux = puncte[0];
				puncte[0] = puncte[PozMin];
				puncte[PozMin] = aux;
				
				//Ordonez dupa unghiul relativ la cel mai de jos punct
				for(i=1;i<bucx.length-1;i++)
				{
					if(Math.atan2(puncte[i].getY()-puncte[0].getY(),puncte[i].getX()-puncte[0].getX()) >
					Math.atan2(puncte[i+1].getY()-puncte[0].getY(),puncte[i+1].getX()-puncte[0].getX()))
							{
								aux = puncte[i];
								puncte[i] = puncte[i+1];
								puncte[i+1] = aux;
							}
				}
				
				stiva.push(puncte[0]);
				stiva.push(puncte[1]);
				int n = 1;
				for(i=2;i<bucx.length;i++)
				{
					while(isLeftTurn(stiva.get(n-1),stiva.get(n),puncte[i])<0)
					{
						stiva.pop();
						n--;
					}
					stiva.push(puncte[i]);
					n++;
				}
				
				String raspuns =  new String();
				while(!stiva.isEmpty())
				{
					raspuns = raspuns + "("+stiva.lastElement().x+ ","+stiva.lastElement().y+") ";
					stiva.pop();
				}
				
				textField_2.setText(raspuns);
			}
			
		});
		btnNewButton.setBounds(10, 198, 175, 23);
		contentPane.add(btnNewButton);
		
		textField_2 = new JTextField();
		textField_2.setBounds(20, 237, 260, 76);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		
		
	}
}
