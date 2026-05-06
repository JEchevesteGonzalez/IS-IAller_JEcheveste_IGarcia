package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Friendly;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EliminarFriendlyGUI extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> comboFriendlies;

	public EliminarFriendlyGUI(String usuarioSupervisor) {
		setTitle("Eliminar Friendly - Supervisor: " + usuarioSupervisor);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelecciona = new JLabel("Selecciona el usuario Friendly a eliminar:");
		lblSelecciona.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelecciona.setBounds(30, 30, 350, 20);
		contentPane.add(lblSelecciona);
		
		comboFriendlies = new JComboBox<String>();
		comboFriendlies.setBounds(30, 61, 370, 30);
		contentPane.add(comboFriendlies);
		
		JButton btnEliminar = new JButton("Eliminar Cuenta");
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEliminar.setBounds(130, 130, 180, 40);
		contentPane.add(btnEliminar);

		// --- CARGAR LOS FRIENDLIES DEL SUPERVISOR ---
		BLFacade facade = MainGUI.getBusinessLogic();
		Comprador supervisor = (Comprador) facade.buscarPorUser(usuarioSupervisor);
		
		if (supervisor != null && supervisor.getDependientes() != null && !supervisor.getDependientes().isEmpty()) {
			for (Friendly f : supervisor.getDependientes()) {
				comboFriendlies.addItem(f.getUsuario());
			}
		} else {
			comboFriendlies.addItem("No tienes usuarios asignados");
			comboFriendlies.setEnabled(false);
			btnEliminar.setEnabled(false);
		}

		// --- ACCIÓN DEL BOTÓN ELIMINAR ---
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String friendlySeleccionado = (String) comboFriendlies.getSelectedItem();
				
				int confirmar = JOptionPane.showConfirmDialog(null, 
						"¿Estás seguro de que deseas eliminar permanentemente la cuenta de " + friendlySeleccionado + "?", 
						"Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				
				if (confirmar == JOptionPane.YES_OPTION) {
					// Llamamos al método especializado del Facade
					facade.eliminarFriendlyAsignado(friendlySeleccionado);
					
					JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito.");
					dispose(); // Cerramos la ventana
				}
			}
		});
	}
}