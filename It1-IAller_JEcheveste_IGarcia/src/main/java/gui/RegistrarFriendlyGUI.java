package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import businessLogic.BLFacade;
import domain.Usuario;

public class RegistrarFriendlyGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField txtPass1;
	private JPasswordField txtPass2;
	private JLabel textoErrores;

	public RegistrarFriendlyGUI(String supervisorUsr) {
		setTitle("Registrar nuevo usuario Friendly");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 430, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
		lblNombreUsuario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreUsuario.setBounds(30, 30, 150, 20);
		contentPane.add(lblNombreUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(190, 30, 180, 22);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblPass1 = new JLabel("Contraseña:");
		lblPass1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPass1.setBounds(30, 80, 150, 20);
		contentPane.add(lblPass1);
		
		txtPass1 = new JPasswordField();
		txtPass1.setBounds(190, 80, 180, 22);
		contentPane.add(txtPass1);
		
		JLabel lblPass2 = new JLabel("Repetir Contraseña:");
		lblPass2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPass2.setBounds(30, 130, 150, 20);
		contentPane.add(lblPass2);
		
		txtPass2 = new JPasswordField();
		txtPass2.setBounds(190, 130, 180, 22);
		contentPane.add(txtPass2);
		
		textoErrores = new JLabel("");
		textoErrores.setFont(new Font("Tahoma", Font.BOLD, 12));
		textoErrores.setBounds(30, 230, 350, 20);
		contentPane.add(textoErrores);
		
		JButton btnRegistrar = new JButton("Registrar Friendly");
		btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textoErrores.setText("");
				
				String user = txtUsuario.getText();
				String p1 = new String(txtPass1.getPassword());
				String p2 = new String(txtPass2.getPassword());
				
				if(user.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
					textoErrores.setForeground(Color.RED);
					textoErrores.setText("Rellena todos los campos.");
					return;
				}
				
				if(!p1.equals(p2)) {
					textoErrores.setForeground(Color.RED);
					textoErrores.setText("Las contraseñas no coinciden.");
					return;
				}
				
				BLFacade facade = MainGUI.getBusinessLogic();
				
				Usuario userExistente = facade.buscarPorUser(user);
				if (userExistente != null) {
					textoErrores.setForeground(Color.RED);
					textoErrores.setText("El nombre de usuario ya existe.");
				} 
				
				else {
					facade.registrarFriendly(user, p1, supervisorUsr);
					
					textoErrores.setForeground(new Color(0, 150, 0));
					textoErrores.setText("Comprador Friendly registrado con éxito");
					
					txtUsuario.setText("");
					txtPass1.setText("");
					txtPass2.setText("");
				}
			}
		});
		btnRegistrar.setBounds(110, 180, 190, 35);
		contentPane.add(btnRegistrar);
	}
}
