/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;

/**
 * Handler for calling web shop import actions or getting status values from web shop. This
 * class is the main entry point for all actions concerning the web shop activities.
 *
 */
public class WebShopCallHandler {

    @Inject
    @Translation
    protected Messages msg;

    @Inject @Optional
    private IPreferenceStore preferences;
    
    @Inject 
    private ILogger log;


//    @Inject
//    private IEventBroker evtBroker;

    /**
     * The action which has to be called.
     */
    public static final String PARAM_ACTION = "org.fakturama.webshop.connector.action";
    
    /**
     * Action for getting all the possible status from web shop.
     */
    public static final String WEBSHOP_CONNECTOR_ACTION_STATUS = "get-status";
    
    /**
     * Action for importing new orders from web shop.
     */
    public static final String WEBSHOP_CONNECTOR_ACTION_IMPORT = "import-orders";

	/**
	 * Prepare the web shop import to request products and orders or to change
	 * the state of an order.
	 */
	public static final String PARAM_IS_GET_PRODUCTS = "com.sebulli.fakturama.webshopimport.prepareGetProductsAndOrders";

	@Execute
	public void execute( 
	        @Named(PARAM_ACTION) String action,
	        @Optional @Named(PARAM_IS_GET_PRODUCTS) String prepareGetProductsAndOrders,
            final MApplication application
            ) throws ExecutionException {
		
		switch(action) {
		case WEBSHOP_CONNECTOR_ACTION_STATUS:
			break;
		case WEBSHOP_CONNECTOR_ACTION_IMPORT:
			break;
		default:
			log.error("das geht nich!");
		}
	}
}
