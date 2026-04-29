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
public class VisualizarVentasActivasGUI extends JFrame {	

	JFrame thisFrame;
	private JPanel contentPane;
	private JTable table;
	private String[] nombreColumnas = {"Título", "Precio", "Fecha", "Habilitada"};
	private DefaultTableModel tableModelProducts = new DefaultTableModel(null, nombreColumnas);

	public boolean isCellEditable(int row, int column) {
		return false; 
	}
	
	/**
	 * Create the frame.
	 */
	private void crearTabla(List<Sale> historial, int sub) {
		tableModelProducts.setRowCount(0);
		if (historial != null) {
			DefaultListModel<Sale> modelo = new DefaultListModel<>();
			for (Sale s : historial) {
				if(s.getEsSubasta()==sub) {
					Vector<Object> row = new Vector<Object>();
					row.add(s.getTitle());
					row.add(s.getPrice());
					row.add(s.getPublicationDate());
					row.add(s.isHabilitado());
					row.add(s); // Metemos el objeto Sale en la columna 4
					
					tableModelProducts.addRow(row); // Lo añadimos al modelo de la tabla
	
					modelo.addElement(s);
				}
			}
		}
	}
	
	public VisualizarVentasActivasGUI(String usuario) {
		thisFrame=this;
		
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(5);
		
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		Seller user = (Seller) facade.buscarPorUser(usuario);
		List<Sale> historial = user.getSales();
		
		int sub;
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearTabla(historial,comboBox.getSelectedIndex());
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 20));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Oferta", "Subasta"}));
		comboBox.setBounds(113, 13, 220, 22);
		getContentPane().add(comboBox);
		
		sub = comboBox.getSelectedIndex();
		
		crearTabla(historial,sub);
		
		
		table = new JTable();
		table.setModel(tableModelProducts);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(4));
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 36, 380, 204);
		getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Sale s=(Sale) tableModelProducts.getValueAt(row, 4);
	            	
		            new ShowSaleGUI(s, false, usuario, thisFrame);
	            }
	        }
	 });
		
	}
	
}
