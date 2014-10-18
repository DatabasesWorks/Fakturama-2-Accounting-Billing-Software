/* 
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.webshopimport;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.javamoney.moneta.FastMoney;

import com.sebulli.fakturama.dao.ContactDAO;
import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.dao.PaymentsDAO;
import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.dao.ShippingCategoriesDAO;
import com.sebulli.fakturama.dao.ShippingsDAO;
import com.sebulli.fakturama.dao.VatsDAO;
import com.sebulli.fakturama.i18n.LocaleUtil;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.migration.CategoryBuilder;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.OrderState;
import com.sebulli.fakturama.misc.ProductUtil;
import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.ContactCategory;
import com.sebulli.fakturama.model.CustomDocument;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.Shipping;
import com.sebulli.fakturama.model.ShippingCategory;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.util.ContactUtil;
import com.sebulli.fakturama.webshopimport.type.AttributeType;
import com.sebulli.fakturama.webshopimport.type.CommentType;
import com.sebulli.fakturama.webshopimport.type.ContactType;
import com.sebulli.fakturama.webshopimport.type.ItemType;
import com.sebulli.fakturama.webshopimport.type.ObjectFactory;
import com.sebulli.fakturama.webshopimport.type.OrderType;
import com.sebulli.fakturama.webshopimport.type.PaymentType;
import com.sebulli.fakturama.webshopimport.type.ProductType;
import com.sebulli.fakturama.webshopimport.type.ShippingType;
import com.sebulli.fakturama.webshopimport.type.Webshopexport;

/**
 * Web shop import manager. This class provides the functionality to connect to
 * the web shop and import the data, which is transmitted as a XML File. This 
 * file is created by a connector, which is individual for each shop system.
 * Look at Fakturama download page for further information. 
 * The WebshopImporter creates the missing products, VATs and documents (orders in this case). 
 * 
 */
public class WebShopImportManager {

    @Inject
    @Translation
    private Messages msg;
    
    @Inject
    private Logger log;
    
    @Inject
    @Preference
    private IEclipsePreferences eclipsePrefs;
    
    @Inject
    private VatsDAO vatsDAO;
    
    @Inject
    private DocumentsDAO documentsDAO;
    
    @Inject
    private ContactDAO contactsDAO;
    
    @Inject
    private ProductsDAO productsDAO;
    
    @Inject
    private ShippingCategoriesDAO shippingCategoriesDAO;
    
    @Inject
    private ShippingsDAO shippingsDAO;
    
    @Inject
    private PaymentsDAO paymentsDAO;
    
    @Inject
    private ProductCategoriesDAO productCategoriesDAO;
    
	/**
	 * Runs the reading of a http stream in an extra thread.
	 * So it can be interrupted by clicking the cancel button. 
	 * 
	 * @author Gerd Bartelt
	 */
	public class InterruptConnection implements Runnable {
	    
		// The connection 
		private URLConnection conn;
		
		// Reference to the input stream data
	    private InputStream inputStream = null;
	    
	    // true, if the reading was successful
	    private boolean isFinished = false;

	    // true, if there was an error
	    private boolean isError = false;
	    
	    
	    /**
	     * Constructor. Creates a new connection to use it in an extra thread
	     * 
	     * @param conn
	     * 			The connection
	     */
	    public InterruptConnection(URLConnection conn) {
	        this.conn = conn;
	    }

	    /**
	     * Return whether the reading was successful
	     * 
	     * @return
	     * 		True, if the stream was read completely
	     */
	    public boolean isFinished() {
	    	return isFinished;
	    }

	    /**
	     * Return whether the was an error
	     * 
	     * @return
	     * 		True, if there was an error
	     */
	    public boolean isError() {
	    	return isError;
	    }
	    
	    	    
	    /**
	     * Returns a reference to the input stream
	     * 
	     * @return
	     * 		Reference to the input stream
	     */
	    public InputStream getInputStream() {
	    	return inputStream;
	    }
	    
	    /**
	     * Start reading the input stream 
	     */
	    public void run() {
	        try {
	        	inputStream = conn.getInputStream();
	        	isFinished = true;
	        } catch (IOException e) {
	        	isError = true;
			}
	    }
	}

	
	// List of all orders, which are out of sync with the web shop.
	private Properties orderstosynchronize = null;

	// The result of this import process
	private String runResult = "";

	// Imported data
//	private String shopSystem ="";
	private String shopURL = "";
	private String productImagePath = "";

	private int worked;

	// Configuration of the web shop request
	private boolean getProducts;
	private boolean getOrders;

    // true, if the product's EAN number is imported as item number
    private Boolean useEANasItemNr = false;

	private String generalWorkspace;
	
	@PostConstruct
	public void initialize() {
		generalWorkspace = eclipsePrefs.get(Constants.GENERAL_WORKSPACE, "");
	}

	/**
	 * Prepare the web shop import to request products and orders.
	 */
	public void prepareGetProductsAndOrders() {
		getProducts = true;
		getOrders = true;
	}

