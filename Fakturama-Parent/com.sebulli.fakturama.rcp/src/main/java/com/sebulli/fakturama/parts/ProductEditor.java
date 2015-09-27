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

package com.sebulli.fakturama.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.sebulli.fakturama.dao.ProductCategoriesDAO;
import com.sebulli.fakturama.dao.ProductsDAO;
import com.sebulli.fakturama.dao.VatsDAO;
import com.sebulli.fakturama.handlers.CallEditor;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.model.ProductCategory;
import com.sebulli.fakturama.model.Product_;
import com.sebulli.fakturama.model.VAT;
import com.sebulli.fakturama.parts.converter.CategoryConverter;
import com.sebulli.fakturama.parts.converter.CommonConverter;
import com.sebulli.fakturama.parts.converter.EntityConverter;
import com.sebulli.fakturama.parts.converter.StringToCategoryConverter;
import com.sebulli.fakturama.parts.converter.StringToEntityConverter;
import com.sebulli.fakturama.parts.widget.FakturamaPictureControl;
import com.sebulli.fakturama.parts.widget.GrossText;
import com.sebulli.fakturama.parts.widget.NetText;
import com.sebulli.fakturama.parts.widget.contentprovider.EntityComboProvider;
import com.sebulli.fakturama.parts.widget.labelprovider.EntityLabelProvider;
import com.sebulli.fakturama.resources.ITemplateResourceManager;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.ProgramImages;

/**
 * The product editor
 */
public class ProductEditor extends Editor<Product> {
	
	private final int NUMBER_OF_PRICES = 5;

    @Inject
    protected VatsDAO vatDao;
    
    /**
     * Event Broker for sending update events to the list table
     */
    @Inject
    protected IEventBroker evtBroker;

    @Inject
    protected ProductCategoriesDAO productCategoriesDAO;
	
	@Inject
	private ITemplateResourceManager resourceManager;

    // Editor's ID
    public static final String ID = "com.sebulli.fakturama.editors.productEditor";

    public static final String EDITOR_ID = "ProductEditor";

	// This UniDataSet represents the editor's input 
	private Product editorProduct;
	
    private MPart part;
    
    @Inject
    private ProductsDAO productsDAO;
    
    @Inject
    protected VatsDAO vatsDao;

	// SWT widgets of the editor
	private Composite top;
	private Text textItemNr;
	private Text textName;
	private Text textGtin;
	private Text textDescription;
	private Combo comboVat;
	private Text textWeight;
	private Text textQuantity;
	private Text textQuantityUnit;
	private ComboViewer comboViewer;
	private Combo comboCategory;
	private FakturamaPictureControl labelProductPicture;
	private Composite photoComposite;

	// Widgets (and variables) for the scaled price.
	private Label[] labelBlock = new Label[NUMBER_OF_PRICES];
	private Text[] textBlock = new Text[NUMBER_OF_PRICES];
	private NetText[] netText = new NetText[NUMBER_OF_PRICES];
	private GrossText[] grossText = new GrossText[NUMBER_OF_PRICES];
	private Double[] net = new Double[NUMBER_OF_PRICES];
//	MonetaryAmount defaultPrice = Money.of(0.0, DataUtils.getInstance().getDefaultCurrencyUnit());
	Double defaultPrice = Double.valueOf(0.0);
	private int scaledPrices;

	// These flags are set by the preference settings.
	// They define if elements of the editor are displayed or not.
	private boolean useWeight;
	private boolean useQuantity;
	private boolean useQuantityUnit;
	private boolean useItemNr;
	private boolean useNet;
	private boolean useGross;
	private boolean useVat;
	private boolean useDescription;
	private boolean usePicture;

	// These are (non visible) values of the document
//    private VAT vat = null;
	private Display display;

	// defines, if the product is new created
	private boolean newProduct;

