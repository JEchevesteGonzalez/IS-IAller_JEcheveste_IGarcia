package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Resena {

	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer resenaNumber;
	private float valoracion;
	private String fotoProducto;
	private String descripcion;
	@ManyToOne
	private Sale sale;
	private String nUser; //usuario del comprador que pone la resena
	@ManyToOne
	private Seller seller;
	
	
	public Resena(float valoracion, String descripcion, File fotoProducto, Sale sale, String nUser, Seller sel) {
		this.valoracion = valoracion;
		this.descripcion = descripcion;
		this.sale = sale;
		this.nUser = nUser;
		seller = sel;
		if (fotoProducto!=null) {
		    this.fotoProducto=fotoProducto.getName();
			try {
				BufferedImage img1 = ImageIO.read(fotoProducto);

				String path="src/main/resources/images/";
				File outputfile = new File(path+fotoProducto.getName());
		    
		    
			   ImageIO.write(img1, fotoProducto.getName().substring(fotoProducto.getName().lastIndexOf(".") + 1), outputfile);  // ignore returned boolean

			} catch(IOException ex) {
				//System.out.println("Write error for " + outputfile.getPath()  ": " + ex.getMessage());
			}
		}
	}
	
	

	public Integer getResenaNumber() {
		return resenaNumber;
	}


	public void setResenaNumber(Integer resenaNumber) {
		this.resenaNumber = resenaNumber;
	}



	public float getValoracion() {
		return valoracion;
	}

	public void setValoracion(float valoracion) {
		this.valoracion = valoracion;
	}

	public String getFotoProducto() {
		return fotoProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public String getnUser() {
		return nUser;
	}

	public void setnUser(String nUser) {
		this.nUser = nUser;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String toString(){
		return this.resenaNumber+";"+this.valoracion;  
	}
	
	public String getFile() {
		return fotoProducto;
	}
}