	/**
	 * Prepare the web shop import to change the state of an order.
	 */
	public void prepareChangeState() {
		getProducts = false;
		getOrders = false;
	}

	
	/**
	 * This is the central execution entry point for the Webshop import process.
	 * 
	 * @param parent
	 * @return
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	@Execute
	public ExecutionResult execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell parent) {
	    ExecutionResult result = null;
	    prepareGetProductsAndOrders();
        try {
            ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(parent);
            IRunnableWithProgress op = new WebShopImportWorker();
            progressMonitorDialog.run(true, true, op);
            result = new ExecutionResult(runResult, 0);
          }
          catch (InvocationTargetException e) {
              log.error(e, "Error running web shop import manager.");
              result = new ExecutionResult("Error running web shop import manager.", 1);
          }
          catch (InterruptedException e) {
              log.error(e, "Web shop import manager was interrupted.");
              result = new ExecutionResult("Web shop import manager was interrupted.", 2);
          }
        return result;
	}

	/**
	 * Save the list of all orders, which are out of sync with the web shop to
	 * file system
	 * 
	 */
	private void saveOrdersToSynchronize() {
		if (orderstosynchronize.isEmpty())
			return;

		try (Writer writer = new FileWriter(generalWorkspace + "/orders2sync.txt")) {
			orderstosynchronize.store(writer, "OrdersNotInSyncWithWebshop");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update the progress of an order.
	 * 
	 * @param uds
	 *            The UniDataSet with the new progress value
	 */
	public void updateOrderProgress(Document uds, String comment, boolean notify) {

		// Get the progress value of the UniDataSet
		String orderId = uds.getWebshopId();
		int progress = uds.getProgress();
		int webshopState;

		// Get the orders that are out of sync with the shop
		readOrdersToSynchronize();

		// Convert a percent value of 0..100% to a state of 1,2,3
		if (progress >= OrderState.SHIPPED.getState())
			webshopState = 3;
		else if (progress >= OrderState.PROCESSING.getState())
			webshopState = 2;
		else
			webshopState = 1;

		// Set the new progress state 
		// Add an "*" to mark the ID as "notify customer"
		String value = Integer.toString(webshopState);

		//Replace the "," by "&comma;
		comment = comment.replace("%2C", "%26comma%3B");
		//Replace the "=" by "&equal;
		comment = comment.replace("%3D", "%26equal%3B");
		
		if (notify)
			value += "*" + comment;

		orderstosynchronize.setProperty(orderId, value);
		saveOrdersToSynchronize();
	}

	/**
     * Read the list of all orders, which are out of sync with the web shop
     * from the file system
     * 
     */
    private void readOrdersToSynchronize() {
        orderstosynchronize = new Properties();
        try (Reader reader = new FileReader(generalWorkspace + "/orders2sync.txt")) {
            orderstosynchronize.load(reader);
        } catch (FileNotFoundException fnex) {
            log.warn(fnex, "file not found: orders2sync.txt (will be created next time)");
        } catch (IOException e) {
            log.error(e);
        }
    }

    class WebShopImportWorker implements IRunnableWithProgress {
	    private IProgressMonitor localMonitor;
	    
	    @Override
	    public void run(IProgressMonitor pMonitor) throws InvocationTargetException, InterruptedException  {
	        localMonitor = pMonitor;
            runResult = "";
    		Webshopexport webshopexport = null;
            
            // Get URL, user name and password from the preference store
            String address = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_URL, "");
            String user = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_USER, "");
            String password = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_PASSWORD, "");
            Integer maxProducts  = eclipsePrefs.getInt(Constants.PREFERENCES_WEBSHOP_MAX_PRODUCTS, 1);
            Boolean onlyModifiedProducts  = eclipsePrefs.getBoolean(Constants.PREFERENCES_WEBSHOP_ONLY_MODIFIED_PRODUCTS, true);
            useEANasItemNr  = eclipsePrefs.getBoolean(Constants.PREFERENCES_WEBSHOP_USE_EAN_AS_ITEMNR, true);
            Boolean useAuthorization = eclipsePrefs.getBoolean("WEBSHOP_AUTHORIZATION_ENABLED", false); 
            String authorizationUser = eclipsePrefs.get("WEBSHOP_AUTHORIZATION_USER", "");
            String authorizationPassword = eclipsePrefs.get("WEBSHOP_AUTHORIZATION_PASSWORD", "");
            
            // Check empty URL
            if (address.isEmpty()) {
                //T: Status message importing data from web shop
                runResult = msg.importWebshopErrorUrlnotset;
                return;
            }
            
            // Add "http://"
            if (!address.toLowerCase().startsWith("http://") 
                    && !address.toLowerCase().startsWith("https://") 
                    && !address.toLowerCase().startsWith("file://")) {
                address = "http://" + address;
            }
    
            // Get the open order IDs that are out of sync with the webshop
			// from the file system
			readOrdersToSynchronize();

            try {
                // Connect to web shop
                worked = 0;
                URLConnection conn = null;
                //T: Status message importing data from web shop
                localMonitor.beginTask(msg.importWebshopInfoConnection, 100);
                //T: Status message importing data from web shop
                localMonitor.subTask(msg.importWebshopInfoConnected + " " + address);
                setProgress(10);
                URL url = new URL(address);
                conn = url.openConnection();
                conn.setDoInput(true);
                conn.setConnectTimeout(4000);
                if (!address.toLowerCase().startsWith("file://")) {
                    conn.setDoOutput(true);
    
                    // Use password for password protected web shops
                    if (useAuthorization) {
                        String encodedPassword = Base64Coder.encodeString(authorizationUser + ":" + authorizationPassword );
                        conn.setRequestProperty( "Authorization", "Basic " + encodedPassword );
                    }
    
                    // Send user name , password and a list of unsynchronized orders to
                    // the shop
                    OutputStream outputStream = null;
                    outputStream = conn.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    setProgress(20);
                    String postString = "username=" + URLEncoder.encode(user, "UTF-8") + "&password=" +URLEncoder.encode(password, "UTF-8") ;
    
                    String actionString = "";
                    if (getProducts)
                        actionString += "_products";
                    if (getOrders)
                        actionString += "_orders";
                    if (!actionString.isEmpty())
                        actionString = "&action=get" + actionString;
    
                    postString += actionString + "&setstate=" + orderstosynchronize.toString();
                    
                    if (maxProducts > 0) {
                        postString += "&maxproducts=" + maxProducts.toString();
                    }
    
                    if (onlyModifiedProducts) {
                        String lasttime = eclipsePrefs.get("lastwebshopimport","");
                        if (! lasttime.isEmpty())
                            postString += "&lasttime=" + lasttime.toString();
                    }
                
                    log.debug("POST-String: " + postString);
                    writer.write(postString);
                    writer.flush();
                    writer.close();
    
                }
                setProgress(30);
                
                // Start a connection in an extra thread
                InterruptConnection interruptConnection = new InterruptConnection(conn);
                new Thread(interruptConnection).start();
                while (!localMonitor.isCanceled() && !interruptConnection.isFinished() && !interruptConnection.isError());
    
                // If the connection was interrupted and not finished: return
                if (!interruptConnection.isFinished()) {
                    ((HttpURLConnection)conn).disconnect();
                    if (interruptConnection.isError()) {
                        //T: Status error message importing data from web shop
                        runResult = msg.importWebshopErrorCantconnect;
                    }
                    return;
                }
    
                // If there was an error, return with error message
                if (interruptConnection.isError()) {
                    ((HttpURLConnection)conn).disconnect();
                    //T: Status message importing data from web shop
                    runResult = msg.importWebshopErrorCantread;
                    return;
                }
                
        		// 1. We need to create JAXContext instance
        		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

        		// 2. Use JAXBContext instance to create the Unmarshaller.
        		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

				// 3. Use the Unmarshaller to unmarshal the XML document to get
				// an instance of JAXBElement.
    
                //T: Status message importing data from web shop
                localMonitor.subTask(msg.importWebshopInfoLoading);
    
                // Get the directory of the workspace
                String filename = generalWorkspace;
    
                Path logFile = null;
                BufferedWriter logBuffer = null;
    
                // Do not save log files, of there is no workspace set
                if (!filename.isEmpty()) {
    
                    // Create a sub folder "Log", if it does not exist yet.
                    filename += "/Log/";
                    Path directory = Paths.get(filename);
                    if (!Files.isDirectory(directory)) {
                        Files.createDirectories(directory);
                    }
    
                    // Name of the log file
                    filename += "WebShopImport.log";
    
                    // Create a File object
                    logFile = Paths.get(filename);
    
                    // Create a new file
                    Files.deleteIfExists(logFile);
                    Files.createFile(logFile);
    
                    // Create a buffered writer to write the imported data to the file system
                    logBuffer = Files.newBufferedWriter(logFile);
                }

				// 4. Get the instance of the required JAXB Root Class from the
				// JAXBElement.
				webshopexport = (Webshopexport) unmarshaller
        					.unmarshal(interruptConnection.getInputStream());
        		setProgress(50);
        		
        		// Write the web shop log file
                if (logBuffer != null) {
                	Marshaller marshaller = jaxbContext.createMarshaller(); 
                	marshaller.marshal(webshopexport, logBuffer);
                    logBuffer.close();
                }
                
                // parse the XML stream
                if (!localMonitor.isCanceled()) {
                	if(webshopexport.getWebshop() == null) {
                        //T: Status message importing data from web shop
                        runResult = msg.importWebshopErrorNodata + "\n" + address;
                        return;
                    }
    
                    // Clear the list of orders to sync, if the data was sent
                	// ndList = document.getElementsByTagName("webshopexport");
					if (webshopexport.getOrders() != null) {
						orderstosynchronize = new Properties();
					} else {
						runResult = "import NOT ok";
					}
    
                    // Get the error elements and add them to the run result list
                    //ndList = document.getElementsByTagName("error");
                    if (StringUtils.isNotEmpty(webshopexport.getError()) ) {
                        runResult = webshopexport.getError();
                    }
                }
                // else cancel the download process
    
                // Interpret the imported data (and load the product images)
                if (runResult.isEmpty()) {
                    // If there is no error - interpret the data.
                    interpretWebShopData(localMonitor, webshopexport);
                }
    
                // Store the time of now
                String now = DataUtils.DateAsISO8601String();
                eclipsePrefs.put("lastwebshopimport", now);
                
                localMonitor.done();
            }
            catch (MarshalException mex) {
                //T: Status message importing data from web shop
                runResult = msg.importWebshopErrorNodata + "\n" + address + "\n" + mex.getMessage();
			}
            catch (Exception e) {
                //T: Status message importing data from web shop
                runResult = msg.importWebshopErrorCantopen + "\n" + address + "\n";
                runResult += "Message:" + e.getLocalizedMessage()+ "\n";
                if (e.getStackTrace().length > 0)
                    runResult += "Trace:" + e.getStackTrace()[0].toString()+ "\n";

                if (webshopexport != null)
                    runResult += "\n\n" + webshopexport;
                }
            }