	/**
	 * Saves the contents of this part
	 * 
	 * @param monitor
	 *            Progress monitor
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Persist
	public void doSave(IProgressMonitor monitor) {

		/*
		 * the following parameters are not saved:
		 * - id (constant) 
		 * - options (not yet implemented)
		 * - date_added (not modified by editor)
		 */

		if (newProduct) {
			// Check, if the item number is the next one
			int result = setNextNr(textItemNr.getText(), Product_.itemNumber.getName());

			// It's not the next free ID
			if (result == ERROR_NOT_NEXT_ID) {
				// Display an error message
				MessageDialog.openError(top.getShell(),

				//T: Title of the dialog that appears if the item/product number is not valid.
				msg.editorProductErrorItemnumberTitle,
				
				//T: Text of the dialog that appears if the item/product number is not valid.
				 MessageFormat.format(msg.editorProductErrorItemnumberNotnextfree, textItemNr.getText())+ "\n" +
						//T: Text of the dialog that appears if the number is not valid.
						msg.editorContactHintSeepreferences);
			}

		}

		// Always set the editor's data set to "undeleted"
		editorProduct.setDeleted(Boolean.FALSE);

		// Set the product data
        // ... done through databinding...

		int i;
		Double lastScaledPrice = Double.valueOf(0.0);

