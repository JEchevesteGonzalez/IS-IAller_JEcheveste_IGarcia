package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import domain.Comprador;
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Seller extends Comprador implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlID
	private String email;
	private String name; 
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private List<Sale> sales=new ArrayList<Sale>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private ArrayList<Resena> resenas = new ArrayList<Resena>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private ArrayList<Sale> devoluciones = new ArrayList<Sale>();
	
	public Seller(Comprador c,String email, String name) {
		super(c);
		this.email = email;
		this.name = name;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public List<Sale> getSales() {
		return sales;
	}
	
	public ArrayList<Resena> getResenas() {
		return resenas;
	}

	public String toString(){
		return email+";"+name+sales;
	}
	
	/**
	 * This method creates/adds a sale to a seller
	 * 
	 * @param title of the sale
	 * @param description of the sale
	 * @param status 
	 * @param tVenta 
	 * @param selling price
	 * @param publicationDate
	 * @return Sale
	 */
	
	


	public Sale addSale(String title, String description, int status, float price,  Date pubDate, File file, int tVenta)  {
		
		Sale sale=new Sale(title, description, status, price,  pubDate, file, this, tVenta);
        sales.add(sale);
        return sale;
	}
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesSaleExist(String title)  {	
		for (Sale s:sales)
			if ( s.getTitle().compareTo(title)==0 )
			 return true;
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (email != other.email)
			return false;
		return true;
	}

	
}
