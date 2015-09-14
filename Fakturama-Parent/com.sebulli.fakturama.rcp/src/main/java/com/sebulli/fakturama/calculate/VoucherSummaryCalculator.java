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
 
package com.sebulli.fakturama.calculate;

import java.util.List;

import javax.inject.Inject;
import javax.money.MonetaryAmount;

import org.eclipse.e4.core.services.log.Logger;

import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.dto.VatSummarySet;
import com.sebulli.fakturama.dto.VoucherSummary;
import com.sebulli.fakturama.model.ExpenditureItem;

/**
 *
 */
public class VoucherSummaryCalculator {

    @Inject
    private Logger log;

    /**
     * Calculates the tax, gross and sum of an voucher
     * 
     * @param globalVatSummarySet
     *            The documents vat is added to this global VAT summary set.
     * @param items
     *            Document's items
     * @param useCategory
     *            If true, the category is also used for the vat summary as a
     *            description
     */
    public VoucherSummary calculate(VatSummarySet globalVoucherSummarySet, List<ExpenditureItem> items, boolean useCategory, 
            MonetaryAmount paid, MonetaryAmount total, boolean discounted) {
        VoucherSummary retval = new VoucherSummary();
        Double vatPercent;
        String vatDescription;

        // PaidFactor is the relation between paid and total value.
        // e.g. if there is a discount of 3%, the total value is 100$
        // and the paid value is 97$, then the paidFactor is 0.97
        Double paidFactor = 1.0;
        
        // Total value must not be 0, if paid value is != 0
        if  ((total.isZero()) && (!paid.isZero())) {
            log.error("Voucher Summary: Total value is 0, but paid value != 0");
        }
        
        if (discounted && (!total.isZero()))
            paidFactor = paid.divide(total.getNumber().doubleValue()).getNumber().doubleValue();

        
        // This Vat summary contains only the VAT entries of this document,
        // whereas the the parameter vatSummaryItems is a global VAT summary
        // and contains entries from this document and from others.
        VatSummarySet voucherSummaryItems = new VatSummarySet();

        // Set the values to 0.0
//        resetValues();

        // Use all non-deleted items
        for (ExpenditureItem item : items) {

            // Get the data from each item
            vatDescription = item.getVat().getDescription();
            vatPercent = item.getVat().getTaxValue();

            Price price = new Price(item, paidFactor);
            MonetaryAmount itemVat = price.getTotalVat();

            // Add the total net value of this item to the sum of net items
            retval.setTotalNet(retval.getTotalNet().add(price.getTotalNet()));

            // Add the VAT to the sum of VATs
            retval.setTotalVat(retval.getTotalVat().add(itemVat));

            VatSummaryItem voucherSummaryItem;
            if (useCategory) {
                // Add the VAT summary item to the ... 
                voucherSummaryItem = new VatSummaryItem(vatDescription, vatPercent, price.getTotalNet(), itemVat,
                        item.getAccountType());
            }
            else {
                // Add the VAT summary item to the ... 
                voucherSummaryItem = new VatSummaryItem(vatDescription, vatPercent, price.getTotalNet(), itemVat, "");

            }

            // .. VAT summary of the voucher ..
            voucherSummaryItems.add(voucherSummaryItem);

        }

        // Gross value is the sum of net and VAT value
        retval.setTotalGross(retval.getTotalNet().add(retval.getTotalVat()));

        // Finally, round the values

//        this.totalGross.round();
//        this.totalNet.round();
        retval.setTotalVat(retval.getTotalGross().subtract(retval.getTotalNet()));

        // Round also the Vat summaries
        voucherSummaryItems.roundAllEntries();

//        // Add the entries of the document summary set also to the global one
        if (globalVoucherSummarySet != null)
            globalVoucherSummarySet.addVatSummarySet(voucherSummaryItems); 

        return retval;
    }

}