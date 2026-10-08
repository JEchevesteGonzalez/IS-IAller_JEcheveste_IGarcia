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
import tests.TestDataAccess;

public class RetirarFondosMockBlackTest {
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
		String usuario = "Usuario";
		float cantidadP = 500;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,300,"Banco");
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
	
	//Pruebas limite test1
	@Test
	public void test2() {
		String usuario = "Usuario";
		float cantidadP = 301;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,300,"Banco");
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
	public void test3() {
		String usuario = "Usuario";
		float cantidadP = 300;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,300,"Banco");
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
		String usuario = "Usuario";
		float cantidadP = 299;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,300,"Banco");
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
	//Fin pruebas limite test1
	
	@Test
	public void test5() {
		String usuario = "Usuario";
		float cantidadP = -100;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,200,"Banco");
		cu.setComprador(c1);
		c1.setCuentas(cu);
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {
			if (sut.retirarFondos(usuario, cantidadP)){
				fail();
			}else {
				assertTrue(true);
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	//Pruebas limite test5
	@Test
	public void test6() {
		String usuario = "Usuario";
		float cantidadP = 1;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,200,"Banco");
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
	public void test7() {
		String usuario = "Usuario";
		float cantidadP = 0;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,200,"Banco");
		cu.setComprador(c1);
		c1.setCuentas(cu);
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {
			if (sut.retirarFondos(usuario, cantidadP)){
				fail();
			}else {
				assertTrue(true);
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test8() {
		String usuario = "Usuario";
		float cantidadP = -1;
		Comprador c1 = new Comprador(usuario, "Prueba");
		Cuentas cu = new Cuentas(1234,200,"Banco");
		cu.setComprador(c1);
		c1.setCuentas(cu);
		Mockito.when(db.find(Comprador.class, usuario)).thenReturn(c1);
		try {
			if (sut.retirarFondos(usuario, cantidadP)){
				fail();
			}else {
				assertTrue(true);
			}
		}catch(Exception e) {
			fail();
		}
	}
	//Fin pruebas limite test5
	
	@Test
	public void test9() {
		String usuario = "Usuario";
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
	}
	
	@Test
	public void test10() {
		String usuario = "Usuario";
		float cantidadP = 100;
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
}
