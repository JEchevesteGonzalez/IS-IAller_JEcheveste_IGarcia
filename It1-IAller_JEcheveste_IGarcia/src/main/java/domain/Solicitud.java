package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@Entity
public class Solicitud {

	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer solicitudNumber;
	private Integer saleNumber;
	private String estado; //En tramite, rechazada, aceptada
	@ManyToOne
	private Friendly friendly;
	@ManyToOne
	private Comprador supervisor;
	

	public Solicitud(Integer saleNumber, String estado, Friendly friendly, Comprador supervisor) {
		this.saleNumber = saleNumber;
		this.estado = estado;
		this.friendly = friendly;
		this.supervisor = supervisor;
	}
	public Integer getSolNumber() {
		return solicitudNumber;
	}
	public void setSolNumber(Integer solNumber) {
		this.solicitudNumber = solNumber;
	}
	public Integer getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(Integer saleNumber) {
		this.saleNumber = saleNumber;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Friendly getFriendly() {
		return friendly;
	}
	public void setFriendly(Friendly friendly) {
		this.friendly = friendly;
	}
	public Comprador getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Comprador supervisor) {
		this.supervisor = supervisor;
	}
	
}
