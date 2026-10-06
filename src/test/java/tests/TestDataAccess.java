package tests;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Comprador;


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

	public boolean retirarFondos(String usuario, float cantidad) {
		//Creamos una transaccion de la misma
		db.getTransaction().begin();
		try {
			//Comprobamos si el usuario que nos pasan existe en la base de datos
			Comprador user = db.find(Comprador.class, usuario);
			
			//Si el usuario existe, continua la transaccion
			if (user != null) {
				//Se calcula el que sera el nuevo saldo del usuario restandole la cantidad a pagar
				float nuevoSaldo = user.getSaldo() - cantidad;
				//Si la cantidad es mayor al saldo que tenia el usuario, se le deja el saldo en 0
				if (nuevoSaldo<0) {
					nuevoSaldo=0;
				}
				//Se le pone el nuevo saldo al usuario
				user.setSaldo(nuevoSaldo);
				//Añadimos los datos a la base de datos
				db.getTransaction().commit();
				//La transaccion finaliza correctamente
				return true;
			//Si el usuario no existe, no se completa la transaccion
			} else {
				return false;
			}
		//En el caso de elevarse alguna excepcion, tampoco se completa la transaccion
		} catch (Exception e) {
			return false;
		//Finalmente, cerramos la base de datos independientemente de haberse finalizado la transaccion
		}
	}
	
	public void crearUsuarioNull(String usuario) {
    	open();
    	db.getTransaction().begin();
    	Comprador comp = new Comprador(usuario, "prueba");
    	db.persist(comp);
    	db.getTransaction().commit();
    	close();
	}
	
}