package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Comprador;

import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class BorrarCuentaGUI extends JFrame {
	private JTextField usuario;
	private JLabel lblUsuario;
	private JLabel lblContrasena;
	private JLabel textoErrores;
	private JPasswordField Contrasena;
	
	public BorrarCuentaGUI() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Borrar cuenta");
		lblNewLabel.setBounds(171, 11, 73, 39);
		getContentPane().add(lblNewLabel);
		
		usuario = new JTextField();
		usuario.setBounds(157, 61, 86, 20);
		getContentPane().add(usuario);
		usuario.setColumns(10);
		
		lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(49, 64, 54, 14);
		getContentPane().add(lblUsuario);
		
		lblContrasena = new JLabel("Contrasena:");
		lblContrasena.setBounds(49, 133, 73, 14);
		getContentPane().add(lblContrasena);
		
		JButton btnBorrarCuenta = new JButton("Borrar Cuenta");
		btnBorrarCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuarioIntro = usuario.getText();
				String contrIntro = new String(Contrasena.getPassword());
				
				if(usuarioIntro.isEmpty() || contrIntro.isEmpty()) {
					textoErrores.setText("Rellena todos los campos.");
					return;
				}
				
				BLFacade facade = MainGUI.getBusinessLogic();
				
				Comprador com = facade.buscarPorUser(usuarioIntro);
				
				if(com != null && contrIntro.equals(com.getContrasena())) {
					int confirmacion = JOptionPane.showConfirmDialog(null, 
							"¿Estás seguro de que deseas eliminar la cuenta de forma permanente?", 
							"Confirmar Borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (confirmacion == JOptionPane.YES_OPTION) {
						facade.eliminarUsuario(usuarioIntro);
						dispose();	
					}
				} else {
					textoErrores.setText("Usuario o contraseña incorrectos.");
				}
			}
				
			
		});
		btnBorrarCuenta.setBounds(144, 183, 117, 23);
		getContentPane().add(btnBorrarCuenta);
		
		JButton btnAtrs = new JButton("Atrás");
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAtrs.setBounds(10, 19, 89, 23);
		getContentPane().add(btnAtrs);
		
		textoErrores = new JLabel("");
		textoErrores.setForeground(Color.RED);
		textoErrores.setBounds(49, 187, 46, 14);
		getContentPane().add(textoErrores);
		
		Contrasena = new JPasswordField();
		Contrasena.setBounds(157, 130, 87, 20);
		getContentPane().add(Contrasena);
		
		
		
	}
}
