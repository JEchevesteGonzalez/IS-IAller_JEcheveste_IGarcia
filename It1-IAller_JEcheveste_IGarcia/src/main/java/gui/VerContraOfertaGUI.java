package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Oferta;
import domain.Sale;
import domain.Seller;

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
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class VerContraOfertaGUI extends JFrame {	

	JFrame thisFrame;
	private JPanel contentPane;
	private JTable table;
	private String[] nombreColumnas = {"Comprador", "Precio"};
	private DefaultTableModel tableModelProducts = new DefaultTableModel(null, nombreColumnas);

	public boolean isCellEditable(int row, int column) {
		return false; 
	}
	
	/**
	 * Create the frame.
	 */
	public VerContraOfertaGUI(Sale s){
		thisFrame=this;
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(3);
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		ArrayList<Oferta> historial = s.getOfertas();
		
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(3);
		
		if (historial != null) {
			for (Oferta f : historial) {
				Vector<Object> row = new Vector<Object>();
				row.add(f.getnUser());
				row.add(f.getPrecio());
				row.add(f); // Metemos el objeto Sale en la columna 4
				
				tableModelProducts.addRow(row); // Lo añadimos al modelo de la tabla
			}
		}
		
		DefaultListModel<Oferta> modelo = new DefaultListModel<>();

		for (Oferta f : historial) {
		    modelo.addElement(f);
		}
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 36, 380, 204);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(tableModelProducts);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2));
		
		scrollPane.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Sale s=(Sale) tableModelProducts.getValueAt(row, 2);
	            }
	        }
	 });
		
	}
	
}
