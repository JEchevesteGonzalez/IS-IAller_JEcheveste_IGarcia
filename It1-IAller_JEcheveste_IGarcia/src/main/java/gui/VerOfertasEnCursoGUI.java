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
import javax.swing.JOptionPane;
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
public class VerOfertasEnCursoGUI extends JFrame {	

	JFrame thisFrame;
	private JPanel contentPane;
	private JTable table;
	private String[] nombreColumnas = {"Nombre de venta", "Precio ofertado"};
	private DefaultTableModel tableModelProducts = new DefaultTableModel(null, nombreColumnas);
	private Oferta PosO = null;
	private Sale sale = null;

	public boolean isCellEditable(int row, int column) {
		return false; 
	}
	
	/**
	 * Create the frame.
	 */
	
	private void crearTabla(List<Oferta> historial) {
		tableModelProducts.setRowCount(0);
		DefaultListModel<Oferta> modelo = new DefaultListModel<>();
		if (historial != null) {
			for (Oferta f : historial) {
				Vector<Object> row = new Vector<Object>();
				Sale s = f.getS();
				if(s!=null && s.getEsSubasta()==0) {
					row.add(s.getTitle());
					row.add(f.getPrecio());
					row.add(f); // Metemos el objeto Sale en la columna 4
					row.add(s);
				
					tableModelProducts.addRow(row); // Lo añadimos al modelo de la tabla
				
					modelo.addElement(f);
				}
			}
		}
		
	}
	
	public VerOfertasEnCursoGUI(String usuario){
		thisFrame=this;
		
		tableModelProducts.setDataVector(null, nombreColumnas);
		tableModelProducts.setColumnCount(4);
		
		this.setSize(495, 290);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(null);
		BLFacade facade = MainGUI.getBusinessLogic();

		ArrayList<Oferta> historial = facade.buscarPorUser(usuario).getOfertasEnCurso();

		crearTabla(historial);
		
		table = new JTable();
		table.setModel(tableModelProducts);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(3));
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(2));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 40, 380, 141);
		getContentPane().add(scrollPane);

		scrollPane.setViewportView(table);
		
		JLabel lblErrores = new JLabel("");
		lblErrores.setBounds(12, 227, 368, 16);
		getContentPane().add(lblErrores);
		
		
		table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            if(mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1) {
			        JTable tablaClicada =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = tablaClicada.rowAtPoint(point);
			        
	            	PosO=(Oferta) tableModelProducts.getValueAt(row, 2);
	            	sale=(Sale) tableModelProducts.getValueAt(row, 3);
	            }
	        }
	 });
		JButton btnEliminarOferta = new JButton("Eliminar Oferta");
		btnEliminarOferta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(PosO!=null && sale!=null) {
					int confirmacion = JOptionPane.showConfirmDialog(null, 
							"¿Estás seguro de que deseas eliminar la oferta?", 
							"Confirmar Borrado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (confirmacion == JOptionPane.YES_OPTION) {
						facade.eliminarOferta(sale, PosO).getOfertas();
						crearTabla(facade.buscarPorUser(usuario).getOfertasEnCurso());
					}
				}
				else {
					lblErrores.setText("No se ha escogido oferta.");
				}
			}
		});
		
		btnEliminarOferta.setBounds(12, 194, 131, 25);
		getContentPane().add(btnEliminarOferta);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setBounds(372, 194, 93, 25);
		getContentPane().add(btnSalir);
		
	}
}
