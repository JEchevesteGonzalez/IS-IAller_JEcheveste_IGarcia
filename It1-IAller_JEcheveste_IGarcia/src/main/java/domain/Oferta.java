package domain;

import javax.persistence.Entity;

@Entity
public class Oferta {
	private Sale s;
	private Comprador c;
	
	public Oferta(Sale s, Comprador c) {
		this.s = s;
		this.c = c;
	}
	
}
