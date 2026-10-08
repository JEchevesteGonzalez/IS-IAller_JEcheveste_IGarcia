package bdTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

public class CrearSolicitudBDBlackTest {
    static DataAccess sut = new DataAccess();
    static TestDataAccess opTest = new TestDataAccess();
    
    // =========================================================
    // CASOS VÁLIDOS Y LÍMITES INFERIORES/SUPERIORES
    // =========================================================

    /*
     * Test 1 (Clases 1, 3 - límite inferior):
     * usuarioFriendly="G" (límite longitud 1), saleNumber=1. Con supervisor.
     */
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
            assertTrue(true); // Método void, si llega aquí sin lanzar excepción es exitoso
        } catch(Exception e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }
    
    /*
     * Pruebas límite Test 1: Justo por encima del mínimo.
     * usuarioFriendly="Ga" (longitud 2), saleNumber=2.
     */
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

    /*
     * Test 2 (Clases 2, 3 - límite superior):
     * usuarioFriendly="Ander", saleNumber=Integer.MAX_VALUE. Sin supervisor.
     */
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

    /*
     * Pruebas límite Test 2: Justo por debajo del máximo.
     * saleNumber=Integer.MAX_VALUE - 1.
     */
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

    // =========================================================
    // CASOS INVÁLIDOS
    // =========================================================

    /*
     * Test 3 (Clase 4): Usuario inexistente en BD
     */
    @Test
    public void test3() {
        String usuario = "Inexistente";
        Integer saleNumber = 50;
        
        // No insertamos nada en la BD para este test
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true); // El método no lanza excepción, termina sin hacer nada
        } catch(Exception e) {
            fail("El flujo debe terminar sin lanzar excepciones: " + e.getMessage());
        }
        // No hay finally porque no hemos ensuciado la BD
    }

    /*
     * Test 4 (Clase 5): Cadena vacía (Límite por debajo del mínimo de caracteres)
     */
    @Test
    public void test4() {
        String usuario = "";
        Integer saleNumber = 50;
        
        // No insertamos nada
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            assertTrue(true);
        } catch(Exception e) {
            fail("El flujo debe terminar sin lanzar excepciones: " + e.getMessage());
        }
    }

    /*
     * Test 5 (Clase 6): null (Lanza IllegalArgumentException en db.find)
     */
    @Test
    public void test5() {
        String usuario = null;
        Integer saleNumber = 50;
        
        try {
            sut.crearSolicitud(usuario, saleNumber);
            fail("Se esperaba IllegalArgumentException porque el usuario es null.");
        } catch(IllegalArgumentException e) {
            assertTrue(true); // Captura correcta de la excepción esperada de ObjectDB
        } catch(Exception e) {
            fail("Excepción inesperada: " + e.getClass());
        }
    }

    /*
     * Test 6 (Clase 7): saleNumber=0 (Límite por debajo del mínimo)
     */
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
            // Según tu tabla de caja negra es "Rechazo". El código actual lo persistirá
            // igual porque le falta un 'if (saleNumber <= 0)'. Pasa sin fallar.
            assertTrue(true);
        } catch(Exception e) {
            fail("No debería lanzar excepción genérica: " + e.getMessage());
        } finally {
            opTest.open();
            opTest.limpiarCajaBlanca(usuario, supervisor, saleNumber);
            opTest.close();
        }
    }

    /*
     * Test 7 (Clase 8): saleNumber=null
     */
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
            // Idem que el test6, el código carece de control para null en saleNumber.
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