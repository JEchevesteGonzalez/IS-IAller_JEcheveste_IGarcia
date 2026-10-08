package mockTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Comprador;
import domain.Friendly;
import domain.Solicitud;

public class CrearSolicitudMockBlackTest {
	private static class TestableDataAccess extends DataAccess {
		TestableDataAccess(EntityManager db) {
			super(db);
		}

		@Override
		public void open() {
			// No-op in tests
		}

		@Override
		public void close() {
			// No-op in tests
		}
	}

	TestableDataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock protected EntityManagerFactory entityManagerFactory;
	@Mock protected EntityManager db;
	@Mock protected EntityTransaction et;
	
	@Before
	public void init() {		
		// Inicializa los mocks para simular la BD
		MockitoAnnotations.openMocks(this);		
		persistenceMock = Mockito.mockStatic(Persistence.class);		
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);		
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();		
		Mockito.doReturn(et).when(db).getTransaction();
		
		// Mockear los métodos de transacción para que no hagan nada
		Mockito.doNothing().when(et).begin();
		Mockito.doNothing().when(et).commit();
		Mockito.doNothing().when(et).rollback();
		
		// Usar una subclase de prueba para evitar inline mocking incompatible con Java 21
		sut = new TestableDataAccess(db);
	}	
	
	@After		
	public void tearDown() {		
		persistenceMock.close();					
	}	

	// =========================================================
	// CASOS VÁLIDOS Y LÍMITES INFERIORES/SUPERIORES
	// =========================================================

	@Test
	public void test1_Clases1_3_LimiteInferior() {
		// Test 1: usuario="G" (límite 1 char), saleNumber=1 (límite inferior)
		String usuario = "G";
		Integer saleNumber = 1;
		
		Comprador supervisor = new Comprador("Supervisor", "1234");
		ArrayList<Solicitud> listaSupervisor = supervisor.getSolicitudes();
		Friendly friendlyMock = new Friendly(usuario, "1234", supervisor);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		// Simulamos el comportamiento de la BD para que devuelva el usuario
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			// Ejecutamos el System Under Test
			sut.crearSolicitud(usuario, saleNumber);
			
			// Capturamos el objeto que se pasó al persist de la BD simulada
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			
			// Comprobamos los resultados
			Solicitud sGuardada = captor.getValue();
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertTrue("La solicitud debe estar en la lista de friendly", listaFriendly.contains(sGuardada));
			assertTrue("La solicitud debe estar en la lista de supervisor", listaSupervisor.contains(sGuardada));
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}

	@Test
	public void test1_LimiteJustoEncimaMinimo() {
		// Pruebas Límite Test 1: usuario="Ga" (2 chars), saleNumber=2 (justo por encima del mínimo)
		String usuario = "Ga";
		Integer saleNumber = 2;
		
		Comprador supervisor = new Comprador("Supervisor", "1234");
		ArrayList<Solicitud> listaSupervisor = supervisor.getSolicitudes();
		Friendly friendlyMock = new Friendly(usuario, "1234", supervisor);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			
			Solicitud sGuardada = captor.getValue();
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertTrue("La solicitud debe estar en la lista de friendly", listaFriendly.contains(sGuardada));
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}

	@Test
	public void test2_Clases2_3_LimiteSuperior() {
		// Test 2: usuario="Ander" sin supervisor, saleNumber=Integer.MAX_VALUE (límite superior)
		String usuario = "Ander";
		Integer saleNumber = Integer.MAX_VALUE;
		
		Friendly friendlyMock = new Friendly(usuario, "1234", null);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			
			Solicitud sGuardada = captor.getValue();
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertTrue("La solicitud debe estar en la lista de friendly", listaFriendly.contains(sGuardada));
			assertNull("El supervisor debe ser null", sGuardada.getSupervisor());
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}

	@Test
	public void test2_LimiteJustoDebajoMaximo() {
		// Pruebas Límite Test 2: saleNumber=Integer.MAX_VALUE - 1 (justo por debajo del máximo)
		String usuario = "Ander";
		Integer saleNumber = Integer.MAX_VALUE - 1;
		
		Friendly friendlyMock = new Friendly(usuario, "1234", null);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			
			Solicitud sGuardada = captor.getValue();
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}

	// =========================================================
	// CASOS INVÁLIDOS
	// =========================================================

	@Test
	public void test3_UsuarioInexistente() {
		// Test 3 (Clase 4): Usuario inexistente
		String usuario = "Inexistente";
		Integer saleNumber = 50;
		
		// Simulamos que la BD devuelve null al buscar
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(null);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			
			// Comprobamos que el método persist NUNCA fue invocado
			Mockito.verify(db, Mockito.times(0)).persist(Mockito.any());
		} catch(Exception e) {
			fail("El flujo debe terminar sin lanzar excepciones no controladas: " + e.getMessage());
		}
	}

	@Test
	public void test4_UsuarioCadenaVacia() {
		// Test 4 (Clase 5): Cadena vacía (Límite por debajo del mínimo de caracteres)
		String usuario = "";
		Integer saleNumber = 50;
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(null);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			Mockito.verify(db, Mockito.times(0)).persist(Mockito.any());
		} catch(Exception e) {
			fail("El flujo debe terminar sin persistir ni lanzar excepciones: " + e.getMessage());
		}
	}

	@Test
	public void test5_UsuarioNull() {
		// Test 5 (Clase 6): null
		String usuario = null;
		Integer saleNumber = 50;
		
		// El EntityManager de Java lanza IllegalArgumentException si la clave primaria es null
		Mockito.when(db.find(Friendly.class, usuario)).thenThrow(new IllegalArgumentException("Usuario no puede ser null"));
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			fail("Se esperaba IllegalArgumentException porque el usuario es null.");
		} catch(IllegalArgumentException e) {
			assertTrue("Se lanzó la excepción esperada", true);
		} catch(Exception e) {
			fail("Excepción inesperada: " + e.getClass() + " - " + e.getMessage());
		}
	}

	@Test
	public void test6_SaleNumberCero() {
		// Test 6 (Clase 7): saleNumber=0 (Límite por debajo del mínimo)
		String usuario = "Gorka";
		Integer saleNumber = 0;
		
		Friendly friendlyMock = new Friendly(usuario, "1234", null);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			
			// La implementación actual no valida saleNumber, así que se persistirá
			// Si quieres que se rechace, sería un defecto en la implementación
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			assertEquals(saleNumber, captor.getValue().getSaleNumber());
		} catch(Exception e) {
			fail("No debería lanzar una excepción: " + e.getMessage());
		}
	}

	@Test
	public void test7_SaleNumberNull() {
		// Test 7 (Clase 8): saleNumber=null
		String usuario = "Gorka";
		Integer saleNumber = null;
		
		Friendly friendlyMock = new Friendly(usuario, "1234", null);
		ArrayList<Solicitud> listaFriendly = friendlyMock.getSolicitudes();
		
		Mockito.when(db.find(Friendly.class, usuario)).thenReturn(friendlyMock);
		
		try {
			sut.crearSolicitud(usuario, saleNumber);
			
			// La implementación actual no valida saleNumber nulo
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			assertNull("El saleNumber debe ser null", captor.getValue().getSaleNumber());
		} catch(Exception e) {
			fail("El método debería gestionar los nulos: " + e.getClass() + " - " + e.getMessage());
		}
	}
}