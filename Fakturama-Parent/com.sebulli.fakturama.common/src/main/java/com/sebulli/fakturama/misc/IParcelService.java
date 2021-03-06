/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.misc;

import java.util.Properties;

/**
 * @author rheydenr
 *
 */
public interface IParcelService {

    public abstract String getRelativeTemplatePath();

	String getName();

	String getUrl();

	int size();

	void setActive(int i);

	String getName(int i);

	Properties getProperties();

}