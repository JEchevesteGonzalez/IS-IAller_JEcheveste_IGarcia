package bdTest;

import domain.Comprador;
import domain.Friendly;
import domain.Solicitud;
import org.junit.Test;

import dataAccess.DataAccess;
import tests.TestDataAccess;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EliminarFriendlyAsignadoBDWhiteTest {
    static DataAccess sut = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();

    @Test
    public void test1() {
        //Usuario no existe en BD
        String usuarioFriendly = "Jon";

        try {
            //sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            //sut.close();

            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test2() {
        //Usuario sin supervisor y solicitudes null
        String usuarioFriendly = "Jon";

        testDA.open();
        testDA.addFriendly(usuarioFriendly, "Prueba", null, null);
        testDA.close();

        try {
            //sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            //sut.close();

            testDA.open();
            Friendly f = testDA.getFriendly(usuarioFriendly);
            testDA.close();

            assertNull(f);
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            if (testDA.getFriendly(usuarioFriendly) != null) {
                testDA.removeFriendly(usuarioFriendly);
            }
            testDA.close();
        }

    }

    @Test
    public void test3() {
        //Usuario con supervisor y solicitudes null
        String usuarioFriendly = "Jon";
        String supervisor = "Supervisor";

        testDA.open();
        testDA.addFriendly(usuarioFriendly, "Prueba", new Comprador(supervisor, "Prueba"), null);
        testDA.close();

        try {
            //sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            //sut.close();

            testDA.open();
            Friendly f = testDA.getFriendly(usuarioFriendly);
            Comprador c = testDA.getComprador(supervisor);
            testDA.close();

            assertNull(f);
            assertTrue(c.getDependientes() == null || !c.getDependientes().contains(f));
        } catch (Exception e) {
        	fail();
        } finally {
            testDA.open();
            if (testDA.getFriendly(usuarioFriendly) != null) {
                testDA.removeFriendly(usuarioFriendly);
            }
            if (testDA.getComprador(supervisor) != null) {
                testDA.removeComprador(supervisor);
            }
            testDA.close();
        }
    }

    @Test
    public void test4() {
        //Usuario con supervisor y lista de solicitudes vacia
        String usuarioFriendly = "Jon";
        String supervisor = "Supervisor";

        testDA.open();
        testDA.addFriendly(usuarioFriendly, "Prueba", new Comprador(supervisor, "Prueba"), new ArrayList<Solicitud>());
        testDA.close();

        try {
            //sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            //sut.close();

            testDA.open();
            Friendly f = testDA.getFriendly(usuarioFriendly);
            Comprador c = testDA.getComprador(supervisor);
            testDA.close();

            assertNull(f);
            assertTrue(c.getDependientes() == null || !c.getDependientes().contains(f));
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            if (testDA.getFriendly(usuarioFriendly) != null) {
                testDA.removeFriendly(usuarioFriendly);
            }
            if (testDA.getComprador(supervisor) != null) {
                testDA.removeComprador(supervisor);
            }
            testDA.close();
        }
    }

    @Test
    public void test5() {
        //Usuario con supervisor y lista de solicitudes
        String usuarioFriendly = "Jon";
        String supervisor = "Super";
        ArrayList<Solicitud> listaSolicitudes = new ArrayList<Solicitud>();
        Solicitud s = new Solicitud(1, "estado", null, null);
        listaSolicitudes.add(s);

        testDA.open();
        testDA.addFriendly(usuarioFriendly, "Prueba", new Comprador(supervisor, "Prueba"), listaSolicitudes);
        testDA.close();

        try {
            //sut.open();
            sut.eliminarFriendlyAsignado(usuarioFriendly);
            //sut.close();

            testDA.open();
            Friendly f = testDA.getFriendly(usuarioFriendly);
            Comprador c = testDA.getComprador(supervisor);
            Solicitud solicitudEliminada = testDA.getSolicitud(s.getSaleNumber());
            testDA.close();

            assertNull(f);
            assertNull(solicitudEliminada);
            assertTrue(c.getDependientes() == null || !c.getDependientes().contains(f));
        } catch (Exception e) {
            fail();
        } finally {
            testDA.open();
            if (testDA.getFriendly(usuarioFriendly) != null) {
                testDA.removeFriendly(usuarioFriendly);
            }
            if (testDA.getComprador(supervisor) != null) {
                testDA.removeComprador(supervisor);
            }
            for (Solicitud sol : listaSolicitudes) {
                if (testDA.getSolicitud(sol.getSaleNumber()) != null) {
                    testDA.removeSolicitud(sol.getSaleNumber());
                }
            }
            testDA.close();
        }
    }
}
