package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Sale;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class CompradorGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public CompradorGUI(String usuario) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 554);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Visualizar ofertas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame verVentas = new QuerySalesGUI(usuario);
				verVentas.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton.setBounds(0, 101, 637, 103);
		contentPane.add(btnNewButton);
		
		JButton btnVisualizarComprasAnteriores = new JButton("Visualizar compras anteriores");
		btnVisualizarComprasAnteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame verHist = new VisualizarComprasGUI(usuario);
				verHist.setVisible(true);
			}
		});
		btnVisualizarComprasAnteriores.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnVisualizarComprasAnteriores.setBounds(0, 0, 637, 103);
		contentPane.add(btnVisualizarComprasAnteriores);
		
		JButton btnNewButton_1 = new JButton("Agregar Fondos");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame fondos = new FondosGUI(usuario);
				fondos.setVisible(true);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnNewButton_1.setBounds(0, 202, 326, 86);
		contentPane.add(btnNewButton_1);
		
		JButton btnOfertasEnCurso = new JButton("Ofertas En Curso");
		btnOfertasEnCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame OfertasEnCurso = new VerOfertasEnCursoGUI(usuario);
				OfertasEnCurso.setVisible(true);
			}
		});
		btnOfertasEnCurso.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnOfertasEnCurso.setBounds(326, 202, 311, 86);
		contentPane.add(btnOfertasEnCurso);
		
		JButton btnRegistrarFriendly = new JButton("Registrar Friendly");
		btnRegistrarFriendly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame registrarFriendly = new RegistrarFriendlyGUI(usuario);
				registrarFriendly.setVisible(true);
			}
		});
		btnRegistrarFriendly.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegistrarFriendly.setBounds(0, 286, 637, 103);
		contentPane.add(btnRegistrarFriendly);
		
		JButton btnSolicitudesFriendly = new JButton("Ver solicitudes del Friendly");
		btnSolicitudesFriendly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame gestionar = new GestionarSolicitudesGUI(usuario);
				gestionar.setVisible(true);
			}
		});
		btnSolicitudesFriendly.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSolicitudesFriendly.setBounds(0, 389, 637, 103);
		contentPane.add(btnSolicitudesFriendly);
	}
}
