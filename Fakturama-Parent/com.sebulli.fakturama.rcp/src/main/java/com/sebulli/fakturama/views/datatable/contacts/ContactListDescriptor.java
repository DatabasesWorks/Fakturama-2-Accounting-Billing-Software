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

package com.sebulli.fakturama.views.datatable.contacts;

/**
 * Enum for describing a Contact list. This contains the name of the displayed
 * values, the position of the columns and a default width of each column
 * (copied from old ColumnWidth*PreferencePages).
 *
 */
public enum ContactListDescriptor {

    NO("customerNumber", "common.label.default", 0, 60), 
    FIRSTNAME("firstName", "common.label.default", 1, 200), 
    LASTNAME("name", "common.field.name", 2, 120), 
    COMPANY("description", "common.field.description", 3, 150), 
    ZIP("taxValue", "common.field.value", 4, 50),
    CITY("taxValue", "common.field.value", 5, 80),
    ;
    
    private String propertyName, messageKey;
    private int position, defaultWidth;

    /**
     * @param propertyName
     * @param position
     * @param defaultWidth
     */
    private ContactListDescriptor(String propertyName, String messageKey, int position, int defaultWidth) {
        this.propertyName = propertyName;
        this.messageKey = messageKey;
        this.position = position;
        this.defaultWidth = defaultWidth;
    }

    /**
     * @return the propertyName
     */
    public final String getPropertyName() {
        return propertyName;
    }

    /**
     * @return the position
     */
    public final int getPosition() {
        return position;
    }

    /**
     * @return the defaultWidth
     */
    public final int getDefaultWidth() {
        return defaultWidth;
    }

    /**
     * @return the messageKey
     */
    public String getMessageKey() {
        return messageKey;
    }

    public static ContactListDescriptor getDescriptorFromColumn(int columnIndex) {
        for (ContactListDescriptor descriptor : values()) {
            if (descriptor.getPosition() == columnIndex) { return descriptor; }
        }
        return null;
    }

    /**
     * Gets all visible(!) properties of Contacts type.
     * 
     * @return properties of Contacts type
     */
    public static final String[] getContactPropertyNames() {
        return new String[] { 
                ContactListDescriptor.NO.getPropertyName(), 
                ContactListDescriptor.FIRSTNAME.getPropertyName(),
                ContactListDescriptor.LASTNAME.getPropertyName(), 
                ContactListDescriptor.COMPANY.getPropertyName(), 
                ContactListDescriptor.ZIP.getPropertyName(), 
                ContactListDescriptor.CITY.getPropertyName(), 
        };
    }
}
