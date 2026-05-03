package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Friendly;
import domain.Seller;
import domain.Usuario;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
    private String sellerMail;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JTextField usuario;
	private JPasswordField contrasena;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel textoErrores= new JLabel("");
	
    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}
	
	public JLabel getTextoErrores() {
		return textoErrores;
	}
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		
		this.setSize(495, 290);
		
		jContentPane = new JPanel();
		
		
		setContentPane(jContentPane);
		jContentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblLogin.setBounds(204, 13, 84, 51);
		jContentPane.add(lblLogin);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsuario.setBounds(36, 66, 89, 51);
		jContentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrasena:");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasea.setBounds(36, 110, 136, 51);
		jContentPane.add(lblContrasea);
		
		usuario = new JTextField();
		usuario.setText("");
		usuario.setBounds(157, 82, 176, 22);
		jContentPane.add(usuario);
		usuario.setColumns(10);
		
		contrasena = new JPasswordField();
		contrasena.setText("");
		contrasena.setBounds(157, 126, 176, 22);
		jContentPane.add(contrasena);
		
		JButton register = new JButton("Register");
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Pasar a la pantalla register
				JFrame a = new RegisterGUI();
				a.setVisible(true);
			}
		});
		register.setBounds(368, 13, 97, 25);
		jContentPane.add(register);
		

		JRadioButton rdbtnFriendly = new JRadioButton("Friendly");
		buttonGroup.add(rdbtnFriendly);
		rdbtnFriendly.setBounds(46, 156, 127, 25);
		jContentPane.add(rdbtnFriendly);
		
		JRadioButton rdbtnComprador = new JRadioButton("Comprador");
		buttonGroup.add(rdbtnComprador);
		rdbtnComprador.setBounds(185, 156, 127, 25);
		jContentPane.add(rdbtnComprador);
		
		JRadioButton rdbtnVendedor = new JRadioButton("Vendedor");
		buttonGroup.add(rdbtnVendedor);
		rdbtnVendedor.setBounds(331, 156, 127, 25);
		jContentPane.add(rdbtnVendedor);
		
		textoErrores = new JLabel("");
		textoErrores.setForeground(Color.RED);
		textoErrores.setBounds(36, 177, 163, 30);
		jContentPane.add(textoErrores);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": "+sellerMail);
		
		JButton entrar = new JButton("Entrar");
		entrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String usuarioIntro = usuario.getText();
				String contrIntro = new String(contrasena.getPassword());
				Usuario login=appFacadeInterface.buscarPorUser(usuarioIntro);
				
				if( login != null && usuarioIntro.equals(login.getUsuario())) {			
					if(contrIntro.equals(login.getContrasena())){
						
						if(rdbtnComprador.isSelected()) {
							if(login instanceof Comprador) {
								JFrame comprador = new CompradorGUI(usuarioIntro);
								comprador.setVisible(true);
								textoErrores.setText("");
							}
							else {
								textoErrores.setText("Este usuario no esta registrado como comprador");
							}
						}
						else if (rdbtnVendedor.isSelected()){
							if(login instanceof Seller) {
								//Pasar a la otra pantalla
								JFrame vendedor = new SellerGUI(usuarioIntro);
								vendedor.setVisible(true);
								textoErrores.setText("");
							}
							else {
								textoErrores.setText("Este usuario no esta registrado como vendedor");
							}
						}
						else if (rdbtnFriendly.isSelected()){
							if(login instanceof Friendly) {
								//Pasar a la otra pantalla
								JFrame friendly = new SellerGUI(usuarioIntro);
								friendly.setVisible(true);
								textoErrores.setText("");
							}
							else {
								textoErrores.setText("Este usuario no esta registrado como comprador friendly");
							}
						}
						else {
							textoErrores.setText("No se ha seleccionado ningun modo de login");
						}
					}
					else {
						textoErrores.setText("La contrasena es incorrecta");
					}
				}
				else {
					textoErrores.setText("El usuario no existe");
				}
			}
			
		});
		entrar.setBounds(204, 182, 97, 25);
		jContentPane.add(entrar);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});

		JButton btnBorrarCuenta = new JButton("Borrar Cuenta");
		btnBorrarCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame borrarGUI = new BorrarCuentaGUI();
				borrarGUI.setVisible(true);
			}
		});
		btnBorrarCuenta.setBounds(368, 50, 115, 25); 
		jContentPane.add(btnBorrarCuenta);
	}
} // @jve:decl-index=0:visual-constraint="0,0"

