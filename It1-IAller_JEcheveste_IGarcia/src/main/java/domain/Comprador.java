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
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Sale> historialDeCompras;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ArrayList<Oferta> comprasActuales;

	@OneToOne
	private Cuentas cuentas;
	
	
	public Comprador (String usuario, String contrasena){
		this.usuario=usuario;
		this.contrasena=contrasena;
		this.historialDeCompras= new ArrayList<Sale>();	
		this.cuentas = null;
		this.comprasActuales= new ArrayList<Oferta>();
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
		return cuentas.getSaldo();
	}


	public void setSaldo(float saldo) {
		cuentas.setSaldo(saldo);
	}


	public ArrayList<Sale> getHistorialDeCompras() {
		return historialDeCompras;
	}


	public void setHistorialDeCompras(ArrayList<Sale> historialDeCompras) {
		this.historialDeCompras = historialDeCompras;
	}
	
	public Cuentas getCuentas() {
		return cuentas;
	}
	
	public void setCuentas(Cuentas cuentas) {
		this.cuentas = cuentas;
	}
	
	public ArrayList<Oferta> getComprasActuales() {
		return comprasActuales;
	}


	public void setComprasActuales(ArrayList<Oferta> comprasActuales) {
		this.comprasActuales = comprasActuales;
	}
}
