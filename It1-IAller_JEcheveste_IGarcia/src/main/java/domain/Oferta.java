package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Oferta {
	@Id
	@GeneratedValue
	private Integer ofertaNumber;
	private String nUser;
	private float precio;
	
	@ManyToOne
	private Sale s;
	
	public Oferta(String nUser, float precio, Sale s) {
		this.nUser = nUser;
		this.precio= precio;
		this.s = s;
	}

	public String getnUser() {
		return nUser;
	}

	public void setnUser(String nUser) {
		this.nUser = nUser;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Sale getS() {
		return s;
	}

	public void setS(Sale s) {
		this.s = s;
	}
	
	public Integer getOfertaNumber() {
		return ofertaNumber;
	}

	public void setOfertaNumber(Integer ofertaNumber) {
		this.ofertaNumber = ofertaNumber;
	}
	
}
