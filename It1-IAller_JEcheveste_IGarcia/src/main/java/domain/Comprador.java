package domain;

import java.util.ArrayList;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import domain.Comprador;

@Entity
public class Comprador extends Usuario{
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private ArrayList<Sale> historialDeCompras;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private ArrayList<Oferta> ofertasEnCurso;

	@OneToOne
	private Cuentas cuentas;
	
	
	public Comprador (String usuario, String contrasena){
		super(usuario, contrasena);
		this.historialDeCompras= new ArrayList<Sale>();	
		this.cuentas = null;
		this.ofertasEnCurso= new ArrayList<Oferta>();
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
	
	public ArrayList<Oferta> getOfertasEnCurso() {
		return ofertasEnCurso;
	}


	public void setOfertasEnCurso(ArrayList<Oferta> ofertasEnCurso) {
		this.ofertasEnCurso = ofertasEnCurso;
	}
}
