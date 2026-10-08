package mockTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
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

public class CrearSolicitudMockWhiteTest {
	private static class TestableDataAccess extends DataAccess {
		int openCalls;
		int closeCalls;

		TestableDataAccess(EntityManager db) {
			super(db);
		}

		@Override
		public void open() {
			openCalls++;
		}

		@Override
		public void close() {
			closeCalls++;
		}
	}

	TestableDataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	
	@Mock protected EntityManagerFactory entityManagerFactory;
	@Mock protected EntityManager db;
	@Mock protected EntityTransaction et;
	
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
		
		// Usar una subclase de prueba para evitar inline mocking incompatible con Java 21
		sut = new TestableDataAccess(db);
	}	
	
	@After		
	public void tearDown() {		
		persistenceMock.close();					
	}	

	private Solicitud ejecutarCrearSolicitud(String usuarioFriendly, Integer saleNumber, Friendly friendly) {
		Mockito.when(db.find(Friendly.class, usuarioFriendly)).thenReturn(friendly);

		sut.crearSolicitud(usuarioFriendly, saleNumber);

		if (friendly != null) {
			ArgumentCaptor<Solicitud> captor = ArgumentCaptor.forClass(Solicitud.class);
			Mockito.verify(db, Mockito.times(1)).persist(captor.capture());
			Mockito.verify(et, Mockito.times(1)).begin();
			Mockito.verify(et, Mockito.times(1)).commit();
			assertEquals(1, sut.openCalls);
			assertEquals(1, sut.closeCalls);
			return captor.getValue();
		}

		Mockito.verify(db, Mockito.times(0)).persist(Mockito.any());
		Mockito.verify(et, Mockito.times(1)).begin();
		Mockito.verify(et, Mockito.times(1)).commit();
		assertEquals(1, sut.openCalls);
		assertEquals(1, sut.closeCalls);
		return null;
	}
	
	@Test
	public void test1() {
		// CASO 1: friendly != null, friendly.solicitudes != null, supervisor != null, supervisor.solicitudes != null
		String usuarioFriendly = "Gorka";
		Integer saleNumber = 99;
		
		Comprador supervisor = new Comprador("Supervisor", "1234");
		ArrayList<Solicitud> listaSupervisor = supervisor.getSolicitudes();
		Friendly friendly = new Friendly(usuarioFriendly, "1234", supervisor);
		ArrayList<Solicitud> listaFriendly = friendly.getSolicitudes();
		
		try {
			Solicitud sGuardada = ejecutarCrearSolicitud(usuarioFriendly, saleNumber, friendly);
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertSame(friendly, sGuardada.getFriendly());
			assertSame(supervisor, sGuardada.getSupervisor());
			assertTrue("La solicitud debe estar en la lista de friendly", listaFriendly.contains(sGuardada));
			assertTrue("La solicitud debe estar en la lista del supervisor", listaSupervisor.contains(sGuardada));
			
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		// CASO 2: friendly != null, friendly.solicitudes != null, supervisor != null, supervisor.solicitudes == null
		String usuarioFriendly = "Gorka";
		Integer saleNumber = 100;
		
		Comprador supervisor = new Comprador("Supervisor", "1234");
		supervisor.setSolicitudes(null);
		Friendly friendly = new Friendly(usuarioFriendly, "1234", supervisor);
		ArrayList<Solicitud> listaFriendly = friendly.getSolicitudes();
		
		try {
			Solicitud sGuardada = ejecutarCrearSolicitud(usuarioFriendly, saleNumber, friendly);
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertSame(friendly, sGuardada.getFriendly());
			assertSame(supervisor, sGuardada.getSupervisor());
			assertTrue("La solicitud debe estar en la lista de friendly", listaFriendly.contains(sGuardada));
			
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		// CASO 3: friendly != null, friendly.solicitudes != null, supervisor == null
		String usuarioFriendly = "Gorka";
		Integer saleNumber = 101;
		
		Friendly friendly = new Friendly(usuarioFriendly, "1234", null);
		ArrayList<Solicitud> listaFriendly = friendly.getSolicitudes();
		
		try {	
			Solicitud sGuardada = ejecutarCrearSolicitud(usuarioFriendly, saleNumber, friendly);
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertSame(friendly, sGuardada.getFriendly());
			assertNull(sGuardada.getSupervisor());
			assertTrue(listaFriendly.contains(sGuardada));
			
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		// CASO 4: friendly != null, friendly.solicitudes == null, supervisor == null
		String usuarioFriendly = "Gorka";
		Integer saleNumber = 102;
		
		Friendly friendly = new Friendly(usuarioFriendly, "1234", null);
		friendly.setSolicitudes(null);
		
		try {
			Solicitud sGuardada = ejecutarCrearSolicitud(usuarioFriendly, saleNumber, friendly);
			assertEquals(saleNumber, sGuardada.getSaleNumber());
			assertEquals("En tramite", sGuardada.getEstado());
			assertSame(friendly, sGuardada.getFriendly());
			assertNull(sGuardada.getSupervisor());
			
		} catch(Exception e) {
			fail("No se esperaba excepción: " + e.getMessage());
		}
	}

	@Test
	public void test5() {
		// CASO 5: friendly == null
		String usuarioFriendly = "Gorka";
		Integer saleNumber = 103;
		
		// Simulamos que la BD devuelve null (no encuentra al usuario)
		
		try {
			Solicitud sGuardada = ejecutarCrearSolicitud(usuarioFriendly, saleNumber, null);
			assertNull(sGuardada);
			// Como friendly es null, NUNCA se debe llamar a db.persist()
			Mockito.verify(db, Mockito.times(0)).persist(Mockito.any());
			
		} catch(Exception e) {
			fail("El flujo no debería lanzar excepción, solo terminar silenciosamente.");
		}
	}
}