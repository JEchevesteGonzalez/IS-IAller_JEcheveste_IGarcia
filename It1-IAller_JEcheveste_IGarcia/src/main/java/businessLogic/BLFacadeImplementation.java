package businessLogic;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Comprador;
import domain.Cuentas;
import domain.Oferta;
import domain.Sale;
import domain.Seller;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	 private static final int baseSize = 160;

	private static final String basePath="src/main/resources/images/";
	DataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Sale createSale(String title, String description,int status, float price, Date pubDate, String usuario, File file, int tVenta) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product=dbManager.createSale(title, description, status, price, pubDate, usuario, file, tVenta);		
		dbManager.close();
		return product;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Sale> getSales(String desc){
		dbManager.open();
		List<Sale>  rides=dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getPublishedSales(String desc, Date pubDate) {
			dbManager.open();
			List<Sale>  rides=dbManager.getPublishedSales(desc,pubDate);
			dbManager.close();
			return rides;
		}
	/**
	    * {@inheritDoc}
	    */
	@WebMethod public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    /**
	 * {@inheritDoc}
	 */
    @WebMethod public Image downloadImage(String imageName) {
        File image = new File(basePath+imageName);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Comprador buscarPorUser(String usuario) {
    	return dbManager.buscarPorUser(usuario);
    }
    
	public void addSeller(String usuario, String correo, String nombre) {
		dbManager.addSeller(usuario, correo, nombre);
	}
    
	public void addComprador(String usuario, String contrasena) {
		dbManager.addComprador(usuario, contrasena);
	}
	
	
	public boolean comprarProducto(String usuario, Sale saleNumber) {
		return dbManager.comprarProducto(usuario, saleNumber);
	}
	
	
	public void editSale(Sale sale, String text, int numStatus, float price, Date trim, boolean hab) {
    	dbManager.editSale(sale, text, numStatus, price, trim, hab);
    }
	
	public void eliminarUsuario(String usuario) {
		dbManager.eliminarUsuario(usuario);
	}
	
	public boolean anadirFondos(String usuario, float cantidad) {
		return dbManager.anadirFondos(usuario, cantidad);
	}
	
	public boolean retirarFondos(String usuario, float cantidad) {
		return dbManager.retirarFondos(usuario, cantidad);
	}
	
	public boolean pujar(Sale sale, float ofer) {
		return dbManager.pujar(sale, ofer);
	}
	
	public void anadirCuentas(String comprIntro, String contrIntro, Cuentas cu) {
		dbManager.anadirCuentas(comprIntro, contrIntro, cu);
	}
	
	public void crearOferta(String nUser, float precio, Sale s) {
		dbManager.crearOferta(nUser, precio, s);
	}
	
	public void devolverOfertas(Sale s) {
		dbManager.devolverOfertas(s);
	}
	
	public void aceptarOferta(Sale s, Oferta dOf) {
		dbManager.aceptarOferta(s, dOf);
	}

	public Sale eliminarOferta(Sale s, Oferta dOf) {
		return dbManager.eliminarOferta(s, dOf);
	}
	
	public void borrarVenta(Sale s) {
		dbManager.borrarVenta(s);
	}
	
}

