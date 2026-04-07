package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.Comprador;
import domain.Cuentas;
import domain.Oferta;
import domain.Sale;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the data access to the objectdb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;
  
	private static final String basePath="src/main/resources/images/";
	private static final String dbServerDir = "src/main/resources/db/";

	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			if (!c.isDatabaseLocal()) fileName=dbServerDir+fileName;
			
			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
			//Create compradores
			Comprador comprador1=new Comprador("AitorMenta","batbihirulau");
			Comprador comprador2=new Comprador("Megaane","contrasena");
			Comprador comprador3=new Comprador("Teseller","12345");

		/*	
		    //Create sellers 
			Seller seller1=new Seller("AitorMenta","batbihirulau","seller1@gmail.com","Aitor Fernandez");
			Seller seller2=new Seller("Megaane","contrasena","seller22@gmail.com","Ane Gaztañaga");
			Seller seller3=new Seller("Teseller","12345","seller3@gmail.com","Test Seller");

			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			seller1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null);
			seller1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2, 20,  today, null);
			seller1.addSale("samsung 42\" telebista", "berria, erabili gabe", 2, 175,  today, null);


			seller2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null);
			seller2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			seller2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null);
			seller2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			seller3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null);
		 */
			
			db.persist(comprador1);
			db.persist(comprador2);
			db.persist(comprador3);

	
			db.getTransaction().commit();
			System.out.println("db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
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
	 * @return Product
 	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String usuario, File file, int tVenta) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		

		System.out.println(">> DataAccess: createProduct=> title= "+title+" usuario="+usuario);
		try {
		

			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();
			
			Seller seller = db.find(Seller.class, usuario);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file, tVenta);
			//next instruction can be obviated

			db.persist(seller); 
			db.getTransaction().commit();
			 System.out.println("sale stored "+sale+ " "+seller);

			return sale;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}

