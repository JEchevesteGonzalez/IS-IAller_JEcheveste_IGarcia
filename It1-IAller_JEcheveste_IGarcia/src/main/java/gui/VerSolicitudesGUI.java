package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Friendly;
import domain.Sale;
import domain.Solicitud;

public class VerSolicitudesGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;

	public VerSolicitudesGUI(String usuario) {
		setTitle("Mis Solicitudes - " + usuario);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		String[] columnNames = {"Producto", "Estado de la Solicitud"};
		tableModel = new DefaultTableModel(null, columnNames);
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);

		cargarSolicitudes(usuario);
	}

	private void cargarSolicitudes(String usuario) {
		BLFacade facade = MainGUI.getBusinessLogic();
		
		Friendly friendly = (Friendly) facade.buscarPorUser(usuario);

		if (friendly != null) {
			ArrayList<Solicitud> listaSolicitudes = friendly.getSolicitudes();

			for (Solicitud solicitud : listaSolicitudes) {
				Vector<Object> fila = new Vector<Object>();
				
				Sale producto = facade.buscarVentaPorId(solicitud.getSaleNumber());

				if (producto != null) {
					fila.add(producto.getTitle());
				} else {
					fila.add("Producto no disponible");
				}
				
				fila.add(solicitud.getEstado());
				
				tableModel.addRow(fila);
			}
		}
	}
}