/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama;

import org.apache.commons.lang3.BooleanUtils;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.sebulli.fakturama.misc.Constants;

// import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Gerd Bartelt
 */
public class Activator implements BundleActivator {


	// The bundle ID (Bundle-SymbolicName)
    public static final String PLUGIN_ID = "com.sebulli.fakturama.rcp";

    // The shared instance
    private static BundleContext context;
	
    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static BundleContext getContext() {
        return context;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        // background color for focused widgets
        JFaceResources.getColorRegistry().put(Constants.COLOR_BGYELLOW, new RGB(255, 255, 225));
        
        // perhaps: set RTL mode and configure the Workbench like so:
        if(BooleanUtils.toBoolean(System.getProperty("force.rtl"))) {
                Window.setDefaultOrientation(SWT.RIGHT_TO_LEFT);
        }

        // background for Browser
		JFaceResources.getColorRegistry().put(Constants.COLOR_WHITE, new RGB(0xff, 0xff, 0xff));
   }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }
}
