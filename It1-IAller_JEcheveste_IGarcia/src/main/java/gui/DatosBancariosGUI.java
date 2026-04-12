package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Cuentas;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class DatosBancariosGUI extends JFrame{
	JFrame thisFrame=this;
	private JTextField nCuenta;
	private JTextField sInic;
	private JTextField nBanco;
	public DatosBancariosGUI(String usuarioIntro, String contrIntro) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JLabel lblIntroduceElNumero = new JLabel("Introduce el numero de cuenta:");
		lblIntroduceElNumero.setBounds(12, 56, 186, 16);
		getContentPane().add(lblIntroduceElNumero);
		
		JLabel lblIntroduceElSaldo = new JLabel("Introduce el saldo inicial:");
		lblIntroduceElSaldo.setBounds(12, 108, 143, 16);
		getContentPane().add(lblIntroduceElSaldo);
		
		JLabel lblIntroduceElNombre = new JLabel("Introduce el nombre del banco:");
		lblIntroduceElNombre.setBounds(12, 157, 186, 16);
		getContentPane().add(lblIntroduceElNombre);
		
		nCuenta = new JTextField();
		nCuenta.setBounds(201, 53, 116, 22);
		getContentPane().add(nCuenta);
		nCuenta.setColumns(10);
		
		sInic = new JTextField();
		sInic.setColumns(10);
		sInic.setBounds(201, 105, 116, 22);
		getContentPane().add(sInic);
		
		nBanco = new JTextField();
		nBanco.setColumns(10);
		nBanco.setBounds(201, 154, 116, 22);
		getContentPane().add(nBanco);
		
		JLabel lblIntroduceTusDatos = new JLabel("Introduce tus datos bancarios");
		lblIntroduceTusDatos.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblIntroduceTusDatos.setBounds(54, 0, 337, 40);
		getContentPane().add(lblIntroduceTusDatos);
		
		JLabel lblErrores = new JLabel("\r\n");
		lblErrores.setBounds(12, 224, 365, 16);
		getContentPane().add(lblErrores);
		
		JButton btnGuardarDatos = new JButton("Guardar datos");
		btnGuardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				Cuentas cu = new Cuentas();
				try {
					cu.setNumeroCuenta(Integer.parseInt(nCuenta.getText()));
					cu.setSaldo(Float.parseFloat(sInic.getText()));
					cu.setNombreBanco(nBanco.getText());
					facade.anadirCuentas(usuarioIntro, contrIntro, cu);
					thisFrame.setVisible(false);
				}
				catch (Exception e){
					lblErrores.setText("Introduce valores correctos: Cuenta y saldo numeros, banco texto");
				}
			}
		});
		btnGuardarDatos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnGuardarDatos.setBounds(109, 185, 224, 40);
		getContentPane().add(btnGuardarDatos);
	}
}
