package bdTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

public class RetirarFondosBDWhiteTest {
	static DataAccess sut = new DataAccess();
	static TestDataAccess opTest = new TestDataAccess();
	
	@Test
	public void test1() {
		String usuario = "Yo";
		float cantidadP = 100;
		opTest.open();
		opTest.crearUsuarioNull(usuario);
		opTest.close();
		try {
			if (!(sut.retirarFondos(usuario, cantidadP))){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}finally {
			opTest.open();
			opTest.eliminarUsuario(usuario);
			opTest.close();
		}
	}
	
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
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 1000);
		opTest.close();
		try {	
			if (sut.retirarFondos(usuario, cantidadP)){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}finally {
			opTest.open();
			opTest.eliminarUsuario(usuario);
			opTest.close();
		}
	}
	
	@Test
	public void test4() {
		String usuario = "Yo";
		float cantidadP = 300;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 100);
		opTest.close();
		try {
			if (sut.retirarFondos(usuario, cantidadP)){
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}finally {
			opTest.open();
			opTest.eliminarUsuario(usuario);
			opTest.close();
		}
	}
}
