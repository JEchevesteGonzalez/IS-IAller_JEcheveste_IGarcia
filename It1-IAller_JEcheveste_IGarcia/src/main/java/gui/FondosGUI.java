package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Comprador;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FondosGUI extends JFrame{
	
	private JTextField textField;
	private JTextField rFondos;
	public FondosGUI(String usuario) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 300);
		getContentPane().setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel lblNewLabel = new JLabel("Inserta la cantidad de fondos que quieras añadir:");
		lblNewLabel.setBounds(10, 70, 289, 42);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(297, 81, 121, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Tu saldo actual es:");
		lblNewLabel_1.setBounds(10, 45, 125, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblfondosActuales = new JLabel("");
		lblfondosActuales.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblfondosActuales.setForeground(new Color(0, 0, 0));
		lblfondosActuales.setBackground(new Color(255, 255, 255));
		lblfondosActuales.setBounds(147, 45, 112, 14);
		getContentPane().add(lblfondosActuales);
		
		JLabel textoErrores = new JLabel("");
		textoErrores.setForeground(new Color(255, 0, 0));
		textoErrores.setBounds(20, 184, 216, 42);
		getContentPane().add(textoErrores);
		
		Comprador userDB = facade.buscarPorUser(usuario);
		if (userDB != null) {
			lblfondosActuales.setText(userDB.getSaldo() + " €"); // Asume que tu método se llama getSaldo()
		}
		
		JButton btnNewButton = new JButton("Añadir Fondos");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 try {
				 
					float cantidadAAñadir = Float.parseFloat(textField.getText());
					
					if (cantidadAAñadir <= 0) {
						textoErrores.setText("Error: Introduce una cantidad mayor a 0.");
						return;
					}
					
					boolean exito = facade.anadirFondos(usuario, cantidadAAñadir);
					
					if (exito) {
						textField.setText("");
						textoErrores.setForeground(Color.GREEN);
						Comprador userActualizado = facade.buscarPorUser(usuario);
						lblfondosActuales.setText(userActualizado.getSaldo() + " €");
					} else {
						textoErrores.setText("Error en la base de datos al añadir fondos.");
					}
					
				} catch (NumberFormatException ex) {
					textoErrores.setText("Error: Introduce solo números ");
				}
			
				 
			}
		});
		btnNewButton.setBounds(297, 114, 121, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Atrás");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(10, 216, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblInsertaLaCantidad = new JLabel("Inserta la cantidad de fondos que quieras retirar:");
		lblInsertaLaCantidad.setBounds(10, 161, 289, 42);
		getContentPane().add(lblInsertaLaCantidad);
		
		rFondos = new JTextField();
		rFondos.setColumns(10);
		rFondos.setBounds(299, 172, 121, 20);
		getContentPane().add(rFondos);
		
		JButton btnRetirarFondos = new JButton("Retirar Fondos");
		btnRetirarFondos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 try {
				 
					float cantidadARetirar = Float.parseFloat(textField.getText());
					
					if (cantidadARetirar <= 0) {
						textoErrores.setText("Error: Introduce una cantidad mayor a 0.");
						return;
					}
					
					boolean exito = facade.retirarFondos(usuario, cantidadARetirar);
					
					if (exito) {
						textField.setText("");
						textoErrores.setForeground(Color.GREEN);
						Comprador userActualizado = facade.buscarPorUser(usuario);
						lblfondosActuales.setText(userActualizado.getSaldo() + " €");
					} else {
						textoErrores.setText("Error en la base de datos al retirar fondos.");
					}
					
				} catch (NumberFormatException ex) {
					textoErrores.setText("Error: Introduce solo números ");
				}
			
				 
			}
		});
		
		btnRetirarFondos.setBounds(299, 203, 121, 23);
		getContentPane().add(btnRetirarFondos);
		
		
	}
}
