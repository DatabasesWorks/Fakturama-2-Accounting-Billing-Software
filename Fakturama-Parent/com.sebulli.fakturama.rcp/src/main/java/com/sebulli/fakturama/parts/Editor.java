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

package com.sebulli.fakturama.parts;

import java.io.IOException;

import javax.inject.Inject;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.beans.IBeanValueProperty;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.nebula.jface.cdatetime.CDateTimeObservableValue;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.nebula.widgets.formattedtext.FormattedText;
import org.eclipse.nebula.widgets.formattedtext.FormattedTextObservableValue;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.sebulli.fakturama.calculate.NumberGenerator;
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.dao.PropertiesDAO;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.model.FakturamaModelFactory;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.IEntity;

/**
 * Parent class for all editors
 * 
 */
public abstract class Editor<T extends IEntity> {
	
    /**
     * Indicates if a widget is in "calculating" state, i.e. if modification occurs and dirty state has to be set.
     */
	protected static final String CALCULATING_STATE = "calculating";
	
	/**
	 * Token for the update event.
	 */
	public static final String UPDATE_EVENT = "update";

	public static final String BIND_MODE_INDICATOR = "IS_IN_BIND_MODE";

    @Inject
    protected IPreferenceStore defaultValuePrefs;

    @Inject
    protected PropertiesDAO propertiesDao;
	
    @Inject
    private IEclipseContext context;

	@Inject
	@Translation
	protected Messages msg;
    
    @Inject
    protected ILogger log;
    
    /**
     * Event Broker for sending update events to the list table
     */
    @Inject
    protected IEventBroker evtBroker;

    @Inject
	protected ContactsDAO contactDAO;

    protected FakturamaModelFactory modelFactory =  FakturamaModelPackage.MODELFACTORY;

	protected StdComposite stdComposite = null;
	protected String tableViewID = "";
	private String editorID = "";
	protected static final int NO_ERROR = 0;
	protected static final int ERROR_NOT_NEXT_ID = 1;

	private DataBindingContext ctx = new DataBindingContext();

	protected abstract MDirtyable getMDirtyablePart();

	/**
	 * Set the font size of a label to 24pt
	 * 
	 * @param label
	 *            The label that is modified
	 */
	protected void makeLargeLabel(Label label) {
		resizeLabel(label, 24);
	}

	/**
	 * Set the font size of a label to 9pt
	 * 
	 * @param label
	 *            The label that is modified
	 */
	protected void makeSmallLabel(Label label) {
		resizeLabel(label, 9);
	}

	/**
	 * Set the font size of a label to x px
	 * 
	 * @param label
	 *            The label that is modified
	 * @size Size of the label in px
	 */
	protected void resizeLabel(Label label, int size) {
		FontData[] fD = label.getFont().getFontData();
		fD[0].setHeight(size);
		Font font = new Font(null, fD[0]);
		label.setFont(font);
		font.dispose();
	}
	
    protected String getDefaultEntryKey() {
        // per default this method does nothing;
        // only three editors use the default button.
        return "";
    }


	/**
	 * Class to create the widgets to show and set the standard entry.
	 * 
	 */
	protected class StdComposite {

		// Text widgets that displays the standard widget
		private Text txtStd;
		// The button
		public Button stdButton;

		// The unidataset of this editor 
		private final T uds;

		// The label for "This dataset"
		private String thisDataset = null;

//		// The data set array with this and the other unidatasets
//		private List<T> dataSetArray;