	    /**
	     * Sets the progress of the job in percent
	     * 
	     * @param percent
	     */
	    void setProgress(int percent) {
	        if (percent > worked) {
	            localMonitor.worked(percent - worked);
	            worked = percent;
	        }
	    }

        /**
         * Interpret the complete node of all orders and import them
         * @param webshopexport 
         * @throws SQLException 
         */
        private void interpretWebShopData(IProgressMonitor monitor, Webshopexport webshopexport) throws SQLException {
        
        	//shopSystem ="";
        	shopURL = "";
        	productImagePath = "";
        
        	// Mark all orders as "in sync with the web shop"
        	allOrdersAreInSync();
        
        	// There is no order
        	if (webshopexport == null)
        		return;
        
        	// Get the general shop data
        	if (webshopexport.getWebshop() != null) {
        		//shopSystem = webshopexport.getWebshop().getShop();
        		shopURL = webshopexport.getWebshop().getUrl();
        	}
        
        	// Get the general products data
        	if (webshopexport.getProducts() != null) {
        		productImagePath = webshopexport.getProducts().getImagepath();
        	}
        
        	// Get all products and import them
        	
        	List<ProductType> productList = webshopexport.getProducts().getProductList();
			int producListSize = productList.size();
			for (int productIndex = 0; productIndex < producListSize; productIndex++) {
        		//T: Status message importing data from web shop
        		monitor.subTask(msg.importWebshopInfoLoading + " " + Integer.toString(productIndex + 1) + "/" + Integer.toString(producListSize));
        		setProgress(50 + 40 * (productIndex + 1) / producListSize);
        		ProductType product = productList.get(productIndex);
        		createProductFromXMLOrderNode(product);
        		
        		// Cancel the product picture import process
        		if ( monitor.isCanceled() )
        			return;
        	}
        
        	// Get order by order and import it
        	//T: Status message importing data from web shop
        	monitor.subTask(msg.importWebshopInfoImportorders);
        	setProgress(95);
        	List<OrderType> orderList = webshopexport.getOrders().getOrderList();
        	int orderListSize = orderList.size();
        	for (int orderIndex = 0; orderIndex < orderListSize; orderIndex++) {
        		OrderType order = orderList.get(orderIndex);
        		createOrderFromXMLOrderNode(order);
        	}
        
        	// Save the new list of orders that are not in synch with the shop
        	saveOrdersToSynchronize();
        	
        }

