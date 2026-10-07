package mockTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Comprador;
import domain.Friendly;
import domain.Solicitud;

public class EliminarFriendlyAsignadoMockBlackTest {
	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	
	@Mock
	protected EntityManager db;
	
	@Mock
	protected EntityTransaction et;
	
	@Before
	public void init()	{		
		MockitoAnnotations.openMocks(this);		
		persistenceMock	=	Mockito.mockStatic(Persistence.class);		
		persistenceMock.when(()	->	
		Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);		
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();		
		Mockito.doReturn(et).when(db).getTransaction();		
		sut=new	DataAccess(db);					
	}	
	
	@After		
	public void tearDown()	{		
		persistenceMock.close();					
	}	

	@Test
	public void test1() {
		//Usuario no existe en BD
		String usuarioFriendly = "Jon";
        Mockito.when(db.find(Friendly.class, usuarioFriendly)).thenReturn(null);
		try {
			sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            sut.close();
			
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).rollback();
			Mockito.verify(et, Mockito.never()).commit();
			Mockito.verify(db, Mockito.never()).remove(Mockito.any());
			
		}catch(Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void test2() {
		//Usuario null
		
	    String usuarioFriendly = null;
        
	    try {
			sut.open();
			sut.eliminarFriendlyAsignado(usuarioFriendly);
			sut.close();
			
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).rollback();
			Mockito.verify(et, Mockito.never()).commit();
			Mockito.verify(db, Mockito.never()).remove(Mockito.any());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test3() {
		//Usuario con supervisor y solicitudes null
	    String usuarioFriendly = "";
        
	    try {
			sut.open();
			sut.eliminarFriendlyAsignado(usuarioFriendly);
			sut.close();
			
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).rollback();
			Mockito.verify(et, Mockito.never()).commit();
			Mockito.verify(db, Mockito.never()).remove(Mockito.any());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test4() {
		//Usuario con supervisor y lista de solicitudes vacia
		String usuarioFriendly = "Jon";
		
		
		Friendly f = new Friendly(usuarioFriendly, usuarioFriendly, null);
		Comprador supervisor = new Comprador("Comprador", "contraseña");
		f.setSupervisor(null);
		f.setSolicitudes(new ArrayList<Solicitud>());

	    Mockito.when(db.find(Friendly.class, usuarioFriendly)).thenReturn(f);
        
        try {
        	sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            sut.close();
            
            Mockito.verify(et, Mockito.times(1)).begin();
            Mockito.verify(db, Mockito.times(1)).remove(f);
            Mockito.verify(db, Mockito.times(1)).remove(Mockito.any());
            Mockito.verify(et, Mockito.times(1)).commit();
            Mockito.verify(et, Mockito.never()).rollback();
        }catch(Exception e) {
        	fail();
        }
	}
	
	
	
	@Test
	public void test5() {
		//Usuario con supervisor y lista de solicitudes 
		String usuarioFriendly = "Jon";
		
		
		Friendly f = new Friendly(usuarioFriendly, usuarioFriendly, null);
		Comprador supervisor = new Comprador("Comprador", "contraseña");
		List<Friendly> dependientes = new ArrayList<Friendly>();
		dependientes.add(f);
		supervisor.setDependientes(dependientes);
		
		f.setSupervisor(supervisor);
		ArrayList<Solicitud> listaSolicitudes = new ArrayList<Solicitud>();
		Solicitud s = new Solicitud(1, "estado", null, null);
		listaSolicitudes.add(s);
	    f.setSolicitudes(listaSolicitudes);

	    Mockito.when(db.find(Friendly.class, usuarioFriendly)).thenReturn(f);
        
        try {
        	sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            sut.close();
            
            Mockito.verify(et, Mockito.times(1)).begin();
            Mockito.verify(db, Mockito.times(1)).remove(f);
            Mockito.verify(db, Mockito.times(1)).remove(s);
            Mockito.verify(db, Mockito.times(2)).remove(Mockito.any());
            Mockito.verify(et, Mockito.times(1)).commit();
            Mockito.verify(et, Mockito.never()).rollback();
        }catch(Exception e) {
        	fail();
        }
	}
}
