package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Seller;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usuario;
	private JPasswordField contrasena;
	private JPasswordField repContrasena;
	private JTextField correo;
	private JTextField nombre;

	/**
	 * Create the frame.
	 */
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsuario.setBounds(12, 39, 98, 34);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasea.setBounds(12, 71, 98, 37);
		contentPane.add(lblContrasea);
		
		JLabel lblCorreo = new JLabel("Correo:");
		lblCorreo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCorreo.setBounds(12, 140, 74, 34);
		contentPane.add(lblCorreo);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombre.setBounds(12, 175, 98, 28);
		contentPane.add(lblNombre);
		
		JLabel lblRepetirContrasena = new JLabel("Repetir contraseña:");
		lblRepetirContrasena.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRepetirContrasena.setBounds(12, 103, 157, 37);
		contentPane.add(lblRepetirContrasena);
		
		usuario = new JTextField();
		usuario.setBounds(174, 47, 246, 22);
		contentPane.add(usuario);
		usuario.setColumns(10);
		usuario.setEnabled(true);
		
		contrasena = new JPasswordField();
		contrasena.setBounds(174, 80, 246, 22);
		contentPane.add(contrasena);
		contrasena.setEnabled(true);
		
		repContrasena = new JPasswordField();
		repContrasena.setVisible(false);
		repContrasena.setBounds(174, 112, 246, 22);
		contentPane.add(repContrasena);
		
		correo = new JTextField();
		correo.setVisible(false);
		correo.setColumns(10);
		correo.setBounds(174, 148, 246, 22);
		contentPane.add(correo);
		
		nombre = new JTextField();
		nombre.setVisible(false);
		nombre.setColumns(10);
		nombre.setBounds(174, 180, 246, 22);
		contentPane.add(nombre);
		
		JLabel lblNewLabel = new JLabel("Register");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(179, 0, 116, 34);
		contentPane.add(lblNewLabel);
		
		JLabel textoErrores = new JLabel("");
		textoErrores.setForeground(Color.RED);
		textoErrores.setBounds(12, 216, 280, 24);
		contentPane.add(textoErrores);
		
		JButton registrarse = new JButton("Registrarse");
		registrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String usuarioIntro = usuario.getText();
				String contrIntro = new String(contrasena.getPassword());
				BLFacade facade = MainGUI.getBusinessLogic();
				Comprador login = facade.buscarPorUser(usuarioIntro);
				if(usuarioIntro.isEmpty()) {
                    textoErrores.setText("El usuario no puede estar vacio.");
				}
				else if(contrIntro.isEmpty()) {
                    textoErrores.setText("La contrasena no puede estar vacia.");
				}
				else {
					if (login != null) {
			            if (contrIntro.equals(login.getContrasena())) {
			                
			                // Usamos instanceof en lugar de getClass() para evitar problemas con la BD
			                if (login instanceof Seller) {
			                    textoErrores.setText("Este usuario ya está registrado como vendedor.");
			                    usuario.setEnabled(true);
			                    contrasena.setEnabled(true);
			                } 
			                // Si no es Seller, obligatoriamente es Comprador. Procedemos a "ascenderlo".
			                else {
			                    if (!correo.isVisible() && !nombre.isVisible()) {
			                        // Mostrar los campos extra para que se haga vendedor
			                        correo.setVisible(true);
			                        nombre.setVisible(true);
			                        usuario.setEnabled(false);
			                        contrasena.setEnabled(false);
			                        textoErrores.setText("Introduce correo y nombre para ascender a vendedor.");
			                    } else {
			                        // Guardar el nuevo vendedor (tu DataAccess ya borra el comprador antiguo)
			                        if (!correo.getText().isEmpty() && !nombre.getText().isEmpty()) {
			                            facade.addSeller(usuarioIntro, correo.getText(), nombre.getText());
			                            textoErrores.setText("¡Ascendido a vendedor con éxito!");
			                            
			                            // Reseteamos la vista por si quiere hacer algo más
			                            correo.setVisible(false);
			                            nombre.setVisible(false);
			                            usuario.setEnabled(true);
			                            contrasena.setEnabled(true);
			                        } else {
			                            textoErrores.setText("Introduce un correo y nombre válidos.");
			                        }
			                    }
			                }
			            } else {
			                textoErrores.setText("La contraseña es incorrecta.");
			                usuario.setEnabled(true);
			                contrasena.setEnabled(true);
			            }
			        } 
			        // CASO 3: EL USUARIO NO EXISTE (Registro nuevo)
			        else {
			            if (!repContrasena.isVisible()) {
			                textoErrores.setText("Usuario nuevo. Repite la contraseña para registrarte.");
			                repContrasena.setVisible(true);
			                usuario.setEnabled(false);
			                contrasena.setEnabled(false);
			            } else {
			                if (contrIntro.equals(new String(repContrasena.getPassword()))) {
			                    facade.addComprador(usuarioIntro, contrIntro);
			                    textoErrores.setText("¡Comprador registrado con éxito!");
			                    
			                    // Reseteamos la vista
			                    repContrasena.setVisible(false);
			                    usuario.setEnabled(true);
			                    contrasena.setEnabled(true);
			                } else {
			                    textoErrores.setText("Las contraseñas no coinciden.");
			                }
			            }
			        }
			    }
			}
		});
		registrarse.setBounds(304, 215, 116, 25);
		contentPane.add(registrarse);
	}

}