		try {
			// Set all of the scaled prices (according to the count of possible scales from settings)
			for (i = 0; i < scaledPrices; i++) {
				String methodName = String.format("getPrice%d", i+1);
				Object obj = MethodUtils.invokeExactMethod(editorProduct, methodName);
				lastScaledPrice = (Double) obj;
				// blocks are set via databinding
			}
		
		// if not all 5 scales are set we set the remaining prices to the last scaled price
			for (; i < 5; i++) {
				String methodName = String.format("setPrice%d", i+1);
				MethodUtils.invokeExactMethod(editorProduct, methodName, lastScaledPrice);
			}
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Set the product data
        // ... done through databinding...

		// If it is a new product, add it to the product list and
		// to the data base
		if (newProduct) {
			newProduct = false;

		}
		// If it's not new, update at least the data base
		else {
//			Data.INSTANCE.getProducts().updateDataSet(product);
		}
			try {
				editorProduct = productsDAO.save(editorProduct);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		// Set the Editor's name to the product name.
		this.part.setLabel(editorProduct.getName());

		// Refresh the table view of all contacts
        evtBroker.post(EDITOR_ID, "update");
        
        // reset dirty flag
        getMDirtyablePart().setDirty(false);

	}

	/**
	 * Initializes the editor. If an existing data set is opened, the local
	 * variable "product" is set to This data set. If the editor is opened to
	 * create a new one, a new data set is created and the local variable
	 * "product" is set to this one.
	 * 
	 * @param input
	 *            The editor's input
	 * @param site
	 *            The editor's site
	 */
	@PostConstruct
	public void init(Composite parent) {
        this.part = (MPart) parent.getData("modelElement");
        this.part.setIconURI(Icon.COMMAND_PRODUCT.getIconURI());
        
String tmpObjId = (String) part.getProperties().get(CallEditor.PARAM_OBJ_ID);
if (StringUtils.isNumeric(tmpObjId)) {
    Long objId = Long.valueOf(tmpObjId);
    // Set the editor's data set to the editor's input
    this.editorProduct = productsDAO.findById(objId);
}

// initialize prices
Arrays.fill(net, defaultPrice);

		// Test if the editor is opened to create a new data set. This is,
		// if there is no input set.
		newProduct = (editorProduct == null);

		// If new ..
		if (newProduct) {

			// Create a new data set
			editorProduct = modelFactory.createProduct();
            String category = (String) part.getProperties().get(CallEditor.PARAM_CATEGORY);
            if(StringUtils.isNotEmpty(category)) {
                ProductCategory newCat = productCategoriesDAO.getCategory(category, false);
                editorProduct.setCategories(newCat);
            }
            
			//T: Header of product editor
            part.setLabel(msg.mainMenuNewProductName);

			// Set the vat to the standard value
            int vatId = defaultValuePrefs.getInt(Constants.DEFAULT_VAT);
            VAT vat = vatsDao.findById(vatId);  // initially set default VAT
			editorProduct.setVat(vat);

			// Get the next item number
			editorProduct.setItemNumber(getNextNr());

		}
		else {

			// Set the Editor's name to the product name.
			part.setLabel(editorProduct.getName());
		}
		  
        createPartControl(parent);
	}

//	/**
//	 * Set the variable picturePath to the path of the product picture, which is
//	 * a combination of the selected workspace, the /pics/products/ folder and
//	 * the product name.
//	 * 
//	 * Also update the text widget textProductPicturePath which is displayed
//	 * under the product picture.
//	 */
//	private void createPicturePathFromPictureName() {
//
//		// Get the workspace
//		filename1 = defaultValuePrefs.getString(Constants.GENERAL_WORKSPACE);
//
//		// add the picture sub folder
//		filename2 = Constants.PRODUCT_PICTURE_FOLDER;
//
//		// Set the variables
//		picturePath = filename1 + filename2;
//		filename2 += pictureName;
//
//		// Display the text under the product picture
//		if (textProductPicturePath != null) {
//			textProductPicturePath.setText(filename2);
//		}
//	}
//
//	/**
//	 * Create the picture name based on the product's item number
//	 */
//	private void createPictureName() {
//
//		pictureName = createPictureName(textName.getText(), textItemNr.getText());
//
//		// Add the full path.
//		createPicturePathFromPictureName();
//	}
//
//	/**
//	 * Create the picture name based on the product's item number. Remove illegal
//	 * characters and add a ".jpg"
//	 * 
//	 * @param name
//	 *            The name of the product
//	 * @param itemNr
//	 *            The item number of the product
//	 * @return Picture name as String
//	 */
//	public String createPictureName(String name, String itemNr) {
//
//		String pictureName;
//
//		// Get the product's item number
//		pictureName = itemNr;
//
//		// If the product name is different to the item number,
//		// add also the product name to the pictures name
//		if (!name.equals(itemNr))
//			pictureName += "_" + name;
//
//		// Remove all illegal characters that are not allowed as file name.
//		final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':', ' ', '.' };
//		for (char c : ILLEGAL_CHARACTERS)
//			pictureName = pictureName.replace(c, '_');
//
//		// Add the .*jpg
//		pictureName += ".jpg";
//
//		return pictureName;
//	}

	/**
	 * Reload the product picture
	 */
	private void setPicture() {

		try {
			// Display the picture, if a product picture is set.
			if (editorProduct.getPicture() != null) {

				// Load the image, based on the picture name
//				Image image = new Image(display, filename1 + filename2);
				ByteArrayInputStream imgStream = new ByteArrayInputStream(editorProduct.getPicture());
				Image image = new Image(display, imgStream);

				// Get the pictures size
				int width = image.getBounds().width;
				int height = image.getBounds().height;

				// Maximum picture width is 250px
				if (width > 250) {
					height = 250 * height / width;
					width = 250;
				}

				// Rescale the picture to maximum 250px width
				Image scaledImage = new Image(display, image.getImageData().scaledTo(width, height));
				
				labelProductPicture.setDefaultImage(scaledImage);
			}
			// Display an empty background if no picture is set.
			else {
				try {
					Image prodImage = resourceManager.getProgramImage(display, ProgramImages.NO_PICTURE);
					labelProductPicture.setDefaultImage(prodImage);
				}
				catch (Exception e1) {
					log.error(e1, "Icon not found");
				}
			}
		}
		catch (Exception e) {
			// Show an error icon, if the picture is not found
			try {
				Image prodImage = resourceManager.getProgramImage(display, ProgramImages.NOT_FOUND_PICTURE);
				labelProductPicture.setDefaultImage(prodImage);
			}
			catch (Exception e1) {
				log.error(e1, "Icon not found");
			}
		}
	}

	/**
	 * Creates the SWT controls for this workbench part
	 * 
	 * @param the
	 *            parent control
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createPartControl(final Composite parent) {

		// Get a reference to the display
		display = parent.getDisplay();

		// Some of this editos's control elements can be hidden.
		// Get the these settings from the preference store
		useItemNr = defaultValuePrefs.getBoolean("PRODUCT_USE_ITEMNR");
		useDescription = defaultValuePrefs.getBoolean("PRODUCT_USE_DESCRIPTION");
		scaledPrices = defaultValuePrefs.getInt("PRODUCT_SCALED_PRICES");
		useWeight = defaultValuePrefs.getBoolean("PRODUCT_USE_WEIGHT");
		useNet = (defaultValuePrefs.getInt("PRODUCT_USE_NET_GROSS") != 2);
		useGross = (defaultValuePrefs.getInt("PRODUCT_USE_NET_GROSS") != 1);
		useVat = defaultValuePrefs.getBoolean("PRODUCT_USE_VAT");
		usePicture = defaultValuePrefs.getBoolean("PRODUCT_USE_PICTURE");
		useQuantity = defaultValuePrefs.getBoolean("PRODUCT_USE_QUANTITY");
		useQuantityUnit = defaultValuePrefs.getBoolean("PRODUCT_USE_QUNIT");
		
		// Get the product VAT
		//	done by databinding

		// Create the ScrolledComposite to scroll horizontally and vertically
	    ScrolledComposite scrollcomposite = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);

		// Create the top Composite
		top = new Composite(scrollcomposite, SWT.SCROLLBAR_OVERLAY | SWT.NONE );  //was parent before 
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(top);
		
		scrollcomposite.setContent(top);
		scrollcomposite.setMinSize(1000, 600);   // 2nd entry should be adjusted to higher value when new fields will be added to composite 
		scrollcomposite.setExpandHorizontal(true);
		scrollcomposite.setExpandVertical(true);
        scrollcomposite.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,true));

		// Create an invisible container for all hidden components
		Composite invisible = new Composite(top, SWT.NONE);
		invisible.setVisible(false);
		GridDataFactory.fillDefaults().hint(0, 0).span(2, 1).applyTo(invisible);

		// Add context help reference 
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ContextHelpConstants.PRODUCT_EDITOR);
		
		// Group: Product description
		Group productDescGroup = new Group(top, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(productDescGroup);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(productDescGroup);

		productDescGroup.setText(msg.commonFieldDescription);

		// Item number
		Label labelItemNr = new Label(useItemNr ? productDescGroup : invisible, SWT.NONE);
		labelItemNr.setText(msg.exporterDataItemnumber);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelItemNr);
		textItemNr = new Text(useItemNr ? productDescGroup : invisible, SWT.BORDER);
		bindModelValue(editorProduct, textItemNr, Product_.itemNumber.getName(), 64);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textItemNr);

		// Product name
		Label labelName = new Label(productDescGroup, SWT.NONE);
		labelName.setText(msg.commonFieldName);
		labelName.setToolTipText(msg.editorProductNameTooltip);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelName);
		textName = new Text(productDescGroup, SWT.BORDER);
		textName.setToolTipText(labelName.getToolTipText());
		bindModelValue(editorProduct, textName, Product_.name.getName(), 64);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textName);

		// Product category
		Label labelCategory = new Label(productDescGroup, SWT.NONE);
		labelCategory.setText(msg.commonFieldCategory);
		labelCategory.setToolTipText(msg.editorProductCategoryTooltip);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelCategory);

		createCategoryCombo(productDescGroup);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(comboCategory);
        
        // GTIN
		Label labelGtin = new Label(productDescGroup, SWT.NONE);
		labelGtin.setText(msg.editorProductFieldGtin);
//		labelGtin.setToolTipText(msg.editorProductFieldGtinTooltip);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelGtin);
		textGtin = new Text(productDescGroup, SWT.BORDER);
//		textGtin.setToolTipText(labelGtin.getToolTipText());
		bindModelValue(editorProduct, textGtin, Product_.gtin.getName(), 64);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textGtin);

		// Product description
		Label labelDescription = new Label(useDescription ? productDescGroup : invisible, SWT.NONE);
		labelDescription.setText(msg.commonFieldDescription);
		//T: Tool Tip Text
		labelDescription.setToolTipText(msg.editorProductAdddescriptionTooltip);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelDescription);
		textDescription = new Text(useDescription ? productDescGroup : invisible, SWT.BORDER | SWT.MULTI);
//		textDescription.setText(DataUtils.makeOSLineFeeds(editorProduct.getStringValueByKey("description")));
		textDescription.setToolTipText(labelDescription.getToolTipText());
		bindModelValue(editorProduct, textDescription, Product_.description.getName(), 250);
		GridDataFactory.fillDefaults().hint(10, 80).grab(true, false).applyTo(textDescription);

		// Product quantity
		Label labelQuantityUnit = new Label(useQuantityUnit ? productDescGroup : invisible, SWT.NONE);
		//T: Product Editor - Label Product quantity unit
		labelQuantityUnit.setText(msg.editorProductFieldQuantityunitName);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelQuantityUnit);
		textQuantityUnit = new Text(useQuantityUnit ? productDescGroup : invisible, SWT.BORDER);
		bindModelValue(editorProduct, textQuantityUnit, Product_.quantityUnit.getName(), 16);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textQuantityUnit);

		
		// Product price
		Label labelPrice = new Label(productDescGroup, SWT.NONE);

		// Use net or gross price
		if (useNet && useGross)
			//T: Label in the product editor
			labelPrice.setText(msg.commonFieldPrice);
		else if (useNet)
			//T: Label in the product editor
			labelPrice.setText(msg.editorProductFieldPriceName);
		else if (useGross)
			//T: Label in the product editor
			labelPrice.setText(msg.editorProductFieldGrosspriceName);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelPrice);

		// Create a container composite for the scaled price
		Composite pricetable = new Composite(productDescGroup, SWT.NONE);
		GridLayoutFactory.swtDefaults().margins(0, 0).numColumns((scaledPrices > 1) ? (useNet && useGross) ? 4 : 3 : 2).applyTo(pricetable);

		// If there is a net and gross column, and 2 columns for the quantity
		// there are 2 cells in the top left corner, that are empty
		if ((scaledPrices >= 2) && useNet && useGross) {
			new Label(pricetable, SWT.NONE);
			new Label(pricetable, SWT.NONE);
		}

		// Display the heading for the net and gross columns
		if (useNet && useGross) {
			Label labelNet = new Label(pricetable, SWT.CENTER);
			labelNet.setText(msg.productDataNet);
			Label labelGross = new Label(pricetable, SWT.CENTER);
			labelGross.setText(msg.productDataGross);
		}
		
		Map<Integer, PriceBlock> priceBlocks = createPriceBlocks();
		
		// Create a row for each entry of the scaled price table
		for (int i = 0; i < 5; i++) {
			Object priceObj;
			try {

				// Get the net price scaled price
				String methodName = String.format("getPrice%d", i+1);
				priceObj = MethodUtils.invokeExactMethod(editorProduct, methodName);
				net[i] = (priceObj != null) ? (Double) priceObj : defaultPrice;

				// Create the columns for the quantity
				labelBlock[i] = new Label(((i < scaledPrices) && (scaledPrices >= 2)) ? pricetable : invisible,
						SWT.NONE);
				// T: Product Editor - Label Scaled Prices "from" .. Quantity
				// the price is ..
				labelBlock[i].setText(msg.editorProductLabelFrom);

				textBlock[i] = new Text(((i < scaledPrices) && (scaledPrices >= 2)) ? pricetable : invisible,
						SWT.BORDER | SWT.RIGHT);
				methodName = String.format("getBlock%d", i+1);
				priceObj = MethodUtils.invokeExactMethod(editorProduct, methodName);
				textBlock[i].setText(Integer.toString((Integer) priceObj));
				bindModelValue(editorProduct, textBlock[i], priceBlocks.get(i).getBlock().getName(), 6);
				GridDataFactory.swtDefaults().hint(40, SWT.DEFAULT).applyTo(textBlock[i]);

				// Create the net columns
				if (useNet) {
					netText[i] = new NetText((i < scaledPrices) ? pricetable : invisible, SWT.BORDER | SWT.RIGHT,
							net[i], editorProduct.getVat().getTaxValue());
					bindModelValue(editorProduct, netText[i].getNetText(), priceBlocks.get(i).getPrice().getName(), 6);
					GridDataFactory.swtDefaults().hint(80, SWT.DEFAULT).applyTo(netText[i].getNetText().getControl());
				}

				// Create the gross columns
				if (useGross) {
					grossText[i] = new GrossText((i < scaledPrices) ? pricetable : invisible, SWT.BORDER | SWT.RIGHT,
							net[i], editorProduct.getVat().getTaxValue());
//					bindModelValue(editorProduct, grossText[i].getGrossText(), priceBlocks.get(i).getPrice().getName(), 6);
					GridDataFactory.swtDefaults().hint(80, SWT.DEFAULT)
							.applyTo(grossText[i].getGrossText().getControl());
				}

				// If a net and gross column was created, link both together,
				// so, if one is modified, the other will be recalculated.
				if (useNet && useGross) {
					netText[i].setGrossText(grossText[i]);
					grossText[i].setNetText(netText[i]);
				}
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		// Set the tab order
//		if (scaledPrices >= 2)
//			setTabOrder(textDescription, textBlock[0]);
//		else if (useNet)
//			setTabOrder(textDescription, netText[0].getNetText());
//		else
//			setTabOrder(textDescription, grossText[0].getGrossText());

		// product VAT
		Label labelVat = new Label(useVat ? productDescGroup : invisible, SWT.NONE);
        labelVat.setText(msg.commonFieldVat);
		labelVat.setToolTipText(msg.editorProductVatName);
		
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelVat);
//		
        // VAT combo list
        comboVat = new Combo(useVat ? productDescGroup : invisible, SWT.BORDER);
		comboVat.setToolTipText(labelVat.getToolTipText());
        List<VAT> allVATs = vatsDao.findAll();
        comboViewer = new ComboViewer(comboVat);
        comboViewer.setContentProvider(new EntityComboProvider());
        comboViewer.setLabelProvider(new EntityLabelProvider());
		comboViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				// Handle selection changed event 
				ISelection selection = event.getSelection();
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (!structuredSelection.isEmpty()) {
                    // Get the first element ...
                    Object firstElement = structuredSelection.getFirstElement();

                    // Get the selected VAT
                    VAT uds = (VAT) firstElement;

                    // Store the old value
                    Double oldVat = editorProduct.getVat().getTaxValue();

                    // Get the new value
//                    vatId = uds.getId();
//                    vat = uds;

					// Recalculate all the price values
					for (int i = 0; i < scaledPrices; i++) {

						// Recalculate the price values if gross is selected,
						// So the gross value will stay constant.
						if (!useNet) {
							net[i] = net[i] * ((1 + oldVat) / (1 + uds.getTaxValue()));
						}

						// Update net and gross text widget
						if (netText[i] != null)
							netText[i].setVatValue(uds.getTaxValue());
						if (grossText[i] != null)
							grossText[i].setVatValue(uds.getTaxValue());
					}
				}
			}
		});

		// Create a JFace combo viewer for the VAT list
        comboViewer.setInput(allVATs);

        UpdateValueStrategy vatModel2Target = new UpdateValueStrategy();
        vatModel2Target.setConverter(new EntityConverter<VAT>(VAT.class));
        
        UpdateValueStrategy target2VatModel = new UpdateValueStrategy();
        target2VatModel.setConverter(new StringToEntityConverter<VAT>(allVATs, VAT.class));
//		GridDataFactory.fillDefaults().grab(true, false).applyTo(comboVat);
        bindModelValue(editorProduct, comboVat, Product_.vat.getName()/* + "." + VAT_.name.getName()*/,
                target2VatModel, vatModel2Target);

