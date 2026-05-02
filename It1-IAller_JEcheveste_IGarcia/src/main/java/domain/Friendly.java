package domain;

import java.util.ArrayList;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import domain.Comprador;

@Entity
public class Friendly extends Usuario{
	@ManyToOne
	private Comprador supervisor;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private ArrayList<Solicitud> solicitudes = new ArrayList<Solicitud>();
	
	public Friendly (String usuario, String contrasena, Comprador supervisor){
		super(usuario,contrasena);
		this.supervisor = supervisor;
	}

	public Comprador getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Comprador supervisor) {
		this.supervisor = supervisor;
	}

	public ArrayList<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(ArrayList<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

}