        /**
         * Mark all orders as "in sync" with the web shop
         */
        private void allOrdersAreInSync() {
        	orderstosynchronize = new Properties();
        	Path f = Paths.get(generalWorkspace, "/orders2sync.txt");
        	try {
                Files.deleteIfExists(f);
            }
            catch (IOException e) {
                log.error(e, "can't delete orders2sync.txt");
            }
        }

        /**
         * Parse an XML node and create a new order for each order entry
         * 
         * @param order
         *            The node with the orders to import
         * @throws SQLException 
         */
        private void createOrderFromXMLOrderNode(OrderType order) throws SQLException {
    		// Order data
    		String webshopId;
    		String webshopDate;
    		int genderInt = 0;
    		int deliveryGenderInt = 0;

    		// Comments
    		String commentDate;
    		StringBuilder comment;
    		String commentText;

    		// Item data
    		String itemModel = "";
    		String itemName = "";
    		StringBuffer itemDescription;

    		// Remember the vat name, of there is no vat calculated
        	boolean noVat = true;
        	String noVatName = "";
        	
        	// Get the attributes ID and date of this order
        	webshopId = order.getId();
        	webshopDate = order.getDate();
        
        	// Check, if this order is still existing
        	Date calendarWebshopDate = DataUtils.getCalendarFromDateString(webshopDate).getTime();
            if(!documentsDAO.findDocumentByDocIdAndDocDate(DocumentType.ORDER, webshopId, calendarWebshopDate).isEmpty()) {
        		return;
        	}
        
        	// Create a new order
        	Document dataSetDocument = new CustomDocument();
        	dataSetDocument.setBillingType(BillingType.ORDER); // DocumentType.ORDER
        
        	// Set name, web shop order id and date
        	// order_status = order.getStatus();
        	// currency = order.getCurrency();
        	dataSetDocument.setName(webshopId);
        	dataSetDocument.setWebshopId(webshopId);
        	dataSetDocument.setWebshopDate(calendarWebshopDate);
        	dataSetDocument = documentsDAO.save(dataSetDocument);
        
            CategoryBuilder<ContactCategory> contactCatBuilder = new CategoryBuilder<ContactCategory>();
   
        	// First get all contacts. Normally there is only one
            ContactType contact = order.getContact();        
			// Convert a gender character "m" or "f" to the gender number 
			// 1 or 2
			if (contact.getGender().equals("m"))
				genderInt = 1;
			if (contact.getGender().equals("f"))
				genderInt = 2;
			if (contact.getDeliveryGender().equals("m"))
				deliveryGenderInt = 1;
			if (contact.getDeliveryGender().equals("f"))
				deliveryGenderInt = 2;

			// Get the category for new contacts from the preferences
			String shopCategory = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_CONTACT_CATEGORY, "");

