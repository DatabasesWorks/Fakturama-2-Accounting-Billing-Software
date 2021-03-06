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

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.equinox.internal.p2.operations.IStatusCodes;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;

/**
 * Update Handler.
 * @see {@link https://blog.codecentric.de/en/2015/04/add-p2-update-functionality-to-an-eclipse-4-application-eclipse-rcp-cookbook/}
 *
 */
public class UpdateHandler {

	@Inject
	protected ILogger log;

	@Inject
	@Translation
	protected Messages msg;

	boolean cancelled = false;

	@Execute
	public void execute(IProvisioningAgent agent, UISynchronize sync, IWorkbench workbench, Shell shell) {
		// update using a progress monitor
		IRunnableWithProgress runnable = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {

				update(agent, monitor, sync, workbench);
			}
		};

		try {
			new ProgressMonitorDialog(null).run(true, true, runnable);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private IStatus update(IProvisioningAgent agent, IProgressMonitor monitor, UISynchronize sync,
			IWorkbench workbench) {
		ProvisioningSession session = new ProvisioningSession(agent);
		// update all user-visible installable units
		UpdateOperation operation = new UpdateOperation(session);

		SubMonitor sub = SubMonitor.convert(monitor, msg.commandAppUpdateChecking, 200);

		IStatus status = operation.resolveModal(sub.newChild(100));
		if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE) {
			showDialog(sync, MessageDialog.INFORMATION, msg.dialogMessageboxTitleInfo, msg.commandAppUpdateNoupdates);
			return Status.CANCEL_STATUS;
		}
		
		if(status.getCode() == IStatusCodes.MISSING_REQUIREMENTS) {
			showDialog(sync, MessageDialog.ERROR, msg.dialogMessageboxTitleError, "Update not possible, please download installation file and install it manually");
			log.error(null, "can't update application. Reason: ");
			List<String> reasons = collectErrors(status, new ArrayList<>());
			log.warn(StringUtils.join(reasons, '\n'));
			return Status.CANCEL_STATUS;
		}

		ProvisioningJob provisioningJob = operation.getProvisioningJob(sub.newChild(100));
		if (provisioningJob != null) {
			sync.syncExec(() -> {
				boolean performUpdate = MessageDialog.openQuestion(null, msg.commandAppUpdateNewTitle,
						msg.commandAppUpdateNewQuestion);
				if (performUpdate) {
					provisioningJob.addJobChangeListener(new JobChangeAdapter() {

						@Override
						public void done(IJobChangeEvent event) {
							if (event.getResult().isOK()) {
								sync.syncExec(() -> {
									boolean restart = MessageDialog.openQuestion(null, msg.commandAppUpdateRestartTitle,
											msg.commandAppUpdateRestartQuestion);
									if (restart) {
										workbench.restart();
									}
								});
							} else {
								showDialog(sync, MessageDialog.ERROR, msg.dialogMessageboxTitleError,
										event.getResult().getMessage());
								cancelled = true;
							}
						}
					});

					// since we switched to the UI thread for interacting with the user
					// we need to schedule the provisioning thread, otherwise it would
					// be executed also in the UI thread and not in a background thread
					provisioningJob.schedule();

				} else {
					cancelled = true;
				}
			});
		} else {
			if (operation.hasResolved()) {
				showDialog(sync, MessageDialog.ERROR, msg.dialogMessageboxTitleError,
						MessageFormat.format(msg.commandAppUpdateErrorProvisioningjob, operation.getResolutionResult()));
			} else {
				showDialog(sync, MessageDialog.ERROR, msg.dialogMessageboxTitleError,
						msg.commandAppUpdateErrorProvisioningjobresolve);
			}
			cancelled = true;
		}

		if (cancelled) {
			// reset cancelled flag
			cancelled = false;
			return Status.CANCEL_STATUS;
		}
		
		return Status.OK_STATUS;
	}

	private List<String> collectErrors(IStatus status, List<String> reasons) {
		if(status.isMultiStatus()) {
			if(status.getChildren().length > 0) {
				for (IStatus stat : status.getChildren()) {
					collectErrors(stat, reasons);
				}
			} else {
				reasons.add(status.getMessage());
			}
		} else {
			reasons.add(status.getMessage());
		}
		return reasons;
	}

	private void showDialog(UISynchronize sync, int dialogType, String title, final String message) {
		// as the provision needs to be executed in a background thread
		// we need to ensure that the message dialog is executed in
		// the UI thread
		sync.syncExec(() -> {
			switch (dialogType) {
			case MessageDialog.INFORMATION:
				MessageDialog.openInformation(null, title, message);
				break;
			case MessageDialog.ERROR:
				MessageDialog.openError(null, title, message);
				break;
			default:
				break;
			}
		});
	}
}