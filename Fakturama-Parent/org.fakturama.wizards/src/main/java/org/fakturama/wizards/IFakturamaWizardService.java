/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2016 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package org.fakturama.wizards;

import java.util.List;

import org.eclipse.jface.wizard.IWizardNode;

/**
 * Service interface for exporter and importer services.
 *
 */
public interface IFakturamaWizardService {

	String WIZARD_TITLE = "title";
	String WIZARD_DESCRIPTION = "description";
	String WIZARD_PREVIEW_IMAGE = "previewimage";

	/**
	 * Retrieve a {@link List} of {@link IWizardNode}s which this service offers.
	 * @return
	 * @deprecated now used with extension point "exportWizard"
	 */
	public List<WizardEntry> getExporterList();

	/**
	 * Creates the given wizard. 
	 * 
	 * @param className 
	 * @return
	 */
	public IWorkbenchWizard createWizard(String className);
	
	/**
	 * Get the information about that plugin that holds the extension point with
	 * the list of wizards.
	 * 
	 * @return plugin id
	 */
	public String getExtensionPointPlugin();
}