			// use existing contact, or create new one
			Contact contactItem = new Contact();
			if(StringUtils.isNotEmpty(shopCategory)) {
    			ContactCategory contactCat = contactCatBuilder.buildCategoryFromString(shopCategory, ContactCategory.class);
    			contactItem.addToCategories(contactCat);
			}
			
			Address address = new Address();
			Contact deliveryContact = new Contact();
			Address deliveryAddress = new Address();
        
			// set explicit the customers data
            contactItem.setGender(genderInt);
            contactItem.setFirstName(contact.getFirstname());
            contactItem.setName(contact.getLastname());
            contactItem.setCompany(contact.getCompany());
            contactItem.setPhone(contact.getPhone());
            contactItem.setEmail(contact.getEmail());
            address.setStreet(contact.getStreet());
            address.setZip(contact.getZip());
            address.setCity(contact.getCity());
            String countryCode = LocaleUtil.findByName(contact.getCountry());
            
            address.setCountry(countryCode);
            contactItem.setAddress(address);
//            contactItem.setSupplierNumber(contact.get); ==> is not transfered from connector!!!

            deliveryContact.setGender(deliveryGenderInt);
            deliveryContact.setFirstName(contact.getDeliveryFirstname());
            deliveryContact.setName(contact.getDeliveryLastname());
            deliveryContact.setCompany(contact.getDeliveryCompany());
            deliveryAddress.setStreet(contact.getDeliveryStreet());
            deliveryAddress.setZip(contact.getDeliveryZip());
            deliveryAddress.setCity(contact.getDeliveryCity());
            countryCode = LocaleUtil.findByName(contact.getDeliveryCountry());
            deliveryAddress.setCountry(countryCode);
            deliveryContact.setAddress(deliveryAddress);
            contactItem.getDeliveryContacts().add(deliveryContact);
            contactsDAO.save(contactItem);
        
            dataSetDocument.setContact(contactItem);
//            dataSetDocument.setAddress(contactItem.getAddress(false)); // included in contact
//            dataSetDocument.setDeliveryaddress(deliveryContact); // included in contact
            dataSetDocument.setAddressFirstLine(ContactUtil.getNameWithCompany(deliveryContact, false));			
        
        	// Get the comments
        	comment = new StringBuilder("");

        	for (CommentType commentType : order.getComments()) {
        		// Get the comment text
    			commentDate = DataUtils.DateAndTimeAsLocalString(commentType.getDate());
    			commentText = commentType.getTextcontent();
    			if (comment.length() > 0) {
    				comment.append('\n');
    			}
    
    			// Add the date
    			comment.append(commentDate).append(" :\n");
    			comment.append(commentText).append("\n");
        	}
        
        	// Get all the items of this order
        	for (ItemType itemType : order.getItems()) {
    			// Convert VAT percent value to a factor (100% -> 1.00)
    			Double vatPercent = 0.0;
    			try {
    				vatPercent = Double.valueOf(itemType.getVatpercent()).doubleValue() / 100;
    			}
    			catch (NumberFormatException e) {
    				log.error(e, String.format(msg.importWebshopErrorCantconvertnumber, 
    						vatPercent, " (vatPercent)" ));
    			}
    
    			// If one item has a vat value, reset the noVat flag
    			if (vatPercent > 0.0)
    				noVat = false;
    			else {
    				// Use the vat name
    				if (noVatName.isEmpty() && !itemType.getVatname().isEmpty())
    					noVatName = itemType.getVatname();
    			}
    
    			// Calculate the net value of the price
        		MonetaryAmount priceNet = FastMoney.of(0.0, eclipsePrefs.get(Constants.PREFERENCE_GENERAL_CURRENCY, "EUR"));
				MonetaryAmount priceGross = FastMoney.of(itemType.getGross(), eclipsePrefs.get(Constants.PREFERENCE_GENERAL_CURRENCY, "EUR"));
				priceNet = priceGross.divide(1 + vatPercent);
    
    			// Add the VAT value to the data base, if it is a new one
    			VAT vat = new VAT();
    			vat.setName(itemType.getVatname());
    			vat.setDescription(itemType.getVatname());
    			vat.setTaxValue(vatPercent);
    			vat = vatsDAO.addIfNew(vat);
    
    			// Import the item as a new product
    			Product product;
    
    			// Get the category of the imported products from the preferences
    			shopCategory = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_PRODUCT_CATEGORY, "");
    
