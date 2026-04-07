package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.Comprador;
import domain.Sale;

public class PujarGUI extends JFrame{
	
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel_1;
    private static final int baseSize = 160;
	private static final String basePath="src/main/resources/images/";
	
	private static final long serialVersionUID = 1L;

	private JTextField fieldTitle=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	JLabel labelStatus = new JLabel(); 

	private JLabel jLabelTitle = new JLabel("Titulo");
	private JLabel jLabelDescription = new JLabel("Descripción"); 
	private JLabel jLabelProductStatus = new JLabel("Estado");
	private JLabel jLabelPrice = new JLabel("Precio");
	private JTextField fieldPrice = new JTextField();
	private File selectedFile;
    private String irudia;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JLabel statusField=new JLabel();
	private JFrame thisFrame;
	private JTextField oferta;
	
	public PujarGUI(Sale sale,String usuario, JFrame compra, JFrame posCompras) { 
		thisFrame=this; 
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ultima puja: ");
		if(sale.getOfertas().isEmpty()) {
			lblNewLabel.setText("Puja inicial: ");
		}
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(46, 35, 176, 40);
		getContentPane().add(lblNewLabel);
		
		JLabel lblOferta = new JLabel("Haga su puja:");
		lblOferta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOferta.setBounds(217, 86, 130, 40);
		getContentPane().add(lblOferta);
		
		JLabel lblSiSuOferta = new JLabel("La puja ha de ser mayor a la puja anterior o, igual o mayor a la puja inicial.");
		lblSiSuOferta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSiSuOferta.setBounds(62, 270, 458, 40);
		getContentPane().add(lblSiSuOferta);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.setVisible(false);
			}
		});
		btnVolver.setBounds(472, 11, 89, 23);
		getContentPane().add(btnVolver);
		
		oferta = new JTextField();
		oferta.setBounds(227, 137, 86, 20);
		getContentPane().add(oferta);
		oferta.setColumns(10);
		
		JLabel PrecioSale = new JLabel("");
		PrecioSale.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PrecioSale.setBounds(245, 35, 131, 40);
		getContentPane().add(PrecioSale);
		PrecioSale.setText(String.valueOf(sale.getPrice()));
		
		JLabel textoErrores = new JLabel("");
		textoErrores.setForeground(Color.RED);
		textoErrores.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textoErrores.setBounds(62, 213, 471, 30);
		getContentPane().add(textoErrores);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JButton btnComprar = new JButton("Pujar");
		if(sale.getEsSubasta()==1){
			btnComprar.setText("Pujar");
		}
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(oferta.getText()=="") {
						textoErrores.setText("Haga una puja válida");
					}
					else {
							float ofer = Float.parseFloat(oferta.getText());
							if(ofer<sale.getPrice() && sale.getOfertas().isEmpty()) {
								textoErrores.setText("Haga una oferta válida: el precio mayor o igual al pujado");
							}
							else if(ofer<=sale.getPrice() && !(sale.getOfertas().isEmpty())) {
								textoErrores.setText("Haga una oferta válida: el precio mayor al pujado");
							}
							else {
								boolean exito = facade.pujar(sale, ofer);
								if (exito) {
									if(!(sale.getOfertas().isEmpty())) {
										facade.devolverOfertas(sale);
									}
									facade.crearOferta(usuario, ofer, sale);
									JOptionPane.showMessageDialog(null, "Has pujado por "+ofer+" €.");
									thisFrame.setVisible(false);
									compra.setVisible(false);
									posCompras.setVisible(false);
								}
								else {
									jLabelError.setText("Error al procesar la puja.");
								}
							}
					}
				}catch(NumberFormatException ex){
					textoErrores.setText("Error:introduce una cifra numérica");
				}
			}
		});
		btnComprar.setBounds(229, 177, 89, 23);
		getContentPane().add(btnComprar);
		
		JLabel label = new JLabel("€");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		label.setBounds(388, 35, 65, 40);
		getContentPane().add(label);
		this.setSize(new Dimension(604, 370));
		//this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct"));
 
	}
}
