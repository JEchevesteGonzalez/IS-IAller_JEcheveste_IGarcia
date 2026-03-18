package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class SellerGUI extends JFrame {
		private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton;
	private JButton btnCrearVenta;
	
	/**
	 * This is the default constructor
	 */
	public SellerGUI( String usuario) {
		super();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(495, 290);
		
		jContentPane = new JPanel();
		
		
		setContentPane(jContentPane);
		jContentPane.setLayout(null);
		
		btnNewButton = new JButton("Visualizar Ventas Activas\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame verVentasActivas = new VisualizarVentasActivasGUI(usuario);
				verVentasActivas.setVisible(true);
			}
		});
		btnNewButton.setBounds(0, 0, 477, 128);
		jContentPane.add(btnNewButton);
		
		btnCrearVenta = new JButton("Crear Venta");
		btnCrearVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame crear = new CreateSaleGUI(usuario);
				crear.setVisible(true);
			}
		});
		btnCrearVenta.setBounds(0, 123, 477, 128);
		jContentPane.add(btnCrearVenta);
		
	}
	
	
} // @jve:decl-index=0:visual-constraint="0,0"

