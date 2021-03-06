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

package org.fakturama.export.wizard.productbuyers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.fakturama.export.ExportMessages;
import org.fakturama.export.wizard.ExportWizardPageStartEndDate;
import org.fakturama.wizards.IExportWizard;
import org.fakturama.wizards.IFakturamaWizardService;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.Constants;


/**
 * Export wizard to export sales
 * 
 * @author Gerd Bartelt
 */
public class ProductBuyersExportWizard extends Wizard implements IExportWizard {

	@Inject
	@Translation
	protected Messages msg;
	
	@Inject
	@Translation
	protected ExportMessages exportMessages;

	@Inject
	private IEclipseContext ctx;
	
	// The first (and only) page of this wizard
	private ExportWizardPageStartEndDate page1;
	private ProductBuyersExportOptionPage page2;

	/**
	 * Constructor Adds the first page to the wizard
	 * Initializes this creation wizard using the passed workbench and object
	 * selection.
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 *      org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@PostConstruct
	@Override
	public void init(IWorkbench workbench, @Optional IStructuredSelection selection) {
		setWindowTitle(msg.pageExport);
		ctx.set(IFakturamaWizardService.WIZARD_TITLE, exportMessages.wizardExportProductandbuyersTitle);
		ctx.set(IFakturamaWizardService.WIZARD_DESCRIPTION, exportMessages.wizardExportBuyersLongdescription);
		ctx.set(ExportWizardPageStartEndDate.WIZARD_DATESELECT_DONTUSETIMEPERIOD, Boolean.FALSE);
		page1 = ContextInjectionFactory.make(ExportWizardPageStartEndDate.class, ctx);

		ctx.set(IFakturamaWizardService.WIZARD_DESCRIPTION, exportMessages.wizardExportAccountsTableListentriesTitle);
		page2 = ContextInjectionFactory.make(ProductBuyersExportOptionPage.class, ctx);

		addPage(page1);
		addPage(page2);
	}


	/**
	 * Performs any actions appropriate in response to the user having pressed
	 * the Finish button, or refuse if finishing now is not permitted.
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		ctx.set(Constants.PARAM_START_DATE, page1.getStartDate());
		ctx.set(Constants.PARAM_END_DATE, page1.getEndDate());
		ctx.set(ExportWizardPageStartEndDate.WIZARD_DATESELECT_DONTUSETIMEPERIOD, page1.getDoNotUseTimePeriod());
		ctx.set(ProductBuyersExportOptionPage.WIZARD_SORT_BY_QUANTITY, page2.getSortByQuantity());
		ProductBuyersExporter exporter = ContextInjectionFactory.make(ProductBuyersExporter.class, ctx);
		return exporter.export();
	}
}