		/**
		 * Constructor Creates the widgets to set this entry as standard entry.
		 * 
		 * @param parent
		 *            The parent widget
		 * @param uds
		 *            The editor's unidataset
		 * @param dataSetArray
		 *            This and the other unidatasets
		 * @param propertyKey
		 *            The property key that defines the standard
		 * @param thisDataset
		 *            Text for "This dataset"
		 * @param hSpan
		 *            Horizontal span
		 */
		public StdComposite(Composite parent, final T uds, final T currentStandardEntry, final String thisDataset, int hSpan) {

			// Set the local variables
			this.uds = uds;
			this.thisDataset = thisDataset;

			// Create a container for the text widget and the button
			Composite stdComposite = new Composite(parent, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(2).applyTo(stdComposite);
			GridDataFactory.fillDefaults().span(hSpan, 1).applyTo(stdComposite);

			// Create the text widget that displays the standard entry
			txtStd = new Text(stdComposite, SWT.BORDER);
			txtStd.setEditable(false);

			GridDataFactory.swtDefaults().hint(200, -1).align(SWT.BEGINNING, SWT.CENTER).applyTo(txtStd);
			setStdText(currentStandardEntry);

			// Create the button to make this entry to the standard
			stdButton = new Button(stdComposite, SWT.BORDER);
			//T: Button text
			stdButton.setText(msg.commonButtonSetdefault);
			GridDataFactory.swtDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(stdButton);
			stdButton.setEnabled(false);
			/**
			 * Make this entry to the standard
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			stdButton.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> { 
					if (uds.getId() > 0) {
						defaultValuePrefs.setValue(getDefaultEntryKey(), uds.getId());
						txtStd.setText(thisDataset);
						try {
							((IPersistentPreferenceStore)defaultValuePrefs).save();
                            // Refresh the table view of all VATs
                            evtBroker.post(getEditorID(), "update/defaultvalue");
                        } catch (IOException e1) {
                            log.error(e1, "Error while flushing default value preferences.");
                        }
					}
			}));

		}
		
		/**
		 * Test, if this is the standard entry and set the text of the text
		 * widget.
		 */
		public void setStdText(T stdEntry) {
			if (stdEntry != null && txtStd != null) {

				// If the editor's unidataset is the standard entry
				if (uds.getId() == stdEntry.getId()) {
					// Mark it as "standard" ..
					txtStd.setText(thisDataset);
				}
				else
					// .. or display the one that is the standard entry.
					txtStd.setText(stdEntry.getName());
			}
		}
		
		/**
		 * Set the tool tip text
		 * 
		 * @param toolTipText
		 * 				The tool tip text
		 */
		public void setToolTipText (String toolTipText) {
			stdButton.setToolTipText(toolTipText);
		}
		
		/**
		 * Sets the focus of this component to the default button.
		 */
		public void focus() {
			stdButton.setFocus();
		}

	}

	/**
	 * Asks this part to take focus within the workbench. Set the focus to the
	 * standard text
	 */
	@Focus
	public void setFocus() {
		if (stdComposite != null)
//			stdComposite.setStdText();
			stdComposite.focus();
	}

	/**
	 * Get the next document number
	 * 
	 * @return The next document number
	 * @deprecated use {@link NumberGenerator#getNextNr(String)}
	 */
	protected String getNextNr() {
		NumberGenerator numberGenerator = ContextInjectionFactory.make(NumberGenerator.class, context);
		return numberGenerator.getNextNr(getEditorID());
	}

	/**
	 * Set the next free document number in the preference store. But check if
	 * the documents number is the next free one.
	 * 
	 * @param s
	 *            The document number as string.
	 * @return Errorcode, if the document number is correctly set to the next
	 *         free number.
	 * @deprecated use {@link NumberGenerator#setNextFreeNumberInPrefStore(String, String, String)}
	 */
	protected int setNextFreeNumberInPrefStore(String value, String key) {
		NumberGenerator numberGenerator = ContextInjectionFactory.make(NumberGenerator.class, context);
		return numberGenerator.setNextFreeNumberInPrefStore(value, getEditorID());
	}
	
    /**
     * Binds the model properties to the according widgets on UI. This is necessary for initially creating
     * the view <b>and</b> if the underlying object changes (e.g., if you save a newly created entity).
     * 
     * TODO: WIP, must be overwritten, make it abstract later on
     */
    protected void bindModel() {
    }

	/**
	 * Binds a Java bean property to the UI widget.
	 * 
	 * @param target the Java bean
	 * @param source the SWT widget
	 * @param property the property to observe
	 */
	protected Binding bindModelValue(T target, Control source, String property) {
	    return bindModelValue(target, source, property, null, null);
	}
	
    protected Binding bindModelValue(T target, final Control source, String property, UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
        IBeanValueProperty nameProperty = BeanProperties.value(getModelClass(), property);
        IObservableValue<T> model = nameProperty.observe(target);
        Binding retval = null;
        
        IObservableValue<T> uiWidget;
        /*
         * ATTENTION! Dont't be attempted to put the Listener code in this if statement.
         * Otherwise you get ALWAYS a dirty editor!
         */
        if(source instanceof Combo || source instanceof Button) {
            uiWidget = WidgetProperties.selection().observe(source);
        } else if(source instanceof CDateTime) {
            uiWidget = new CDateTimeObservableValue((CDateTime) source);
        } else if(source instanceof Spinner) {
        	uiWidget = WidgetProperties.selection().observe(source);
        } else {
//            uiWidget = WidgetProperties.text(SWT.FocusOut).observe(source);
            uiWidget = WidgetProperties.text(SWT.Modify).observe(source);
        }

        if (targetToModel != null || modelToTarget != null) {
            retval = getCtx().bindValue(uiWidget, model, targetToModel, modelToTarget);
        } else {
            retval = getCtx().bindValue(uiWidget, model);
        }    
        
        if(source instanceof Combo) {
            ((Combo)source).addModifyListener(e -> {
            	// because of the Linux event fireworks bug :-(
            	if (((MPart) getMDirtyablePart()).getTransientData().get(BIND_MODE_INDICATOR) == null 
            			/*&& (((Combo)source).getSelectionIndex() != ((Combo)e.getSource()).getSelectionIndex() || ((Combo)e.getSource()).getSelectionIndex() == -1)*/) {
				    getMDirtyablePart().setDirty(true);
				}
            });
        } else if(source instanceof CDateTime) {
            ((CDateTime)source).addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> { 
            	if(((MPart) getMDirtyablePart()).getTransientData().get(BIND_MODE_INDICATOR) == null) {
                    getMDirtyablePart().setDirty(true);
            	}
            }));
        } else if(source instanceof Button) {
        	((Button)source).addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> { 
            	if(((MPart) getMDirtyablePart()).getTransientData().get(BIND_MODE_INDICATOR) == null) {
        			getMDirtyablePart().setDirty(true);
            	}
        	}));
        }
        
        return retval;
    }
	
    protected Binding bindModelValue(T target, Text source, String property, int limit, UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
    	Binding binding = null;
        if (limit > 0) {
            source.setTextLimit(limit);
        }

        // the bind has to occur _before_ adding a ModifyListener, because
        // else the setting of the initial value would fire a modification event
        binding = bindModelValue(target, source, property, targetToModel, modelToTarget);
        
        source.addModifyListener(e -> {
        	if(((MPart) getMDirtyablePart()).getTransientData().get(BIND_MODE_INDICATOR) == null) {
                getMDirtyablePart().setDirty(true);
        	}
        });
        return binding;
    }	

    /**
     * Supervise this text widget. Set the text limit and request a new
     * "isDirty" validation, if the content of the text widget is modified.
     */
    protected Binding bindModelValue(T target, Text source, String property, int limit) {
        return bindModelValue(target, source, property, limit, null, null);
    }


    protected Binding bindModelValue(T target, FormattedText source, String property, int limit) {
    	Binding binding = null;
    	if(limit > 0) {
    		source.getControl().setTextLimit(limit);
    	}
        IBeanValueProperty nameProperty = BeanProperties.value(getModelClass(), property);
        IObservableValue<T> model = nameProperty.observe(target);
        IObservableValue<T> uiWidget = new FormattedTextObservableValue(source, SWT.Modify);
        binding = getCtx().bindValue(uiWidget, model);

        source.getControl().addModifyListener(e -> {
                /*
                 * If the widget is in "calculating" state we don't have to 
                 * update the dirty state, because this leads to a dirty editor
                 * as soon as it opens. The "calculating" state is set immediately 
                 * before and after the calculation which influences this widget.
                 */
                if(((Text)e.getSource()).getData(CALCULATING_STATE) == null) {
                    getMDirtyablePart().setDirty(true);
                }
        });
        return binding;
    }

    protected Binding bindModelValue(T target, ComboViewer source, String property) {
    	Binding binding = null;
        IBeanValueProperty nameProperty = BeanProperties.value(getModelClass(), property);
        IObservableValue<T> model = nameProperty.observe(target);
        IObservableValue<T> uiWidget = ViewersObservables
                .observeSingleSelection(source);
        binding = getCtx().bindValue(uiWidget, model);
        
        source.getCombo().addModifyListener(e -> {
        	if(((MPart) getMDirtyablePart()).getTransientData().get(BIND_MODE_INDICATOR) == null) {
                getMDirtyablePart().setDirty(true);
        	}
        });
         
        return binding;
    }

	/**
     * @return the ctx
     */
    public DataBindingContext getCtx() {
        return ctx;
    }

    /**
	 * Jump to the next control, if in a multi-line text control the tab key is
	 * pressed. Normally the tab won't jump to the next control, if the current
	 * one is a text control. It will insert a tabulator.
	 * 
	 * @param text
	 *            This (multi-line) text control
	 * @param nextControl
	 *            The next control
	 */
	protected void setTabOrder(Text text, final Control nextControl) {
		text.addKeyListener(new KeyAdapter() {

			/**
			 * Capture the tab key and set the focus to the next control
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == '\t') {
					e.doit = false;
					nextControl.setFocus();
				}
			}

		});
	}

	/**
	 * Test before close, if the document ID is correct
	 */
// TODO this has nothing to do with @PersistState!!!
	public int promptToSaveOnClose(MDirtyable part, Composite parent) {
		
		//T: Dialog Header
		MessageDialog dialog = new MessageDialog(parent.getShell(), msg.commonButtonSavechanges, null,
				//T: Dialog Text
				msg.commonButtonSavechangesquestion, MessageDialog.QUESTION,
				new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL }, 0);

		final int dialogResult = dialog.open();

		if (dialogResult == 0) {
			return 0;
			// Check, if the number is unique
			/*if (thereIsOneWithSameNumber())
				return ISaveablePart2.CANCEL;
			else
				return ISaveablePart2.YES;*/
		}
		else if (dialogResult == 1) {
			return 1; // ISaveablePart2.NO;
		}
		else {
			return 2; // ISaveablePart2.CANCEL;
		}
	}

	/**
	 * Returns if save is allowed
	 * 
	 * @return <code>true</code> if save is allowed
	 */
	@CanExecute
	protected boolean saveAllowed() {
		return getMDirtyablePart().isDirty();
	}

    /**
     * @return the editorID
     */
    protected String getEditorID() {
        return editorID;
    }

    /**
     * @param editorID the editorID to set
     */
    protected void setEditorID(String editorID) {
        this.editorID = editorID;
    }

    protected abstract Class<T> getModelClass();

}
