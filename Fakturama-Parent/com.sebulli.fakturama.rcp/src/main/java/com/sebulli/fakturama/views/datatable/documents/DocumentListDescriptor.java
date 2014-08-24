    
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

    package com.sebulli.fakturama.views.datatable.documents;

    /**
     * Enum for describing a Contact list. This contains the name of the displayed
     * values, the position of the columns and a default width of each column
     * (copied from old ColumnWidth*PreferencePages).
     *
     */
    public enum DocumentListDescriptor {

        // TODO CHANGE messageKeys and propertyNames!!!
        ICON("icon", "icon", 0, 20), 
        DOCUMENT("document", "common.label.default", 1, 80), 
        DATE("name", "common.field.name", 2, 80), 
        NAME("description", "common.field.description", 3, 200), 
        STATE("taxValue", "common.field.value", 4, 100),
        TOTAL("taxValue", "common.field.value", 5, 70),
        PRINTED("printed", "common.field.value", 6, 60),
        ;

        private String propertyName, messageKey;
        private int position, defaultWidth;

        /**
         * @param propertyName
         * @param position
         * @param defaultWidth
         */
        private DocumentListDescriptor(String propertyName, String messageKey, int position, int defaultWidth) {
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

        public static DocumentListDescriptor getDescriptorFromColumn(int columnIndex) {
            for (DocumentListDescriptor descriptor : values()) {
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
                    DocumentListDescriptor.ICON.getPropertyName(), 
                    DocumentListDescriptor.DOCUMENT.getPropertyName(),
                    DocumentListDescriptor.DATE.getPropertyName(), 
                    DocumentListDescriptor.NAME.getPropertyName(), 
                    DocumentListDescriptor.STATE.getPropertyName(), 
                    DocumentListDescriptor.TOTAL.getPropertyName(), 
                    DocumentListDescriptor.PRINTED.getPropertyName(), 
            };
        }
    }

