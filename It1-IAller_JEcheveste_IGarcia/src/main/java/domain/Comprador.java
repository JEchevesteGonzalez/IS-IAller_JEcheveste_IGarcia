package domain;

import java.util.ArrayList;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import domain.Comprador;

@Entity
public class Comprador {
	@Id
	private String usuario;
	private String contrasena;
	private float saldo;
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Sale> historialDeCompras;
	
	
	public Comprador (String usuario, String contrasena){
		this.usuario=usuario;
		this.contrasena=contrasena;
		this.saldo=0;
		this.historialDeCompras= new ArrayList<Sale>();	
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public float getSaldo() {
		return saldo;
	}


	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}


	public ArrayList<Sale> getHistorialDeCompras() {
		return historialDeCompras;
	}


	public void setHistorialDeCompras(ArrayList<Sale> historialDeCompras) {
		this.historialDeCompras = historialDeCompras;
	}
	
	
	
}
