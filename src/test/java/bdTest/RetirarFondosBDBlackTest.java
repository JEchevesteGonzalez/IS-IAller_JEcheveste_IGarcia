package bdTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

public class RetirarFondosBDBlackTest {
	static DataAccess sut = new DataAccess();
	static TestDataAccess opTest = new TestDataAccess();
	
	@Test
	public void test1() {
		String usuario = "Usuario";
		float cantidadP = 500;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 300);
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
	
	//Pruebas limite test1
	@Test
	public void test2() {
		String usuario = "Usuario";
		float cantidadP = 301;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 300);
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
	public void test3() {
		String usuario = "Usuario";
		float cantidadP = 300;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 300);
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
		String usuario = "Usuario";
		float cantidadP = 299;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 300);
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
	//Fin pruebas limite test1
	
	@Test
	public void test5() {
		String usuario = "Usuario";
		float cantidadP = -100;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 200);
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
	
	//Pruebas limite test5
	@Test
	public void test6() {
		String usuario = "Usuario";
		float cantidadP = 1;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 200);
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
	public void test7() {
		String usuario = "Usuario";
		float cantidadP = 0;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 200);
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
	public void test8() {
		String usuario = "Usuario";
		float cantidadP = -1;
		opTest.open();
		opTest.crearUsuarioCuentas(usuario, 200);
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
	//Fin pruebas limite test5
	
	@Test
	public void test9() {
		String usuario = "Usuario";
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
