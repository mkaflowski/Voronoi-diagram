package GUI;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import system.Circle;
import system.MyPoint;
import javax.swing.JButton;

public class MyFrame extends JFrame {

	private JPanel contentPane;
	public static JCheckBox chckbxOkregi;
	private JCheckBox chckbxWypenijKomorkiKolorami;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
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
	public MyFrame() {
		setTitle("Diagram Voroni - M.Kaflowski AGH 2014");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 837, 665);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		chckbxOkregi = new JCheckBox("Rysuj okr\u0119gi");
		chckbxOkregi.setSelected(true);

		final MyPanel panel = new MyPanel();
		panel.setBounds(10, 11, 800, 500);
		contentPane.add(panel);
		
		
		chckbxOkregi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfDrawCircles(chckbxOkregi.isSelected());
			}
		});
		chckbxOkregi.setBounds(10, 542, 155, 23);
		contentPane.add(chckbxOkregi);
		
		chckbxWypenijKomorkiKolorami = new JCheckBox("Wype\u0142nij komórki kolorami");
		chckbxWypenijKomorkiKolorami.setSelected(true);
		chckbxWypenijKomorkiKolorami.setEnabled(true);
		chckbxWypenijKomorkiKolorami.setBounds(10, 582, 204, 23);
		contentPane.add(chckbxWypenijKomorkiKolorami);
		
		JButton btnCzy = new JButton("CZY\u015A\u0106");
		btnCzy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.clear();
			}
		});
		btnCzy.setBounds(221, 582, 89, 23);
		contentPane.add(btnCzy);
		
		
		final JCheckBox chckbxKolorowieGranice = new JCheckBox("Kolorowie granice");
		chckbxKolorowieGranice.setBounds(221, 542, 155, 23);
		contentPane.add(chckbxKolorowieGranice);
		
		chckbxKolorowieGranice.setSelected(true);
		chckbxKolorowieGranice.setEnabled(true);
		
		final JCheckBox chckbxZdjcie = new JCheckBox("Zdj\u0119cie");
		chckbxZdjcie.setSelected(true);
		chckbxZdjcie.setEnabled(true);
		chckbxZdjcie.setBounds(411, 542, 155, 23);
		chckbxZdjcie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.setIfPhoto(chckbxZdjcie.isSelected());
			}
		});
		
		contentPane.add(chckbxZdjcie);
		
		
		chckbxKolorowieGranice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfColorLines(chckbxKolorowieGranice.isSelected());
			}
		});
		
		
		
		
		chckbxWypenijKomorkiKolorami.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setIfFillWithColors(chckbxWypenijKomorkiKolorami.isSelected());
			}
		});

		// dodanie testowych punktów:
		panel.addPoint(new MyPoint(100, 100));
		panel.addPoint(new MyPoint(150, 200));
		panel.addPoint(new MyPoint(300, 60));
		

		// dodanie testowego okr¹gu:
		panel.addCircle(new Circle(new MyPoint(100, 100), new MyPoint(150, 200),
				new MyPoint(300, 60)));
		
		panel.clear();
	}
}
