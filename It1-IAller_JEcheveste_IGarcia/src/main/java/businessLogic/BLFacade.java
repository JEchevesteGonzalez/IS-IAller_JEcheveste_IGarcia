package businessLogic;

import java.io.File;
import java.util.Date;
import java.util.List;

import domain.Comprador;
import domain.Cuentas;
import domain.Oferta;
import domain.Sale;
import domain.Seller;
import domain.Usuario;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.ResenaAlreadyExistsException;
import exceptions.SaleAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.awt.image.BufferedImage;
import java.awt.Image;

import gui.*;
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param tVenta 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Sale
	 */
   @WebMethod
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file, int tVenta) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException;
	
	
	/**
	 * This method retrieves the products that contain desc
	 * 
	 * @param desc the text to search
	 * @return collection of sales that contain desc 
	 */
	@WebMethod public List<Sale> getSales(String desc);
	
	/**
	 * 	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @param pubDate the date  of the publication date
	 * @return collection of sales that contain desc and published before pubDate
	 */
	@WebMethod public List<Sale> getPublishedSales(String desc, Date pubDate);

	
	/**
	 * This method calls the data access to initialize the database with some sellers and products.
	 * It is only invoked  when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
		
	@WebMethod public Image downloadImage(String imageName);

	public Usuario buscarPorUser(String usuario);
	
	public void addSeller(String usuario, String correo, String nombre);
	
	public void addComprador(String usuario, String contrasena);
	
	public boolean comprarProducto(String usuario, Sale saleNumber);

	public boolean anadirFondos(String usuario, float cantidad);

	public void editSale(Sale sale, String text, int numStatus, float price, Date trim, boolean hab);

	public void eliminarUsuario(String usuario);


	public boolean pujar(Sale sale, float ofer);


	public boolean retirarFondos(String usuario, float cantidadARetirar);

	public void anadirCuentas(String usuarioIntro, String contrIntro, Cuentas cu);
	
	public void crearOferta(String nUser, float precio, Sale s);
	
	public void devolverOfertas(Sale s);
	
	public void aceptarOferta(Sale s, Oferta dOf);
	
	public Sale eliminarOferta(Sale s, Oferta dOf);
	
	public void borrarVenta(Sale s);


	public void devolverCompra(Sale sale, String usuario);
	
	public void addResena(float valoracion, String descripcion, File file, Sale sale, String nUser) throws FileNotUploadedException, ResenaAlreadyExistsException;
	public Sale buscarPorNum(Integer num);
}
