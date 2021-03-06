package com.sebulli.fakturama.oldmodel;

/**
 *  ###  Generated by EclipseLink Project EclipseLink Workbench 2.5.1 - Wed Mar 11 00:08:08 CET 2015.  ###
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = "Payments")
@ReadOnly
public class OldPayments {

	private String category;
	private boolean defaultpaid;
	private boolean deleted;
	private String deposittext;
	private String description;
	private int discountdays;
	private double discountvalue;
    @Id
	private int id;
	private String name;
	private int netdays;
	private String paidtext;
	private String unpaidtext;

public OldPayments() {
}

public String getCategory() {
	return this.category;
}

public String getDeposittext() {
	return this.deposittext;
}

public String getDescription() {
	return this.description;
}

public int getDiscountdays() {
	return this.discountdays;
}

public double getDiscountvalue() {
	return this.discountvalue;
}

public int getId() {
	return this.id;
}

public String getName() {
	return this.name;
}

public int getNetdays() {
	return this.netdays;
}

public String getPaidtext() {
	return this.paidtext;
}

public String getUnpaidtext() {
	return this.unpaidtext;
}

public boolean isDefaultpaid() {
	return this.defaultpaid;
}

public boolean isDeleted() {
	return this.deleted;
}

public void setCategory(String category) {
	this.category = category;
}

public void setDefaultpaid(boolean defaultpaid) {
	this.defaultpaid = defaultpaid;
}

public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}

public void setDeposittext(String deposittext) {
	this.deposittext = deposittext;
}

public void setDescription(String description) {
	this.description = description;
}

public void setDiscountdays(int discountdays) {
	this.discountdays = discountdays;
}

public void setDiscountvalue(double discountvalue) {
	this.discountvalue = discountvalue;
}

public void setId(int id) {
	this.id = id;
}

public void setName(String name) {
	this.name = name;
}

public void setNetdays(int netdays) {
	this.netdays = netdays;
}

public void setPaidtext(String paidtext) {
	this.paidtext = paidtext;
}

public void setUnpaidtext(String unpaidtext) {
	this.unpaidtext = unpaidtext;
}

}
