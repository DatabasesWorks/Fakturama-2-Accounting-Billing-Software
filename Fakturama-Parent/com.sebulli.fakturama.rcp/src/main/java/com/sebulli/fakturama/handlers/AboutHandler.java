/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.sebulli.fakturama.handlers;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.sebulli.fakturama.dialogs.about.E4AboutDialog;
import com.sebulli.fakturama.ui.E4ApplicationInfo;
import com.sebulli.fakturama.ui.IE4ApplicationInfo;

public class AboutHandler {

	@Execute
	public void execute(Shell shell, IEclipseContext context) {
		
		// formerly done in org.eclipse.ui.internal.WorkbenchPlugin#getProductInfo
		// the (formerly) first call to this was in org.eclipse.ui.internal.Workbench:
		// (while creating the workbench window)
		//       String applicationName = WorkbenchPlugin.getDefault().getAppName();
		
		IE4ApplicationInfo applicationInfo = ContextInjectionFactory.make(E4ApplicationInfo.class, context);
		
		context.set(IE4ApplicationInfo.class, applicationInfo);
		E4AboutDialog dlg = ContextInjectionFactory.make(E4AboutDialog.class, context);
		dlg.open();
	}
}
