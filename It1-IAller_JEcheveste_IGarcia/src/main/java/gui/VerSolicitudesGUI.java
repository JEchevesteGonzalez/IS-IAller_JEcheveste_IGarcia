package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
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
	private BLFacade facade = MainGUI.getBusinessLogic();
	private JFrame thisFrame;

	public VerSolicitudesGUI(String usuario) {
		setTitle("Mis Solicitudes - " + usuario);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		thisFrame = this;

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		String[] columnNames = {"Producto", "Estado de la Solicitud", "S"};
		tableModel = new DefaultTableModel(null, columnNames);
		table = new JTable(tableModel);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2));
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);

		cargarSolicitudes(usuario);
		
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Solicitud s=(Solicitud) tableModel.getValueAt(row, 2);
	            	Sale sal = facade.buscarPorNum(s.getSaleNumber());
	            	
	            	JFrame sale = new ShowSaleGUI(sal,true,s.getSupervisor().getUsuario(),thisFrame, true);
					sale.setVisible(true);
	            }
	        }
	 });
	}

	private void cargarSolicitudes(String usuario) {
		
		Friendly friendly = (Friendly) facade.buscarPorUser(usuario);
		DefaultListModel<Solicitud> modelo = new DefaultListModel<>();
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
				fila.add(solicitud);
				
				tableModel.addRow(fila);
				modelo.addElement(solicitud);
			}
		}
	}
}