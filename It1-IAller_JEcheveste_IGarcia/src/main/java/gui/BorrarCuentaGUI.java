package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Friendly;
import domain.Seller;
import domain.Usuario;

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
		BLFacade facade = MainGUI.getBusinessLogic();
		
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
				}
				else {
					Usuario u = facade.buscarPorUser(usuarioIntro);
					if(u != null) {
						if(u instanceof Friendly) {
							textoErrores.setText("Tu usuario es dependiente, lo debe eliminar tu supervisor");
						}
						else {
							if(contrIntro.equals(u.getContrasena())) {
								boolean bien = true;
								Comprador c = (Comprador) u;
								if(c.getOfertasEnCurso()!= null) {
									textoErrores.setText("Si tienes ofertas en curso, no puedes eliminar la cuenta.");
									bien = false;
								}
								if(u instanceof Seller) {
									Seller seller = (Seller) u;
									if(seller.getSales() != null) {
										textoErrores.setText("Si tienes ventas o subastas en curso, no puedes eliminar la cuenta.");
										bien = false;
									}
								}
								if(bien) {
									int confirmacion = JOptionPane.showConfirmDialog(null, 
											"¿Estas seguro de que deseas eliminar la cuenta de forma permanente?", 
											"Confirmar Borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
									
									if (confirmacion == JOptionPane.YES_OPTION) {
										facade.eliminarUsuario(usuarioIntro);
										dispose();	
									}
								}
							}
							else {
								textoErrores.setText("Usuario o contrasena incorrectos.");
							}
						}
					}
					else {
						textoErrores.setText("Este usuario no existe.");
					}
				}
			}
				
			
		});
		btnBorrarCuenta.setBounds(144, 183, 117, 23);
		getContentPane().add(btnBorrarCuenta);
		
		JButton btnAtrs = new JButton("Atras");
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
