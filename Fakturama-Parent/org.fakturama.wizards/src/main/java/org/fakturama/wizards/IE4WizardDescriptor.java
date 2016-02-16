package org.fakturama.wizards;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;

/**
 * Base interface for all wizards defined via workbench extension points.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @since 3.1
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IE4WizardDescriptor {
	
	public String getName();
	public void setName(String name);

	/**
	 * Answer the selection for the receiver based on whether the it can handle
	 * the selection. If it can return the selection. If it can handle the
	 * adapted to IResource value of the selection. If it satisfies neither of
	 * these conditions return an empty IStructuredSelection.
	 * 
	 * @return IStructuredSelection
	 * @param selection
	 *            IStructuredSelection
	 */
	IStructuredSelection adaptedSelection(IStructuredSelection selection);
	
	/**
	 * Create a wizard.
	 * 
	 * @return the wizard 
	 * @throws CoreException 
	 */
	IWorkbenchWizard createWizard() throws CoreException;

	/**
	 * Return the description.
	 * 
	 * @return the description
	 */
	String getDescription();
	
	/**
	 * Return the tags associated with this wizard.
	 * 
	 * @return the tags associated with this wizard
	 */
	String[] getTags();
	
	/**
	 * Return the description image for this wizard.
	 * 
	 * @return the description image for this wizard or <code>null</code>
	 */
	public Image getDescriptionImage();

	/**
	 * Return the help system href for this wizard.
	 * 
	 * @return the help system href for this wizard or <code>null</code>
	 */
	String getHelpHref();	

	/**
	 * Return the category for this wizard.  
	 * 
	 * @return the category or <code>null</code>
	 */
	IE4WizardCategory getCategory();
	
	/**
	 * Answer <code>true</code> if this wizard is able to finish without
	 * loading any pages. This is a hint to any
	 * {@link org.eclipse.jface.wizard.WizardSelectionPage} or container that
	 * may contain this wizard to allow the finish button to be pressed without
	 * actually entering the wizard. If this occurs the
	 * {@link org.eclipse.jface.wizard.IWizard#performFinish()} method should be
	 * invoked by the containing wizard without creating any pages.
	 * 
	 * @return <code>true</code> if this wizard can finish immediately
	 */
	boolean canFinishEarly();

	/**
	 * Answer <code>true</code> if this wizard has any pages. This is a
	 * hint to any {@link org.eclipse.jface.wizard.WizardSelectionPage} or
	 * container that may contain this wizard that they should enable the "Next"
	 * button, if appropriate.
	 * 
	 * @return <code>true</code> if this wizard has wizard pages
	 */
	boolean hasPages();
	public void setDescription(String description);
	public void setCategory(IE4WizardCategory category);
	public void setDescriptionImage(Image descriptionImage);
	public String getId();
	
}
