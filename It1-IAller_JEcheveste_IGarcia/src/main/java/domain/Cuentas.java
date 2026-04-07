package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Cuentas {
	private int numeroCuenta;
	private float saldo;
	private String nombreBanco;
	
	@OneToOne
	private Comprador c;
	
	public Cuentas(int numeroCuenta, float saldo, String nombreBanco) {
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.nombreBanco = nombreBanco;
	}

	public Cuentas() {
		this.numeroCuenta=0;
		this.saldo = 0;
		this.nombreBanco = "";
	}
	
	public int getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	
	public Comprador getComprador() {
		return c;
	}
	
	public void setComprador(Comprador c) {
		this.c = c;
	}
}
