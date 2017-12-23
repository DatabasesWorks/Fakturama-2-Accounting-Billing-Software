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
package com.sebulli.fakturama.resources.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.internal.services.ResourceBundleHelper;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.core.services.translation.TranslationService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.FrameworkUtil;

import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.resources.ITemplateResourceManager;

/**
 * This is the "worker" class for creating the full template directory structure in a (new)
 * workspace. Called from ConfigurationManager.
 *
 */
public class TemplateResourceManager implements ITemplateResourceManager {
    private static final String START_DOC_HTML = "start.html";

	private static final String START_DOC_PATH = "Start";

	private static final String CONTRIBUTION_URI = "platform:/plugin/com.sebulli.fakturama.rcp";
    
//    @Inject
    private Logger log;   
    
    // Messages class can't be used at this point, since it isn't in context at this stage
    private TranslationService translationService;

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.resources.templates.ITemplateResourceManager#createWorkspaceTemplates(java.lang.String, java.lang.String)
     */
    @Override
    public boolean createWorkspaceTemplates(String workspace, IEclipseContext context) {
        this.translationService = context.get(TranslationService.class);
        this.log = context.get(Logger.class);
        String templateFolderName = translate("config.workspace.templates.name"); 
        
        // Exit if the workspace path is not set
        if (StringUtils.isBlank(workspace)) {
            return false;
        }

        // Exit, if the workspace path is not valid
        Path workspacePath = Paths.get(StringUtils.removeEnd(workspace, String.valueOf(File.separatorChar)), templateFolderName);
        if (!Files.isDirectory(workspacePath)) {
            // Create and fill the template folder, if it does not exist.
            try {
                Files.createDirectories(workspacePath);
                final String workspacePathString = workspacePath.toString();
                // Copy the document templates from the resources to the file system
                for (DocumentType doctype : DocumentType.values()) {
					switch (doctype) {
                    case NONE:
                        // do nothing!
                        break;
                    case DELIVERY:
                        resourceCopy("Templates/Delivery/Document.ott",
                                Paths.get(workspacePathString, StringUtils.trim(translate(doctype.getSingularKey())), "Document.ott"));
                        break;
                    default:
                        resourceCopy("Templates/Invoice/Document.ott",
                                Paths.get(workspacePathString, StringUtils.trim(translate(doctype.getSingularKey())), "Document.ott"));
                        break;
                    }
                }
    
                // Create the start page, if it does not exist.
                Path startPage = Paths.get(workspacePathString, START_DOC_PATH, START_DOC_HTML);
                if (Files.notExists(startPage)) {
                    resourceCopy("Templates/Start/start.html", Paths.get(workspacePathString, START_DOC_PATH, START_DOC_HTML));
                    resourceCopy("Templates/Start/logo.png", Paths.get(workspacePathString,  START_DOC_PATH, "logo.png"));
                }
        
                // Copy the parcel service templates
                String translatedServiceString = StringUtils.trim(translate("page.parcelservice"));
				Path parcelServiceFolder = Paths.get(workspacePathString, translatedServiceString); // new File(ParcelServiceManager.getTemplatePath());
                if(!Files.exists(parcelServiceFolder)) {   // ParcelServiceManager.getRelativeTemplatePath();
                    resourceCopy("Templates/ParcelService/DHL_de.txt", Paths.get(workspacePathString, translatedServiceString, "DHL_de.txt"));
                    resourceCopy("Templates/ParcelService/eFILIALE_de.txt", Paths.get(workspacePathString, translatedServiceString, "eFILIALE_de.txt"));
                    resourceCopy("Templates/ParcelService/myHermes_de.txt", Paths.get(workspacePathString, translatedServiceString, "myHermes_de.txt"));
                    resourceCopy("Templates/ParcelService/UPS_de.txt", Paths.get(workspacePathString, translatedServiceString, "UPS_de.txt"));
                }
            } catch (IOException ioex) {
                log.error(ioex, "couldn't create template dir in workspace");
                return false; 
            }
        }

        return true;
    }
    

	@Override
	public Image getProgramImage(Display display, ProgramImages imageName) {
		Image img = null;
		try (InputStream in = FrameworkUtil.getBundle(getClass()).getResource(imageName.getPath()).openStream();){
			img = new Image(display, in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return img;
	}
        
    
    /**
     * Copies a resource file from the resource to the file system
     * 
     * @param resource
     *            The resource file
     * @param pFilePath
     *            The destination on the file system
     * @param fileName
     *            The destination file name
     * @param workspace
     */
    private void resourceCopy(String resource, final Path targetFile) {
        // Create the input stream from the resource file "Templates/Invoice/Document.ott"
        URL fileResource = ResourceBundleHelper.getBundleForName(com.sebulli.fakturama.resources.Activator.BUNDLE_ID).getResource(resource);
        if(fileResource == null) {
        	// if running inside Eclipse IDE we have to change the source path silently
        	fileResource = ResourceBundleHelper.getBundleForName(com.sebulli.fakturama.resources.Activator.BUNDLE_ID).getResource("/target/classes/"+resource);
        }
		try(InputStream in = fileResource.openStream()) {
            // Create the destination folder if it doesn't exists
            if (!Files.isDirectory(targetFile.getParent())) {
                Files.createDirectories(targetFile.getParent());
            }
    
            // Copy the file
            Files.copy(in, targetFile);
        } catch (FileNotFoundException fnfex) {
        	log.error(fnfex, "Resource file not found");
        } catch (FileAlreadyExistsException | DirectoryNotEmptyException dnee) {
        	log.warn("file "+targetFile.toAbsolutePath()+" already exists in target directory.");
        } catch (IOException ioex) {
        	log.error(ioex, "Error copying the resource file to the file system.");
        }
    }

    /**
     * Translate the given key.
     * @param key key to translate
     * @param args variable replacements (will replace {0}, {1},... placeholders)
     */
    public String translate(String key, Object... args) {
        if (key == null) return "";
        if (key.charAt(0) != '%') key = '%'+key;
        String rc = translationService.translate(key, CONTRIBUTION_URI);
        if ((args == null) || (args.length == 0)) return rc;
        return MessageFormat.format(rc, args);
    }

    /**
     * @return the translationService
     */
    public TranslationService getTranslationService() {
        return translationService;
    }

    /**
     * @param translationService the translationService to set
     */
    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    }

}
