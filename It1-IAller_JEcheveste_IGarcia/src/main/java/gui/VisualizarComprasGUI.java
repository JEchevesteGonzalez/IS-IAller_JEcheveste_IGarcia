package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Sale;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class VisualizarComprasGUI extends JFrame {

	JFrame thisFrame;
	private JPanel contentPane;
	private JTable table;
	private String[] nombreColumnas = {"Título", "Precio", "Fecha"};
	private DefaultTableModel tableModelProducts = new DefaultTableModel(null, nombreColumnas);

	public boolean isCellEditable(int row, int column) {
		return false; 
	}
	
	/**
	 * Create the frame.
	 */
	public VisualizarComprasGUI(String usuario) {
		thisFrame=this;
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(4);
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		Comprador user = (Comprador) facade.buscarPorUser(usuario);
		ArrayList<Sale> historial = user.getHistorialDeCompras();
		
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(4);
		
		if (historial != null) {
			for (Sale s : historial) {
				
				Vector<Object> row = new Vector<Object>();
				row.add(s.getTitle());
				row.add(s.getPrice());
				row.add(s.getPublicationDate());
				row.add(s); // Metemos el objeto Sale en la columna 4
				
				tableModelProducts.addRow(row); // Lo añadimos al modelo de la tabla
			}
		}
		
		DefaultListModel<Sale> modelo = new DefaultListModel<>();

		for (Sale s : historial) {
		    modelo.addElement(s);
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 36, 380, 204);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(tableModelProducts);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
		
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Sale s=(Sale) tableModelProducts.getValueAt(row, 3);
	            	
		            new ShowSaleGUI(s, true, usuario, thisFrame, false);
	            }
	        }
	 });
	
}
}
