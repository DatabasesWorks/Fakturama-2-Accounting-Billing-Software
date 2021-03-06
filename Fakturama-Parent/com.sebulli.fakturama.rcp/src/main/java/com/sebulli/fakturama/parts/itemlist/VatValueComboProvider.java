/* 
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2015 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     The Fakturama Team - initial API and implementation
 */
 
package com.sebulli.fakturama.parts.itemlist;

import java.util.List;

import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;

import com.sebulli.fakturama.model.VAT;

/**
 * {@link IComboBoxDataProvider} for {@link VAT} values.
 *
 */
public class VatValueComboProvider implements IComboBoxDataProvider {

    private List<VAT> allVats = null;
    
    public VatValueComboProvider(List<VAT> allVats) {
        this.allVats = allVats;
    }

    /* (non-Javadoc)
     * @see org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider#getValues(int, int)
     */
    @Override
    public List<?> getValues(int columnIndex, int rowIndex) {
        return allVats;
    }

}