				if (!shopCategory.isEmpty() && !shopCategory.endsWith("/")) {
					shopCategory += "/";
				}
    
    			// Use item name as item model, if model is empty
    			if (itemType.getModel().isEmpty() && !itemType.getName().isEmpty())
    				itemModel = itemType.getName();
    
    			// Use item model as item name, if name is empty
    			if (itemType.getName().isEmpty() && !itemType.getModel().isEmpty())
    				itemName = itemModel;
    
    			// Import the product attributes
    			itemDescription = new StringBuffer();
    			for (AttributeType attribute : itemType.getAttribute()) {
    				// Get all attributes
					if (itemDescription.length() > 0)
						itemDescription.append(", ");
					itemDescription.append(attribute.getOption()).append(": ");
					itemDescription.append(attribute.getValue());
    			}
    
    			// Create a new product
    			product = new Product();
    			// itemName, itemModel, shopCategory + itemCategory, itemDescription, priceNet, vat, "", "", 1.0, productID, itemQUnit
    			product.setName(itemName);
    			product.setItemNumber(itemModel);
    			CategoryBuilder<ProductCategory> prodCatBuilder = new CategoryBuilder<ProductCategory>();
    			ProductCategory productCategory = prodCatBuilder.buildCategoryFromString(shopCategory + itemType.getCategory(), ProductCategory.class);
    			product.addToCategories(productCategory);
    			
    			product.setDescription(itemDescription.toString());
    			product.setPrice1(priceNet.getNumber().numberValue(BigDecimal.class));
    			product.setVat(vat);
    			//product.setProductId(itemType.getProductid());
    
    			// Add the new product to the data base, if it's not existing yet
    			Product newOrExistingProduct = productsDAO.addIfNew(product);
    			// Get the picture from the existing product
    			product.setPictureName(newOrExistingProduct.getPictureName());
    
    			// Add this product to the list of items
    			DocumentItem item = new DocumentItem();
    			//(Double.valueOf(itemQuantity), product, itemDiscountDouble);
    			item.setQuantity(Double.valueOf(itemType.getQuantity()));
    			item.setQuantityUnit(itemType.getQunit());
    			item.setProduct(product);
    			item.setItemRebate(itemType.getDiscount().doubleValue());
    			
                // search for owning document
    			item.setOwningDocument((CustomDocument) dataSetDocument);
    
    			// Update the modified item data
    			dataSetDocument.getItems().add(item);
        	}
        
        	// Get the shipping(s)
        	ShippingType shippingType = order.getShipping();
    		// Import the shipping data
    		if (shippingType != null) {
    			// Get the VAT value as double
    			Double shippingVatPercent = 0.0;
				shippingVatPercent = Double.valueOf(shippingType.getVatpercent()).doubleValue() / 100;
    
    			// Get the shipping gross value
    			Double shippingGross = 0.0;
				shippingGross = Double.valueOf(shippingType.getGross());
    
    			// Get the category of the imported shipping from the preferences
    			shopCategory = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_SHIPPING_CATEGORY, "");
    
    			VAT shippingvat = new VAT();
    			shippingvat.setName(shippingType.getVatname());
    			shippingvat.setDescription(shippingType.getVatname());
    			shippingvat.setTaxValue(shippingVatPercent);
    			
    			// Add the VAT entry to the data base, if there is not yet one
    			// with the same values				
    			vatsDAO.addIfNew(shippingvat);
    
    			// Add the shipping to the data base, if it's a new shipping
    			Shipping shipping = new Shipping(); 
    			shipping.setName(shippingType.getName());
    			ShippingCategory newShippingCategory = shippingCategoriesDAO.findByName(shopCategory);
    			shipping.getCategories().add(newShippingCategory);
    			shipping.setDescription(shippingType.getName());
    			shipping.setShippingValue(shippingGross);
    			shipping.setShippingVat(shippingvat);
    			shipping.setAutoVat(Boolean.TRUE);
    			shipping = shippingsDAO.addIfNew(shipping);
    
    			// Set the document entries for the shipping
                dataSetDocument.setShipping(shipping);
    			String s = "";
    
    			// Use the order ID of the web shop as customer reference for
    			// imports web shop orders
    			if (webshopId.length() <= 5)
    				s = "00000".substring(webshopId.length(), 5);
    			s += webshopId;
    			//T: Text of the web shop reference
    			dataSetDocument.setCustomerRef(msg.importWebshopInfoWebshopno + " " + s);
    		}
        
        	// Get the payment (s)
    		
    		PaymentType paymentType = order.getPayment();
			if (paymentType != null) {
    			// Add the payment to the data base, if it's a new one
    			Payment payment = new Payment();
    			payment.setName(paymentType.getName());
    			payment.setDescription(paymentType.getName() + " (" + paymentType.getType() + ")");
    			payment.setPaidText(msg.dataDefaultPaymentPaidtext);
    			paymentsDAO.addIfNew(payment);
    		}
        
        	// Set the progress of an imported order to 10%
        	dataSetDocument.setProgress(10);
        
        	// Set the document data
        	dataSetDocument.setWebshopDate(calendarWebshopDate);
        	comment = new StringBuilder(dataSetDocument.getMessage()).append(comment);
        	dataSetDocument.setMessage(comment.toString());
        
        	// TODO is the discount really at PaymentType???
        	dataSetDocument.setItemsRebate(paymentType.getDiscount().doubleValue());
        	dataSetDocument.setTotalValue(BigDecimal.valueOf(paymentType.getTotal().floatValue()));
        
        	// There is no VAT used
        	if (noVat) {
        		// Set the no-VAT flag in the document and use the name and description
        		VAT noVatReference = vatsDAO.findByName(noVatName);
        		if (noVatReference != null) {
        			dataSetDocument.setNoVatReference(noVatReference);
        		}
        	}
        	
        	// Update the data base with the new document data
        	documentsDAO.save(dataSetDocument);
        
        	// Re-calculate the document's total sum and check it.
        	// It must be the same total value as in the web shop
