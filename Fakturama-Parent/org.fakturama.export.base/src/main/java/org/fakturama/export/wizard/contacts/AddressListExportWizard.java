package org.fakturama.export.wizard.contacts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.fakturama.export.wizard.EmptyWizardPage;
import org.fakturama.wizards.IExportWizard;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.resources.ITemplateResourceManager;
import com.sebulli.fakturama.resources.core.ProgramImages;

/**
 * Export wizard to export sales
 * 
 */
public class AddressListExportWizard extends Wizard implements IExportWizard {

	@Inject
//	@Translation
	protected Messages msg;
	
	@Inject
	private ITemplateResourceManager resourceManager;
    
	// The first (and only) page of this wizard
	private EmptyWizardPage page1;

	@Inject
	private IEclipseContext ctx;
	
	/**
	 * Adds the first page to the wizard
	 */
	public AddressListExportWizard() { }
	
	@PostConstruct
	public void initialize() {
		//T: Title of the export wizard
		setWindowTitle(msg.pageExport);
		//T: Title of the export wizard
		Image previewImage = resourceManager.getProgramImage(Display.getCurrent(), ProgramImages.EXPORT_CONTACTS);
		ctx.set("title", msg.wizardExportContactsAllcontactsTitle);
		ctx.set("description", msg.wizardExportContactsAllcontactsDescription);
		ctx.set("previewimage", previewImage);
		page1 = ContextInjectionFactory.make(EmptyWizardPage.class, ctx);
		addPage(page1);
	}

	/**
	 * Performs any actions appropriate in response to the user having pressed
	 * the Finish button, or refuse if finishing now is not permitted.
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		AddressListExport exporter = ContextInjectionFactory.make(AddressListExport.class, ctx);
		return exporter.export();
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
//		initialize();
	}


}