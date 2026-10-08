package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.*;


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

    public void addFriendly(String username, String password, Comprador supervisor, ArrayList<Solicitud> solicitudes) {
    	if (supervisor != null) {
            if (supervisor.getDependientes() == null) {
                supervisor.setDependientes(new ArrayList<Friendly>());
            }
            db.persist(supervisor);
        }
    	
        db.getTransaction().begin();
        Friendly f = new Friendly(username, password, supervisor);
        f.setSupervisor(supervisor);
        f.setSolicitudes(solicitudes);
        
        if (supervisor != null) {
            supervisor.getDependientes().add(f);
        }
        
        db.persist(f);
        db.getTransaction().commit();
    }

	public Friendly getFriendly(String username) {
		return db.find(Friendly.class, username);
	}

    public void removeFriendly(String username) {
		db.getTransaction().begin();
		Friendly f = db.find(Friendly.class, username);
		if (f != null) {
			db.remove(f);
		}
		db.getTransaction().commit();
    }


	public Comprador getComprador(String supervisor) {
		return db.find(Comprador.class, supervisor);
	}

	public void removeComprador(String supervisor) {
		db.getTransaction().begin();
		Comprador c = db.find(Comprador.class, supervisor);
		if (c != null) {
			db.remove(c);
		}
		db.getTransaction().commit();
	}

	public int contarSolicitudes(String username) {
		Friendly f = db.find(Friendly.class, username);
		if (f != null && f.getSolicitudes() != null) {
			return f.getSolicitudes().size();
		}
		return 0;
	}

	public Solicitud getSolicitud(Integer id) {
		return db.find(Solicitud.class, id);
	}

	public void removeSolicitud(Integer id) {
		db.getTransaction().begin();
		Solicitud s = db.find(Solicitud.class, id);
		if (s != null) {
			db.remove(s);
		}
		db.getTransaction().commit();
	}
}