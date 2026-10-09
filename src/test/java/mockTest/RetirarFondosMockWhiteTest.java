package mockTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import domain.Cuentas;

public class RetirarFondosMockWhiteTest {
	DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	
	@Mock
	protected EntityManager db;
	
	@Mock
	protected EntityTransaction et;
	
	@Before
	public void init() {		
		MockitoAnnotations.openMocks(this);		
		persistenceMock = Mockito.mockStatic(Persistence.class);		
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);		
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();		
		Mockito.doReturn(et).when(db).getTransaction();
		
		// Mockear los métodos de transacción para que no hagan nada
		Mockito.doNothing().when(et).begin();
		Mockito.doNothing().when(et).commit();
		Mockito.doNothing().when(et).rollback();
		
		// Crear un spy para poder mockear el método open()
		sut = Mockito.spy(new DataAccess(db));
		
		// Mockear el método open() para que no intente acceder a la BD real
		Mockito.doNothing().when(sut).open();
	}	
	
	@After		
	public void tearDown()	{		
		persistenceMock.close();					
	}	
	
	
	/*@Test
	public void test1() {
		String usuario = "Yo";
		float cantidadP = 100;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {
			if (!(sut.retirarFondos(usuario, cantidadP))){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}*/
	
	@Test
	public void test2() {
		String usuario = "Yo";
		float cantidadP = 300;
		try {
			if (!(sut.retirarFondos(usuario, cantidadP))){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test3() {
		String usuario = "Yo";
		float cantidadP = 300;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,1000,"Banco");
		cu.setComprador(c1);
		c1.setCuentas(cu);
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {	
			if (sut.retirarFondos(usuario, cantidadP)){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test4() {
		String usuario = "Yo";
		float cantidadP = 300;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,100,"Banco");
		cu.setComprador(c1);
		c1.setCuentas(cu);
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {
			if (sut.retirarFondos(usuario, cantidadP)){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}
}
