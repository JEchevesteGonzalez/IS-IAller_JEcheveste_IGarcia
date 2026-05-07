package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Resena;
import domain.Sale;
import domain.Solicitud;

public class GestionarSolicitudesGUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] columnNames = {"Friendly", "Producto", "Estado", "S"};
	private JFrame thisFrame;
	
	public GestionarSolicitudesGUI(String usuarioSupervisor) {
		setTitle("Gesti�n de Solicitudes Friendly - Supervisor: " + usuarioSupervisor);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		thisFrame = this;

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 664, 250);
		contentPane.add(scrollPane);

		tableModel = new DefaultTableModel(null, columnNames);
		table = new JTable(tableModel);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
		scrollPane.setViewportView(table);
		
		JButton btnAceptar = new JButton("Aceptar Solicitud");
		btnAceptar.setBounds(100, 300, 200, 40);
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnAceptar);

		JButton btnRechazar = new JButton("Rechazar Solicitud");
		btnRechazar.setBounds(380, 300, 200, 40);
		btnRechazar.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnRechazar);

		BLFacade facade = MainGUI.getBusinessLogic();
		llenarTabla(usuarioSupervisor, facade);


		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = table.getSelectedRow();
				if (fila != -1) {
					Solicitud s = (Solicitud) tableModel.getValueAt(fila, 3);
					facade.actualizarEstadoSolicitud(s.getSolNumber(), "Aceptada");
					JOptionPane.showMessageDialog(null, "Solicitud aceptada");
					thisFrame.setVisible(false);

					Sale sale = facade.buscarPorNum(s.getSaleNumber());
					new ShowSaleGUI(sale, true, usuarioSupervisor, thisFrame, false);
				}
				else {
		            JOptionPane.showMessageDialog(null, "Por favor, selecciona una solicitud.");
		        }
			}
		});

		btnRechazar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = table.getSelectedRow();
				if (fila != -1) {
					Solicitud s = (Solicitud) tableModel.getValueAt(fila, 3);
					facade.actualizarEstadoSolicitud(s.getSolNumber(), "Rechazada");
					JOptionPane.showMessageDialog(null, "Solicitud rechazada.");
					thisFrame.setVisible(false);
				}
				else {
		            JOptionPane.showMessageDialog(null, "Por favor, selecciona una solicitud.");
		        }
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Solicitud s=(Solicitud) tableModel.getValueAt(row, 3);
	            	Sale sal = facade.buscarPorNum(s.getSaleNumber());
	            	
	            	JFrame sale = new ShowSaleGUI(sal,true,s.getSupervisor().getUsuario(),thisFrame, true);
					sale.setVisible(true);
	            }
	        }
	 });
		
	}

	private void llenarTabla(String usuarioSupervisor, BLFacade facade) {
		Comprador supervisor = (Comprador) facade.buscarPorUser(usuarioSupervisor);
		if (supervisor != null && supervisor.getSolicitudes() != null) {
			ArrayList<Solicitud> sols = supervisor.getSolicitudes();
			tableModel.setRowCount(0);
			DefaultListModel<Solicitud> modelo = new DefaultListModel<>();
			for (Solicitud s : sols) {
				
				Vector<Object> row = new Vector<Object>();
				
				if(s.getFriendly() != null && s.getSaleNumber() != null && (s.getEstado().equals("Aceptada") || s.getEstado().equals("En tramite"))) {
					
					Sale sA = facade.buscarVentaPorId(s.getSaleNumber());
		
					if (sA !=null) {
						row.add(s.getFriendly().getUsuario());
						row.add(sA.getTitle());
						row.add(s.getEstado());
						row.add(s);
						
						tableModel.addRow(row);
						
						modelo.addElement(s);
					}
				}
			}
		}
	}
}
