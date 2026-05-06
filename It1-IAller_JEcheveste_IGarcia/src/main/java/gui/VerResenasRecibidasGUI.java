package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Resena;
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
public class VerResenasRecibidasGUI extends JFrame {	

	JFrame thisFrame;
	private JPanel contentPane;
	private JTable table;
	private String[] nombreColumnas = {"Título", "Precio", "Tipo", "Usuario Comprador", "Valoracion"};
	private DefaultTableModel tableModelProducts = new DefaultTableModel(null, nombreColumnas);

	public boolean isCellEditable(int row, int column) {
		return false; 
	}
	
	/**
	 * Create the frame.
	 */
	private void crearTabla(List<Resena> historial) {
		tableModelProducts.setRowCount(0);
		if (historial != null) {
			DefaultListModel<Resena> modelo = new DefaultListModel<>();
			for (Resena r : historial) {
				Vector<Object> row = new Vector<Object>();
				row.add(r.getSale().getTitle());
				row.add(r.getSale().getPrice());
				row.add(r.getSale().getQueEs());
				row.add(r.getnUser());
				row.add(r.getValoracion());
				row.add(r); // Metemos el objeto Resena en la columna 6
				
				tableModelProducts.addRow(row); // Lo añadimos al modelo de la tabla

				modelo.addElement(r);
			}
		}
	}
	
	public VerResenasRecibidasGUI(String usuario, boolean comprador, Integer saleNum) {
		thisFrame=this;
		
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(6);
		
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();
		
		List<Resena> historial = new ArrayList<Resena>();
		if(comprador && saleNum != null) {
			Sale sale = facade.buscarPorNum(saleNum);
			historial = sale.getResenas();
		}
		else{
			Seller user = (Seller) facade.buscarPorUser(usuario);
			historial = user.getResenas();
		}
		
		crearTabla(historial);
		
		
		table = new JTable();
		table.setModel(tableModelProducts);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 19, 380, 221);
		getContentPane().add(scrollPane);
		
		scrollPane.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	Resena r=(Resena) tableModelProducts.getValueAt(row, 5);
	            	
		            new ShowResenaGUI(r, comprador, usuario, thisFrame);
	            }
	        }
	 });
		
	}
	
}
