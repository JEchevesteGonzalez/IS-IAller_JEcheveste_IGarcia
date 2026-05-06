package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendlyGUI extends JFrame {

	private JPanel contentPane;

	public FriendlyGUI(String usuario) {
		setTitle("Panel de Usuario Friendly: " + usuario);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnVentas = new JButton("Visualizar Ventas");
		btnVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame verVentas = new QuerySalesGUI(usuario); 
				verVentas.setVisible(true);
			}
		});
		btnVentas.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnVentas.setBounds(30, 40, 420, 100);
		contentPane.add(btnVentas);


		JButton btnSolicitudes = new JButton("Ver Mis Solicitudes");
		btnSolicitudes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame misSolicitudes = new VerSolicitudesGUI(usuario);
				misSolicitudes.setVisible(true);
			}
		});
		btnSolicitudes.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSolicitudes.setBounds(30, 170, 420, 100);
		contentPane.add(btnSolicitudes);
	}
}