package gui;

import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.awt.image.BufferedImage;

import businessLogic.BLFacade;
import domain.Resena;
import domain.Sale;


public class ShowResenaGUI extends JFrame {
	
    File targetFile;
    BufferedImage targetImg;
    public JPanel panel_1;
    private static final int baseSize = 160;
	private static final String basePath="src/main/resources/images/";
	
	private static final long serialVersionUID = 1L;

	private JTextField fieldUsuario=new JTextField();
	private JTextField fieldDescription=new JTextField();

	private JLabel jLabelValoracion = new JLabel("Usuario");
	private JLabel jLabelDescription = new JLabel("Descripcion"); 
	private JLabel jLabelProductStatus = new JLabel("Foto");
	private JLabel jLabelPrice = new JLabel("Valoracion");
	private JTextField fieldValoracion = new JTextField();
	private File selectedFile;
    private String irudia;

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	private JLabel jLabelMsg = new JLabel();
	private JLabel statusField=new JLabel();
	private JFrame thisFrame;
	private final JButton btnVerVentaO = new JButton("Ver Venta o Subasta Ligada");
	
	public ShowResenaGUI(Resena resena, boolean comprador,String usuario, JFrame listado) { 
		thisFrame=this; 
		this.setVisible(true);
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		//this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateProductGUI.CreateProduct"));

		fieldUsuario.setText(resena.getnUser());
		fieldDescription.setText(resena.getDescripcion());

		fieldValoracion.setText(Float.toString(resena.getValoracion()));
		
		jLabelValoracion.setBounds(new Rectangle(6, 56, 92, 20));
		
		jLabelPrice.setBounds(new Rectangle(6, 166, 101, 20));
		fieldValoracion.setEditable(false);
		fieldValoracion.setBounds(new Rectangle(137, 166, 66, 29));

		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jLabelMsg.setBounds(new Rectangle(275, 214, 305, 20));
		jLabelMsg.setForeground(Color.red);
		

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelValoracion, null);
		
		
		this.getContentPane().add(jLabelPrice, null);
		this.getContentPane().add(fieldValoracion, null);
		
		jLabelProductStatus.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelProductStatus.setBounds(260, 164, 140, 25);
		getContentPane().add(jLabelProductStatus);
		
		jLabelDescription.setBounds(6, 81, 109, 16);
		getContentPane().add(jLabelDescription);
		fieldUsuario.setEditable(false);
		
		
		fieldUsuario.setBounds(128, 53, 370, 26);
		getContentPane().add(fieldUsuario);
		fieldUsuario.setColumns(10);
		fieldDescription.setEditable(false);
		
		
		fieldDescription.setBounds(127, 81, 371, 73);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		panel_1 = new JPanel();
		panel_1.setBounds(318, 166, 180, 160);
		getContentPane().add(panel_1);
		btnVerVentaO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame sale = new ShowSaleGUI(resena.getSale(),comprador,usuario,thisFrame, true);
				sale.setVisible(true);
			}
		});
		btnVerVentaO.setBounds(16, 242, 285, 45);
		
		getContentPane().add(btnVerVentaO);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		String file=resena.getFile();
		if (file!=null) {
			Image img=facade.downloadImage(file);
			targetImg = rescale((BufferedImage)img);
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		}
	}	 
	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
}

