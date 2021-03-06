/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2015 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.handlers;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.parts.Editor;

/**
 * Handler for removing an invoice reference from a delivery document.
 *
 */
public class RemoveInvoiceReferenceHandler {

    /**
     * Event Broker for sending update events to the list table
     */
    @Inject
    protected IEventBroker evtBroker;

    @Inject
    protected ESelectionService selectionService;

    @Inject
    private DocumentsDAO documentsDAO;

    @Inject
    private ILogger log;

    @SuppressWarnings("unchecked")
    @CanExecute
    public boolean canExecute() {
        boolean retval = false;
        Object selection = selectionService.getSelection();
        if (selection != null) {
            if (selection instanceof Document) {
                retval = ((Document) selection).getBillingType() == BillingType.DELIVERY;
            } else if (selection instanceof List) {
                retval = ((List<Document>) selection).stream().allMatch(d -> d.getBillingType() == BillingType.DELIVERY);
            }
        }
        return retval;
    }

	@Execute
	public void run() {
		@SuppressWarnings("unchecked")
		List<Document> uds = (List<Document>) selectionService.getSelection();
		for (Document document : uds) {
			try {

				document = documentsDAO.update(document);
				// remove the reference
				document.setInvoiceReference(null);

				// also in the database
				documentsDAO.save(document);
			} catch (FakturamaStoringException e) {
				log.error(e);
			}

			// Refresh the table with delivery notes.
			evtBroker.post(DocumentEditor.EDITOR_ID, Editor.UPDATE_EVENT);
		}
	}
}
