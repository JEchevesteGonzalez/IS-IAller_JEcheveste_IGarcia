package tests;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Comprador;
import domain.Cuentas;
import domain.Usuario;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");
	}

	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}
	
	public void crearUsuarioNull(String usuario) {
    	db.getTransaction().begin();
    	Comprador comp = new Comprador(usuario, "prueba");
    	db.persist(comp);
    	db.getTransaction().commit();
	}
	
	public void crearUsuarioCuentas(String usuario, float saldo) {
		db.getTransaction().begin();
    	Comprador comp = new Comprador(usuario, "prueba");
    	db.persist(comp);
		Comprador user = db.find(Comprador.class, usuario);
		Cuentas cu = new Cuentas(1234,saldo,"Banco");
		cu.setComprador(user);
		user.setCuentas(cu);
		db.persist(user);
		db.persist(cu);
		db.getTransaction().commit();
	}
	
	public void eliminarUsuario(String usuario) {
		db.getTransaction().begin();
		
		Usuario user = db.find(Usuario.class, usuario);
		
		if (user != null) {
			
			db.remove(user);
			db.getTransaction().commit(); 
		}
	}
	
}