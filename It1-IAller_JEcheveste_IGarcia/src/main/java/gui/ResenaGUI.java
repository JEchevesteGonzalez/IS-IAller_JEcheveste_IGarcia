package gui;

import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;


public class ResenaGUI extends JFrame {
	
	BLFacade facade = MainGUI.getBusinessLogic();
	
    File targetFile;
    BufferedImage targetImg;
    String encodedfile = null;

    public JPanel panel_1;
    private static final int baseSize = 128;
	private static final String basePath="src/main/resources/images/";

	
	private static final long serialVersionUID = 1L;

	private JTextField fieldValoracion=new JTextField();
	private JTextField fieldDescription=new JTextField();
	
	private JLabel jLabelTitle = new JLabel("Valoracion: ");
	private JLabel jLabelDescription = new JLabel("Descripcion:");

	private JScrollPane scrollPaneEvents = new JScrollPane();
	DefaultComboBoxModel<String> statusOptions = new DefaultComboBoxModel<String>();
	List<String> status;


	private JButton jButtonCreate = new JButton("Crear Resena");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	private JFrame thisFrame;
	private final JButton btnNewButton_2 = new JButton("grabar Imagen");

	public ResenaGUI(Sale sale, String usuario) {

		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.CreateProduct"));

		jLabelTitle.setBounds(new Rectangle(6, 43, 92, 20));
		
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));
		jButtonCreate.setFont(new Font("Lucida Grande", Font.BOLD, 15));

		jButtonCreate.setBounds(new Rectangle(98, 209, 216, 41));

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jLabelMsg.setText("");
				jLabelError.setText("");
				try {
				float val = Float.parseFloat(fieldValoracion.getText());
				if (val <0 || val > 10) {
					jLabelError.setForeground(Color.RED);
					jLabelError.setText("La valoracion deberia ser entre 0 y 10");
				}
				else {
					String desc = fieldDescription.getText();
					if(fieldValoracion.getText().length()==0 || fieldDescription.getText().length()==0){
						jLabelMsg.setForeground(Color.BLACK);
						jLabelMsg.setText("Introduzca los datos necesarios");
					}
					facade.addResena(val, desc, targetFile, sale, usuario);
					jLabelMsg.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Reseña añadida a la compra con éxito","Resena", JOptionPane.INFORMATION_MESSAGE);
					thisFrame.setVisible(false);
				}
				}catch (java.lang.NumberFormatException e1) {
					jLabelError.setForeground(Color.RED);
					jLabelError.setText( ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.ErrorNumber"));		
				}catch (Exception e2) {
					jLabelError.setForeground(Color.RED);
					jLabelError.setText(e2.getMessage());
				}
			}
		});
		jButtonClose.setBounds(new Rectangle(474, 280, 101, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);			}
		});

		jLabelMsg.setBounds(new Rectangle(26, 290, 377, 20));
		jLabelMsg.setForeground(Color.red);

		jLabelError.setBounds(new Rectangle(16, 275, 384, 20));
		jLabelError.setForeground(Color.red);
		
	    status=Utils.getStatus();
		for(String s:status) statusOptions.addElement(s);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jLabelTitle, null);
		
		jLabelDescription.setBounds(6, 97, 109, 16);
		getContentPane().add(jLabelDescription);
		
		
		fieldValoracion.setBounds(98, 40, 83, 26);
		getContentPane().add(fieldValoracion);
		fieldValoracion.setColumns(10);
		
		
		fieldDescription.setBounds(98, 93, 250, 73);
		getContentPane().add(fieldDescription);
		fieldDescription.setColumns(10);
		
		JButton btnFoto = new JButton("Foto");
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF", "jpg", "gif");
				fileChooser.setFileFilter(filter);
		        int result = fileChooser.showOpenDialog(null);  

		        fileChooser.setBounds(30, 148, 320, 80);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            targetFile = fileChooser.getSelectedFile();
		            panel_1.removeAll();
		            panel_1.repaint();

		            try {
		                targetImg = rescale(ImageIO.read(targetFile));
		                encodeFileToBase64Binary(targetFile);
		            } catch (IOException ex) {
		                //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
		            }
		            
		            panel_1.setLayout(new BorderLayout(0, 0));
		            panel_1.add(new JLabel(new ImageIcon(targetImg))); 
		            setVisible(true);

		            }
			}
		});
		btnFoto.setBounds(413, 195, 162, 29);
		getContentPane().add(btnFoto);
		
		panel_1 = new JPanel();
		panel_1.setBounds(413, 60, 162, 106);
		getContentPane().add(panel_1);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					BufferedImage img = ImageIO.read(targetFile);
					
				    File outputfile = new File(basePath+targetFile.getName());

				   ImageIO.write(img, "png", outputfile);  // ignore returned boolean
				   System.out.println("file stored "+img);
				} catch(IOException ex) {
				 //System.out.println("Write error for " + outputfile.getPath()  ": " + ex.getMessage());
				  }
				
			}
		});
		btnNewButton_2.setBounds(137, 350, 117, 29);
		
		getContentPane().add(btnNewButton_2);
		
	}	 

	public BufferedImage rescale(BufferedImage originalImage)
    {
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	
public  String encodeFileToBase64Binary(File file){
        try {
            @SuppressWarnings("resource")
			FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile=new String(Base64.getEncoder().encode(bytes));

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodedfile;
    }
}
