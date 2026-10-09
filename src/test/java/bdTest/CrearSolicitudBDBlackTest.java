package bdTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

public class CrearSolicitudBDBlackTest {
    static DataAccess sut = new DataAccess();
    static TestDataAccess opTest = new TestDataAccess();
    
    @Test
    public void test1() {
        String usuario = "G";
        Integer saleNumber = 1;
        String supervisor = "Sup1";
        
        opTest.open();
        opTest.crearFriendlyConSupervisor(usuario, supervisor, true, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true); 
        } catch(Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }
    
    @Test
    public void test1_LimiteJustoEncimaMinimo() {
        String usuario = "Ga";
        Integer saleNumber = 2;
        String supervisor = "Sup2";
        
        opTest.open();
        opTest.crearFriendlyConSupervisor(usuario, supervisor, true, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }

    @Test
    public void test2() {
        String usuario = "Ander";
        Integer saleNumber = Integer.MAX_VALUE;
        
        opTest.open();
        opTest.crearFriendlySinSupervisor(usuario, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, null, saleNumber);
            opTest.close();
        }
    }

    //Test 2: Justo por debajo del máximo.
     
    @Test
    public void test2_LimiteJustoDebajoMaximo() {
        String usuario = "Ander";
        Integer saleNumber = Integer.MAX_VALUE - 1;
        
        opTest.open();
        opTest.crearFriendlySinSupervisor(usuario, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, null, saleNumber);
            opTest.close();
        }
    }

    
     //Test 3: Usuario inexistente en BD
    
    @Test
    public void test3() {
        String usuario = "Inexistente";
        Integer saleNumber = 50;
        
        // No insertamos nada en la BD para este test
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true); 
        } catch(Exception e) {
            fail("El flujo debe terminar sin lanzar excepciones: " + e.getMessage());
        }
        // No hay finally porque no hemos ensuciado la BD
    }

    // Test 4:Cadena vacía 
     
    @Test
    public void test4() {
        String usuario = "";
        Integer saleNumber = 50;
        
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("El flujo debe terminar sin lanzar excepciones: " + e.getMessage());
        }
    }

     // Test 5: null (Lanza IllegalArgumentException en db.find)
     
    @Test
    public void test5() {
        String usuario = null;
        Integer saleNumber = 50;
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            fail("Se esperaba IllegalArgumentException porque el usuario es null.");
        } catch(IllegalArgumentException e) {
            assertTrue(true); 
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getClass());
        }
    }

    
     // Test 6: saleNumber=0 (Límite por debajo del mínimo)
     
    @Test
    public void test6() {
        String usuario = "Gorka";
        Integer saleNumber = 0;
        String supervisor = "Sup3";
        
        opTest.open();
        opTest.crearFriendlyConSupervisor(usuario, supervisor, true, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("No debería lanzar excepción genérica: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }

    
     // Test 7: saleNumber=null
     
    @Test
    public void test7() {
        String usuario = "Gorka";
        Integer saleNumber = null;
        String supervisor = "Sup4";
        
        opTest.open();
        opTest.crearFriendlyConSupervisor(usuario, supervisor, true, true);
        opTest.close();
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("El método debería gestionar los nulos sin fallar: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }
}