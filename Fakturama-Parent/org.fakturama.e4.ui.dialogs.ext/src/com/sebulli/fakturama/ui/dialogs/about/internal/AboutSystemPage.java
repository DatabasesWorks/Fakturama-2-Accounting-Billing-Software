package com.sebulli.fakturama.ui.dialogs.about.internal;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.sebulli.fakturama.ui.dialogs.WorkbenchMessages;

/**
 * Displays system information about the eclipse application. The content of
 * what is displayed is selectable through the
 * <code>org.eclipse.ui.systemSummaryExtensions</code> extension point.
 */
public final class AboutSystemPage extends ProductInfoPage {

	// This id is used when the system page is opened in its own dialog
	private static final String ID = "productInfo.system"; //$NON-NLS-1$

	private final static int BROWSE_ERROR_LOG_BUTTON = IDialogConstants.CLIENT_ID;

	private final static int COPY_TO_CLIPBOARD_BUTTON = IDialogConstants.CLIENT_ID + 1;

	private Text text;

	@Override
	public void createControl(Composite parent) {
		// FIXME HelpSystem
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
		// IWorkbenchHelpContextIds.SYSTEM_SUMMARY_DIALOG);

		Composite outer = createOuterComposite(parent);

		text = new Text(outer, SWT.MULTI | SWT.BORDER | SWT.READ_ONLY | SWT.V_SCROLL | SWT.NO_FOCUS | SWT.H_SCROLL);
		text.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
		GridData gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
		gridData.grabExcessVerticalSpace = true;
		gridData.grabExcessHorizontalSpace = true;
		gridData.heightHint = convertVerticalDLUsToPixels(300);
		gridData.widthHint = convertHorizontalDLUsToPixels(400);
		text.setLayoutData(gridData);
		text.setFont(JFaceResources.getTextFont());
		fetchConfigurationInfo(text);
		setControl(outer);
	}

	@Override
	public void createPageButtons(Composite parent) {
		Button button = createButton(parent, BROWSE_ERROR_LOG_BUTTON,
				WorkbenchMessages.AboutSystemDialog_browseErrorLogName);
		String filename = Platform.getLogFileLocation().toOSString();
		button.setEnabled(new File(filename).exists());

		createButton(parent, COPY_TO_CLIPBOARD_BUTTON, WorkbenchMessages.AboutSystemDialog_copyToClipboardName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.internal.about.ProductInfoPage#getId()
	 */
	@Override
	String getId() {
		return ID;
	}

	public void copyToClipboard() {
		if (text == null) {
			return;
		}

		Clipboard clipboard = null;
		try {
			clipboard = new Clipboard(text.getShell().getDisplay());
			String contents = text.getSelectionText();
			if (contents.length() == 0)
				contents = text.getText();
			clipboard.setContents(new Object[] { contents }, new Transfer[] { TextTransfer.getInstance() });
		} finally {
			if (clipboard != null) {
				clipboard.dispose();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	@Override
	protected void buttonPressed(int buttonId) {
		switch (buttonId) {
		case BROWSE_ERROR_LOG_BUTTON:
			openErrorLogBrowser();
			break;
		case COPY_TO_CLIPBOARD_BUTTON:
			runCopyToClipboard();
			break;
		}
		super.buttonPressed(buttonId);
	}

	private void openErrorLogBrowser() {

		AboutUtils.openErrorLogBrowser(getShell());
	}

	private void runCopyToClipboard() {
		if (text == null) {
			return;
		}

		Clipboard clipboard = null;
		try {
			clipboard = new Clipboard(getShell().getDisplay());
			clipboard.setContents(new Object[] { text.getText() }, new Transfer[] { TextTransfer.getInstance() });
		} finally {
			if (clipboard != null) {
				clipboard.dispose();
			}
		}
	}

	private void fetchConfigurationInfo(final Text text) {
		text.setText(WorkbenchMessages.AboutSystemPage_RetrievingSystemInfo);
		Job job = new Job(WorkbenchMessages.AboutSystemPage_FetchJobTitle) {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				final String info = ConfigurationInfo.getSystemSummary();
				if (!text.isDisposed()) {
					text.getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!text.isDisposed()) {
								text.setText(info);
							}
						}
					});
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}
}