		// Product weight
		Label labelWeight = new Label(useWeight ? productDescGroup : invisible, SWT.NONE);
		//T: Product Editor - Label Product Weight with unit (kg)
		labelWeight.setText(msg.exporterDataWeight);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelWeight);
		textWeight = new Text(useWeight ? productDescGroup : invisible, SWT.BORDER);
		bindModelValue(editorProduct, textWeight, Product_.weight.getName(), 16);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textWeight);

		// Product quantity
		Label labelQuantity = new Label(useQuantity ? productDescGroup : invisible, SWT.NONE);
		//T: Product Editor - Label Product quantity
		labelQuantity.setText(msg.commonFieldQuantity);

		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER).applyTo(labelQuantity);
		textQuantity = new Text(useQuantity ? productDescGroup : invisible, SWT.BORDER);
		bindModelValue(editorProduct, textQuantity, Product_.quantity.getName(), 16);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textQuantity);

		// Group: Product picture
		Group productPictureGroup = new Group(usePicture ? top : invisible, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(1).applyTo(productPictureGroup);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(productPictureGroup);
		productPictureGroup.setText(msg.exporterDataPicture);

		// The photo
		photoComposite = new Composite(productPictureGroup, SWT.BORDER);
		GridLayoutFactory.swtDefaults().margins(10, 10).numColumns(1).applyTo(photoComposite);
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(photoComposite);
		photoComposite.setBackground(new Color(null, 255, 255, 255));

		// The picture name label
		labelProductPicture = new FakturamaPictureControl(photoComposite, defaultValuePrefs, msg);
//		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, false).applyTo(labelProductPicture);

//		 The picture path
//      ==> this is not longer necessary because we store the picture in database

		// Load the picture from the picture path
//		createPicturePathFromPictureName();		
		setPicture();
		
		labelProductPicture.addPropertyChangeListener(FakturamaPictureControl.IMAGE_BYTEARRAY_PROPERTY,
			    new PropertyChangeListener() {
	        public void propertyChange(PropertyChangeEvent event) {
	            byte[] newImage = (byte[]) event.getNewValue();
	            // if image was deleted we use the default image
	            if(newImage == null) {
	            	editorProduct.setPicture(labelProductPicture.getImageByteArray());
	            } else {
	            	editorProduct.setPicture(newImage);
	            }
	            getMDirtyablePart().setDirty(true);
	        }
		});

	}

    private Map<Integer, PriceBlock> createPriceBlocks() {
		Map<Integer, PriceBlock> priceBlocks = new HashMap<>();
		int index = 0;
		priceBlocks.put(index++, new PriceBlock(Product_.block1, Product_.price1));
		priceBlocks.put(index++, new PriceBlock(Product_.block2, Product_.price2));
		priceBlocks.put(index++, new PriceBlock(Product_.block3, Product_.price3));
		priceBlocks.put(index++, new PriceBlock(Product_.block4, Product_.price4));
		priceBlocks.put(index++, new PriceBlock(Product_.block5, Product_.price5));
		return priceBlocks;
	}

	/**
     * creates the combo box for the Product category
     * @param parent 
     */
    private void createCategoryCombo(Group parent) {
        // Collect all category strings as a sorted Set
        final TreeSet<ProductCategory> categories = new TreeSet<ProductCategory>(new Comparator<ProductCategory>() {
            @Override
            public int compare(ProductCategory cat1, ProductCategory cat2) {
                return StringUtils.defaultString(cat1.getName()).compareTo(StringUtils.defaultString(cat2.getName()));
            }
        });
        categories.addAll(productCategoriesDAO.findAll());

        comboCategory = new Combo(parent, SWT.BORDER);
        ComboViewer viewer = new ComboViewer(comboCategory);
        viewer.setContentProvider(new ArrayContentProvider() {
            @Override
            public Object[] getElements(Object inputElement) {
                return categories.toArray();
            }
        });
        
        // Add all categories to the combo
        viewer.setInput(categories);
        viewer.setLabelProvider(new LabelProvider() {
            @Override
            public String getText(Object element) {
                return element instanceof ProductCategory ? CommonConverter.getCategoryName((ProductCategory)element, "") : null;
            }
        });

        UpdateValueStrategy productCatModel2Target = new UpdateValueStrategy();
        productCatModel2Target.setConverter(new CategoryConverter<ProductCategory>(ProductCategory.class));
        
        UpdateValueStrategy target2productCatModel = new UpdateValueStrategy();
        target2productCatModel.setConverter(new StringToCategoryConverter<ProductCategory>(categories, ProductCategory.class));
        bindModelValue(editorProduct, comboCategory, Product_.categories.getName(), target2productCatModel, productCatModel2Target);
    }

	/**
	 * Test, if there is a document with the same number
	 * 
	 * @return TRUE, if one with the same number is found
	 */
	public boolean thereIsOneWithSameNumber() {

		// Cancel, if there is already a document with the same ID
		if (productsDAO.existsOther(editorProduct)) {
			// Display an error message
			// T: Title of the dialog that appears if the item/product number is not valid.
	    MessageDialog.openError(top.getShell(), 
	    		msg.editorProductErrorItemnumberTitle, 
	    		msg.editorProductWarningDuplicatearticle+ " " + textName.getText());

			return true;
		}
		// Cancel, if there is already a document with the same ID
		if (productsDAO.existsOther(editorProduct)) {
			// Display an error message
		    MessageDialog.openError(top.getShell(), 
		    		msg.editorDocumentErrorDocnumberTitle, 
		    		msg.editorDocumentDialogWarningDocumentexists+ " " + textName.getText());
			return true;
		}

		return false;
	}

	/**
	 * Returns, if save is allowed
	 * 
	 * @return TRUE, if save is allowed
	 * 
	 * @see com.sebulli.fakturama.editors.Editor#saveAllowed()
	 */
	@Override
	protected boolean saveAllowed() {
		// Save is allowed, if there is no product with the same number
		return !thereIsOneWithSameNumber();
	}
	
    @Override
    protected MDirtyable getMDirtyablePart() {
        return part;
    }
    
    @Override
    protected Class<Product> getModelClass() {
        return Product.class;
    }
    
    @Override
    protected String getEditorID() {
    	return "Product";
    }

    /**
     * Helper class for connecting blocks with prices in a correct order.
     * 
     */
    class PriceBlock {
    	private SingularAttribute<Product, Integer> block;
    	private SingularAttribute<Product, Double> price;
		
		/**
		 * @param block
		 * @param price
		 */
		public PriceBlock(SingularAttribute<Product, Integer> block,
				SingularAttribute<Product, Double> price) {
			this.block = block;
			this.price = price;
		}
		
		/**
		 * @return the block
		 */
		public final SingularAttribute<Product, Integer> getBlock() {
			return block;
		}
		/**
		 * @return the price
		 */
		public final SingularAttribute<Product, Double> getPrice() {
			return price;
		}
    }
}