public void open(){
		
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	public void addSeller(String usuario, String correo, String nombre) {
		open();
		db.getTransaction().begin();
		Comprador user=db.find(Comprador.class, usuario);
		Seller vendedor = new Seller(user.getUsuario(),user.getContrasena(),correo,nombre);
		vendedor.setSaldo(user.getSaldo());
		vendedor.setHistorialDeCompras(user.getHistorialDeCompras());
		db.remove(user);
		db.persist(vendedor);
		db.getTransaction().commit();
		close();
	}
	
    public Comprador buscarPorUser(String usuario) {
    	open();
    	Comprador a= db.find(Comprador.class,usuario);
    	close();
    	return a;
    }
	
    public void addComprador (String usuario, String contrasena) {
    	open();
    	db.getTransaction().begin();
    	Comprador comp = new Comprador(usuario, contrasena);
    	db.persist(comp);
    	db.getTransaction().commit();
    	close();
    }
    
    public boolean comprarProducto(String usuario, Sale pro) {
		open();
		db.getTransaction().begin();
		try {
			Comprador comprador = db.find(Comprador.class, usuario);
			Sale producto = db.find(Sale.class, pro.getSaleNumber());
			if (comprador != null && producto !=null && producto.isHabilitado()) {
				if (comprador.getSaldo() >= producto.getPrice()) {
					comprador.setSaldo(comprador.getSaldo() - producto.getPrice());
					comprador.getHistorialDeCompras().add(producto); 
					producto.setHabilitado(false);
				
				
					if (producto.getUsuarioVendedor() != null) {
						Seller vendedor = db.find(Seller.class, producto.getUsuarioVendedor());
						if (vendedor != null) {
							producto.setUsuarioVendedor(vendedor.getUsuario());
							vendedor.setSaldo(vendedor.getSaldo() + producto.getPrice());
							vendedor.getSales().add(producto);
							db.persist(vendedor);
						}
					}
					db.persist(comprador);
					db.persist(producto);
					db.getTransaction().commit();
					close();
					return true; 
				}			
			}
			close();
			return false;
		}
		catch (Exception e){
			close();
			return false;
		}
    }
    

    public void eliminarUsuario(String usuario) {
		open();
		db.getTransaction().begin();
		Comprador user = db.find(domain.Comprador.class, usuario);
		if (user != null) {
			db.remove(user.getCuentas());
			db.remove(user);
			db.getTransaction().commit(); 
		}
		close();
				
	}
	
	public boolean anadirFondos(String usuario, float cantidad) {
		open();
		db.getTransaction().begin();
		try {
			Comprador user = db.find(Comprador.class, usuario);
			
			if (user != null) {
				
				float nuevoSaldo = user.getSaldo() + cantidad;
				user.setSaldo(nuevoSaldo);
				db.getTransaction().commit();
				return true;
				
			} else {
				
				return false;
			}
		} catch (Exception e) {
			return false;
		}finally {
			close();
		}
	}
    
    
    public void editSale(Sale sale, String text, int numStatus, float price, Date trim, boolean hab) {
    	open();
		db.getTransaction().begin();
		//db.persist(sale);
		Sale s = db.find(Sale.class, sale.getSaleNumber());
		//Sale s = sale.getSeller().getSales().get(sale.getSeller().getSales().indexOf(sale));
		s.setDescription(text);
		s.setStatus(numStatus);
		s.setPrice(price);
		s.setPublicationDate(trim);
		s.setHabilitado(hab);
		db.persist(s);
		//db.persist(sale.getSeller());
		db.getTransaction().commit();
		close(); 
    }
    
    
    
    
	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}

	public boolean pujar(Sale pro, float price) {
		open();
		db.getTransaction().begin();
		Sale producto = db.find(Sale.class, pro.getSaleNumber());
		if (producto != null) {
			producto.setPrice(price);
			db.persist(producto);
			db.getTransaction().commit();
			close();
			return true;
		}
		return false;
		
	}

	public boolean retirarFondos(String usuario, float cantidad) {
		open();
		db.getTransaction().begin();
		try {
			Comprador user = db.find(Comprador.class, usuario);
			
			if (user != null) {
				
				float nuevoSaldo = user.getSaldo() - cantidad;
				if (nuevoSaldo<0) {
					nuevoSaldo=0;
				}
				user.setSaldo(nuevoSaldo);
				db.getTransaction().commit();
				return true;
				
			} else {
				
				return false;
			}
		} catch (Exception e) {
			return false;
		}finally {
			close();
		}
	}

	public void anadirCuentas(String usuario, String contrasena, Cuentas cu) {
		open();
		db.getTransaction().begin();
		Comprador user = new Comprador(usuario, contrasena);
		cu.setComprador(user);
		user.setCuentas(cu);
		db.persist(user);
		db.persist(cu);
		db.getTransaction().commit();
		close();
	}
	
	public void crearOferta(String nUser, float precio, Sale s) {
		open();
		db.getTransaction().begin();
		Oferta o = new Oferta(nUser, precio, s);
		Sale sA = db.find (Sale.class,s);
		Comprador c = buscarPorUser(nUser);
		db.persist(o);
		c.getComprasActuales().add(o);
		retirarFondos(nUser, precio);
		sA.getOfertas().add(o);
		db.getTransaction().commit();
		close();
	}
	
	public void devolverOfertas(Sale s) {
		open();
		db.getTransaction().begin();
		Sale sA = db.find (Sale.class,s);
		for(Oferta dOf: sA.getOfertas()) {
			Comprador c=buscarPorUser(dOf.getnUser());
			anadirFondos(dOf.getnUser(), dOf.getPrecio());
			c.getComprasActuales().remove(dOf);
			sA.getOfertas().remove(dOf);
			db.remove(dOf);
		}
		db.getTransaction().commit();
		close();
	}
	
	public void aceptarOferta(Sale s, Oferta dOf) {
		open();
		db.getTransaction().begin();
		Sale sA = db.find(Sale.class,s);
		Comprador c=buscarPorUser(dOf.getnUser());
		c.getComprasActuales().remove(dOf);
		anadirFondos(s.getSeller().getUsuario(), dOf.getPrecio());
		sA.getOfertas().remove(dOf);
		db.remove(dOf);
		db.getTransaction().commit();
		close();
	}
	
	
}
