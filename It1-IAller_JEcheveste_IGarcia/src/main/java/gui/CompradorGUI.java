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
	
	//Prueba de cambio y reset
	
	public CompradorGUI(String usuario) {
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		btnNewButton.setBounds(0, 123, 434, 103);
		contentPane.add(btnNewButton);
		
		JButton btnVisualizarComprasAnteriores = new JButton("Visualizar compras anteriores");
		btnVisualizarComprasAnteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame verHist = new VisualizarComprasGUI(usuario);
				verHist.setVisible(true);
			}
		});
		btnVisualizarComprasAnteriores.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnVisualizarComprasAnteriores.setBounds(0, 0, 434, 124);
		contentPane.add(btnVisualizarComprasAnteriores);
		
		JButton btnNewButton_1 = new JButton("Añadir Fondos: 100 €");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				facade.anadirFondos(usuario, 100);
				//JFrame verSaldo = new VerSaldoGUI (usuario);
				//verSaldo.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(0, 224, 426, 36);
		contentPane.add(btnNewButton_1);
	}
}
