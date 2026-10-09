package bdTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Comprador;
import domain.Friendly;
import domain.Solicitud;
import tests.TestDataAccess;

public class CrearSolicitudBDWhiteTest extends TestDataAccess {
    private static final DataAccess sut = new DataAccess();

    private void limpiarCajaBlanca(String usuario, String supervisor) {
        open();
        try {
            db.getTransaction().begin();

            Friendly friendly = db.find(Friendly.class, usuario);
            if (friendly != null) {
                db.remove(friendly);
            }

            if (supervisor != null) {
                Comprador comprador = db.find(Comprador.class, supervisor);
                if (comprador != null) {
                    db.remove(comprador);
                }
            }

            db.getTransaction().commit();
        } finally {
            close();
        }
    }

    private void setupCajaBlanca(String usuario, boolean friendlySolicitudes, String supervisor,
            boolean supervisorSolicitudes) {
        limpiarCajaBlanca(usuario, supervisor);

        open();
        try {
            db.getTransaction().begin();

            Comprador supervisorEntity = null;
            if (supervisor != null) {
                supervisorEntity = new Comprador(supervisor, "prueba");
                if (!supervisorSolicitudes) {
                    supervisorEntity.setSolicitudes(null);
                }
                db.persist(supervisorEntity);
            }

            Friendly friendly = new Friendly(usuario, "prueba", supervisorEntity);
            if (!friendlySolicitudes) {
                friendly.setSolicitudes(null);
            }
            db.persist(friendly);

            db.getTransaction().commit();
        } finally {
            close();
        }
    }

    private Solicitud buscarSolicitudEnLista(ArrayList<Solicitud> solicitudes, Integer saleNumber) {
        if (solicitudes == null) {
            return null;
        }

        for (Solicitud solicitud : solicitudes) {
            if (saleNumber.equals(solicitud.getSaleNumber())) {
                return solicitud;
            }
        }

        return null;
    }

    private Friendly recargarFriendly(String usuario) {
        open();
        try {
            return db.find(Friendly.class, usuario);
        } finally {
            close();
        }
    }

    private Comprador recargarComprador(String usuario) {
        open();
        try {
            return db.find(Comprador.class, usuario);
        } finally {
            close();
        }
    }

    /*
     * TEST 1
     * Camino: if9(true) - if24(true) - 25 - if29.1(true) - if29.2(true) - 30 - 34
     */
    @Test
    public void test1() {
        String usuario = "GorkaTest1";
        Integer saleNumber = 99;
        String supervisor = "SupGorka1";

        try {
            setupCajaBlanca(usuario, true, supervisor, true);

            sut.crearSolicitud(usuario, saleNumber);

            Friendly friendly = recargarFriendly(usuario);
            assertTrue(friendly != null);
            assertTrue(friendly.getSolicitudes() != null);

            Solicitud solicitud = buscarSolicitudEnLista(friendly.getSolicitudes(), saleNumber);
            assertTrue(solicitud != null);
            assertEquals(saleNumber, solicitud.getSaleNumber());
            assertEquals("En tramite", solicitud.getEstado());
            assertEquals(usuario, solicitud.getFriendly().getNombreUsuario());
            assertEquals(supervisor, solicitud.getSupervisor().getNombreUsuario());
            assertTrue(friendly.getSolicitudes().contains(solicitud));
            assertTrue(buscarSolicitudEnLista(recargarComprador(supervisor).getSolicitudes(), saleNumber) != null);
        } catch (Exception e) {
            fail("No debería fallar: " + e.getMessage());
        } finally {
            limpiarCajaBlanca(usuario, supervisor);
        }
    }

    /*
     * TEST 2
     * Camino: if9(true) - if24(true) - 25 - if29.1(true) - if29.2(false) - 34
     */
    @Test
    public void test2() {
        String usuario = "GorkaTest2";
        Integer saleNumber = 100;
        String supervisor = "SupGorka2";

        try {
            setupCajaBlanca(usuario, true, supervisor, false);

            sut.crearSolicitud(usuario, saleNumber);

            Friendly friendly = recargarFriendly(usuario);
            assertTrue(friendly != null);
            assertTrue(friendly.getSolicitudes() != null);

            Solicitud solicitud = buscarSolicitudEnLista(friendly.getSolicitudes(), saleNumber);
            assertTrue(solicitud != null);
            assertEquals(saleNumber, solicitud.getSaleNumber());
            assertEquals("En tramite", solicitud.getEstado());
            assertEquals(usuario, solicitud.getFriendly().getNombreUsuario());
            assertEquals(supervisor, solicitud.getSupervisor().getNombreUsuario());
            assertTrue(friendly.getSolicitudes().contains(solicitud));

            Comprador supervisorRecargado = recargarComprador(supervisor);
            assertTrue(supervisorRecargado.getSolicitudes() == null);
        } catch (Exception e) {
            fail("No debería fallar: " + e.getMessage());
        } finally {
            limpiarCajaBlanca(usuario, supervisor);
        }
    }

    /*
     * TEST 3
     * Camino: if9(true) - if24(true) - 25 - if29.1(false) - 34
     */
    @Test
    public void test3() {
        String usuario = "GorkaTest3";
        Integer saleNumber = 101;

        try {
            setupCajaBlanca(usuario, true, null, false);

            sut.crearSolicitud(usuario, saleNumber);

            Friendly friendly = recargarFriendly(usuario);
            assertTrue(friendly != null);
            assertTrue(friendly.getSolicitudes() != null);

            Solicitud solicitud = buscarSolicitudEnLista(friendly.getSolicitudes(), saleNumber);
            assertTrue(solicitud != null);
            assertEquals(saleNumber, solicitud.getSaleNumber());
            assertEquals("En tramite", solicitud.getEstado());
            assertEquals(usuario, solicitud.getFriendly().getNombreUsuario());
            assertNull(solicitud.getSupervisor());
        } catch (Exception e) {
            fail("No debería fallar: " + e.getMessage());
        } finally {
            limpiarCajaBlanca(usuario, null);
        }
    }

    /*
     * TEST 4
     * Camino: if9(true) - if24(false) - if29.1(false) - 34
     */
    @Test
    public void test4() {
        String usuario = "GorkaTest4";
        Integer saleNumber = 102;

        try {
            setupCajaBlanca(usuario, false, null, false);

            sut.crearSolicitud(usuario, saleNumber);

            Friendly friendly = recargarFriendly(usuario);
            assertTrue(friendly != null);
            assertNull(friendly.getSolicitudes());
        } catch (Exception e) {
            fail("No debería fallar: " + e.getMessage());
        } finally {
            limpiarCajaBlanca(usuario, null);
        }
    }

    /*
     * TEST 5
     * Camino: if9(false) - 34
     */
    @Test
    public void test5() {
        String usuario = "GorkaInexistente";
        Integer saleNumber = 103;

        try {
            limpiarCajaBlanca(usuario, null);

            sut.crearSolicitud(usuario, saleNumber);

            Friendly friendly = recargarFriendly(usuario);
            assertNull(friendly);
            assertTrue(true);
        } catch (Exception e) {
            fail("El flujo debe terminar sin lanzar excepciones: " + e.getMessage());
        } finally {
            limpiarCajaBlanca(usuario, null);
        }
    }
}