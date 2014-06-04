package com.sebulli.fakturama.views.datatable.tree;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

/**
 * This class provides the labels for the category tree
 * 
 * @author rheydenr
 *
 */
public class TreeCategoryLabelProvider extends CellLabelProvider {

    public String getText(Object obj) {

        // Display the localizes list names.
        //			if (this.rheNatTable.natTable instanceof NatTable)
        //				return "(localized) " + obj.toString();
        //			else
        return obj.toString();
    }

    @Override
    public void update(ViewerCell cell) {
        cell.setText(getText(cell.getElement()));
        //			cell.setImage(getImage(cell.getElement()));
    }

}