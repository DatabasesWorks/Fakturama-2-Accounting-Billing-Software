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

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

/**
 * @author rheydenr
 *
 */
public class SaveAllHandler {

	
	// TODO use internal org.eclipse.e4.ui.internal.workbench.handlers.SaveAllHandler
	  @Execute
	  void execute(EPartService partService) {
	    partService.saveAll(false);
	  }

}