//        	dataSetDocument.calculate();
			MonetaryAmount calcTotal = FastMoney.of(0.0, eclipsePrefs.get(
					Constants.PREFERENCE_GENERAL_CURRENCY, "EUR")); // dataSetDocument.getSummary().getTotalGross().asDouble();
			MonetaryAmount totalFromWebshop = FastMoney.of(paymentType.getTotal(), eclipsePrefs.get(
					Constants.PREFERENCE_GENERAL_CURRENCY, "EUR")); 
        	// If there is a difference, show a warning.
        	if (!calcTotal.isEqualTo(totalFromWebshop)) {
        		// TODO change Locale to OSGI environment variable
        		MonetaryAmountFormat defaultFormat = MonetaryFormats.getAmountFormat(Locale.GERMANY);
        		//T: Error message importing data from web shop
        		//T: Format: ORDER xx TOTAL SUM FROM WEB SHOP: xx IS NOT EQUAL TO CALCULATED ONE: xx. PLEASE CHECK
        		String error = msg.toolbarNewOrderName + ":";
        		error += " " + webshopId + "\n";
        		error += msg.importWebshopInfoTotalsum;
        		error += "\n" + DataUtils.DoubleToFormatedPriceRound(paymentType.getTotal().doubleValue()) + "\n";
        		error += msg.importWebshopErrorTotalsumincorrect;
        		error += "\n" + defaultFormat.format(calcTotal) + "\n";
        		error += msg.importWebshopErrorTotalsumcheckit;
        		runResult = error;
        	}        
        }

        /**
        	 * Parse an XML node and create a new product for each product entry
        	 * 
        	 * @param product
        	 *            The node with the products to import
         * @throws SQLException 
        	 */
        	private void createProductFromXMLOrderNode(ProductType product) throws SQLException {
        		// Get the product description as plain text.
        		String productDescription = product.getShortDescription();
        		String productModel = product.getModel();
        		String productName = product.getName();
        		String pictureName;
        
        		// Convert VAT percent value to a factor (100% -> 1.00)
        		Double vatPercentDouble = 0.0;
       			vatPercentDouble = Double.valueOf(product.getVatpercent()).doubleValue() / 100;
        
        		// Convert the gross or net string to a double value
        		MonetaryAmount priceNet = FastMoney.of(0.0, eclipsePrefs.get(Constants.PREFERENCE_GENERAL_CURRENCY, "EUR"));
        
    			// Use the net string, if it is set
    			// => net string is *never* set! The connectors don't deliver it!
    
    			// Use the gross string, if it is set
    			if (product.getGross() != null) {
    				MonetaryAmount priceGross = FastMoney.of(product.getGross(), eclipsePrefs.get(Constants.PREFERENCE_GENERAL_CURRENCY, "EUR"));
    				priceNet = priceGross.divide(1 + vatPercentDouble);
    			}
        
        		// Add the VAT value to the data base, if it is a new one 
        		VAT vat = new VAT();
        		vat.setName(product.getVatname());
        		vat.setDescription(product.getVatname());
        		vat.setTaxValue(vatPercentDouble);
        	    try {
                    vat = vatsDAO.addIfNew(vat);
                } catch (SQLException e1) {
                    log.error(e1);
                }
        
        		// Import the item as a new product
        		Product productItem;
        
        		// Get the category of the imported products from the preferences
        		String shopCategory = eclipsePrefs.get(Constants.PREFERENCES_WEBSHOP_PRODUCT_CATEGORY, "");
        
        		// If the category is not set, use the shop category
        		if (!shopCategory.isEmpty())
        			if (!shopCategory.endsWith("/"))
        				shopCategory += "/";
        
        		// Use the EAN number
        		if (useEANasItemNr) {
        			if (!product.getEan().isEmpty())
        				productModel = product.getEan();
        		}
        		
        		// Use product name as product model, if model is empty
        		if (productModel.isEmpty() && !product.getName().isEmpty())
        			productModel = product.getName();
        
        		// Use product model as product name, if name is empty
        		if (product.getName().isEmpty() && !productModel.isEmpty())
        			productName = productModel;
        
        		pictureName = "";
        
        		// Create the URL to the product image
        		if (!product.getImage().isEmpty()) {
        			pictureName = ProductUtil.createPictureName(productName, productModel);
        			// TODO kein Trennzeichen? +"/"+ fehlt...
        			downloadImageFromUrl(localMonitor, shopURL + productImagePath + product.getImage(), 
        					generalWorkspace + Constants.PRODUCT_PICTURE_FOLDER, pictureName);
        		}
        
        		// Convert the quantity string to a double value
        		Double quantity = Double.valueOf(product.getQuantity()).doubleValue();
        
        		// Create a new product object
        		productItem = new Product();
        		productItem.setName(productName);
        		productItem.setItemNumber(productModel);
        		
        		CategoryBuilder<ProductCategory> prodCatBuilder = new CategoryBuilder<ProductCategory>();
        		ProductCategory productCategoryFromBuilder = prodCatBuilder.buildCategoryFromString(shopCategory + product.getCategory(), ProductCategory.class);
        		
        		// save ProductCategory
        		productCategoryFromBuilder = productCategoriesDAO.addIfNew(productCategoryFromBuilder);
        		productItem.getCategories().add(productCategoryFromBuilder);
        		productItem.setDescription(productDescription);
        		productItem.setPrice1(priceNet.getNumber().numberValue(BigDecimal.class));
        		productItem.setVat(vat);
        		productItem.setPictureName(pictureName);
        		productItem.setQuantity(quantity);
        		productItem.setWebshopId(product.getId().longValue());
        		productItem.setQuantityUnit(product.getQunit());
        
        		// Add a new product to the data base, if it not exists yet	
        		Product existingProduct = productsDAO.findByExample(productItem);
                if (existingProduct == null) {
        			productsDAO.save(productItem);
        		}
        		else {
        			// Update data
        			existingProduct.getCategories().clear();
        			existingProduct.getCategories().addAll(productItem.getCategories());
        			existingProduct.setName(productItem.getName());
                    existingProduct.setItemNumber(productItem.getItemNumber());
                    existingProduct.setDescription(productItem.getDescription());
                    existingProduct.setPrice1(productItem.getPrice1());
                    existingProduct.setVat(productItem.getVat());
                    existingProduct.setPictureName(productItem.getPictureName());
                    existingProduct.setQuantity(productItem.getQuantity());
                    existingProduct.setWebshopId(productItem.getWebshopId());
                    existingProduct.setQuantityUnit(productItem.getQuantityUnit());
        
        			// Update the modified product data
        			productsDAO.save(existingProduct);
        		}
        
        	}

        /**
         * Download an image and save it to the file system
         * 
         * @param address
         *            The URL of the image
         * @param filePath
         *            The folder to store the image
         * @param fileName
         *            The filename of the image
         */
        private void downloadImageFromUrl(IProgressMonitor monitor, String address, String filePath, String fileName) {
        
        	// Cancel if address or filename is empty
        	if (address.isEmpty() || filePath.isEmpty() || fileName.isEmpty())
        		return;
        
        	// First of all check, if the output file already exists.
        	Path outputFile = Paths.get(filePath, fileName);
        	if (Files.exists(outputFile))
        		return;
        
            // Connect to the web server
            URI u = URI.create(address);
            try (InputStream in = u.toURL().openStream()) {
        
                // Create the destination folder to store the file
                if (!Files.isDirectory(Paths.get(filePath)))
                    Files.createDirectories(outputFile);
                Files.copy(in, outputFile);
            }
            catch (MalformedURLException e) {
                //T: Status message importing data from web shop
                log.error(e, msg.importWebshopErrorMalformedurl + " " + address);
            }
            catch (IOException e) {
                //T: Status message importing data from web shop
                log.error(e, msg.importWebshopErrorCantopenpicture + " " + address);
            }
        }

//        /**
//         * Convert the payment method to a readable (and localized) text.
//         * 
//         * @param intext
//         *            order status
//         * @return payment method as readable (and localized) text
//         */
//        private String getPaymentMethodText(String intext) {
//        	String paymentstatustext = intext;
//        
//        	if (intext.equalsIgnoreCase("cod"))
//        		paymentstatustext = msg.importWebshopDataCashondelivery;
//        	else if (intext.equalsIgnoreCase("prepayment"))
//        		paymentstatustext = msg.importWebshopDataPrepayment;
//        	else if (intext.equalsIgnoreCase("creditcard"))
//        		paymentstatustext = msg.importWebshopDataCreditcard;
//        	else if (intext.equalsIgnoreCase("check"))
//        		paymentstatustext = msg.importWebshopDataCheque;
//        
//        	return paymentstatustext;
//        
//        }

        /**
         * Remove the HTML tags from the result
         * 
         * @return The formated run result string
         */
        protected String getRunResult() {
        	return runResult.replaceAll("\\<.*?\\>", "");
        }
	 }

}
