package bdTest;

import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

public class RetirarFondosBDWhiteTest {
	static DataAccess sut = new DataAccess();
	static TestDataAccess opTest = new TestDataAccess();
	
	@Test
	//TERMINAR
	public void test1() {
		String usuario = "Yo";
		float cantidadP = 100;
		opTest.open();
		opTest.crearUsuarioNull(usuario);
		opTest.close();
		sut.open();
		sut.retirarFondos(usuario, cantidadP);
		sut.close();
	}
}
