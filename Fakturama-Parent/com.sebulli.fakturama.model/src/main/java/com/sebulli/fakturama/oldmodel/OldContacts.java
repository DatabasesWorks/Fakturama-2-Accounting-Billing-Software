package com.sebulli.fakturama.oldmodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 *  ###  Generated by EclipseLink Project EclipseLink Workbench 2.5.1 - Thu Nov 07 21:56:49 CET 2013.  ###
 */
@Entity
@Table(name = "CONTACTS")
@ReadOnly
public class OldContacts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6066305318813006727L;
	
	private String mandatRef;
	private String suppliernumber;
	private String zip;
	private String website;
	private int vatnrvalid;
	private String vatnr;
	private int useNetGross;
	private String title;
	private String street;
	private int reliability;
	private String phone;
	private int payment;
	private String nr;
	private String note;
	private String name;
	private String mobile;
	private String iban;
	private int gender;
	private String firstname;
	private String fax;
	private String email;
	private double discount;
	private String deliveryZip;
	private String deliveryTitle;
	private String deliveryStreet;
	private String deliveryName;
	private int deliveryGender;
	private String deliveryFirstname;
	private String deliveryCountry;
	private String deliveryCompany;
	private String deliveryCity;
	private String deliveryBirthday;
	private boolean deleted;
	private String dateAdded;
	private String country;
	private String company;
	private String city;
	private String category;
	private String birthday;
	private String bic;
	private String bankName;
	private String bankCode;
	private String accountHolder;
	private String account;
	
	@Id
	private int id;

public OldContacts() {
}

public String getAccount() {
	return this.account;
}

public String getAccountHolder() {
	return this.accountHolder;
}

public String getBankCode() {
	return this.bankCode;
}

public String getBankName() {
	return this.bankName;
}

public String getBic() {
	return this.bic;
}

public String getBirthday() {
	return this.birthday;
}

public String getCategory() {
	return this.category;
}

public String getCity() {
	return this.city;
}

public String getCompany() {
	return this.company;
}

public String getCountry() {
	return this.country;
}

public String getDateAdded() {
	return this.dateAdded;
}

public String getDeliveryBirthday() {
	return this.deliveryBirthday;
}

public String getDeliveryCity() {
	return this.deliveryCity;
}

public String getDeliveryCompany() {
	return this.deliveryCompany;
}

public String getDeliveryCountry() {
	return this.deliveryCountry;
}

public String getDeliveryFirstname() {
	return this.deliveryFirstname;
}

public int getDeliveryGender() {
	return this.deliveryGender;
}

public String getDeliveryName() {
	return this.deliveryName;
}

public String getDeliveryStreet() {
	return this.deliveryStreet;
}

public String getDeliveryTitle() {
	return this.deliveryTitle;
}

public String getDeliveryZip() {
	return this.deliveryZip;
}

public double getDiscount() {
	return this.discount;
}

public String getEmail() {
	return this.email;
}

public String getFax() {
	return this.fax;
}

public String getFirstname() {
	return this.firstname;
}

public int getGender() {
	return this.gender;
}

public String getIban() {
	return this.iban;
}

public int getId() {
	return this.id;
}

public String getMandatRef() {
	return this.mandatRef;
}

public String getMobile() {
	return this.mobile;
}

public String getName() {
	return this.name;
}

public String getNote() {
	return this.note;
}

public String getNr() {
	return this.nr;
}

public int getPayment() {
	return this.payment;
}

public String getPhone() {
	return this.phone;
}

public int getReliability() {
	return this.reliability;
}

public String getStreet() {
	return this.street;
}

public String getSuppliernumber() {
	return this.suppliernumber;
}

public String getTitle() {
	return this.title;
}

public int getUseNetGross() {
	return this.useNetGross;
}

public String getVatnr() {
	return this.vatnr;
}

public int getVatnrvalid() {
	return this.vatnrvalid;
}

public String getWebsite() {
	return this.website;
}

public String getZip() {
	return this.zip;
}

public boolean isDeleted() {
	return this.deleted;
}

public void setAccount(String account) {
	this.account = account;
}

public void setAccountHolder(String accountHolder) {
	this.accountHolder = accountHolder;
}

public void setBankCode(String bankCode) {
	this.bankCode = bankCode;
}

public void setBankName(String bankName) {
	this.bankName = bankName;
}

public void setBic(String bic) {
	this.bic = bic;
}

public void setBirthday(String birthday) {
	this.birthday = birthday;
}

public void setCategory(String category) {
	this.category = category;
}

public void setCity(String city) {
	this.city = city;
}

public void setCompany(String company) {
	this.company = company;
}

public void setCountry(String country) {
	this.country = country;
}

public void setDateAdded(String dateAdded) {
	this.dateAdded = dateAdded;
}

public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}

public void setDeliveryBirthday(String deliveryBirthday) {
	this.deliveryBirthday = deliveryBirthday;
}

public void setDeliveryCity(String deliveryCity) {
	this.deliveryCity = deliveryCity;
}

public void setDeliveryCompany(String deliveryCompany) {
	this.deliveryCompany = deliveryCompany;
}

public void setDeliveryCountry(String deliveryCountry) {
	this.deliveryCountry = deliveryCountry;
}

public void setDeliveryFirstname(String deliveryFirstname) {
	this.deliveryFirstname = deliveryFirstname;
}

public void setDeliveryGender(int deliveryGender) {
	this.deliveryGender = deliveryGender;
}

public void setDeliveryName(String deliveryName) {
	this.deliveryName = deliveryName;
}

public void setDeliveryStreet(String deliveryStreet) {
	this.deliveryStreet = deliveryStreet;
}

public void setDeliveryTitle(String deliveryTitle) {
	this.deliveryTitle = deliveryTitle;
}

public void setDeliveryZip(String deliveryZip) {
	this.deliveryZip = deliveryZip;
}

public void setDiscount(double discount) {
	this.discount = discount;
}

public void setEmail(String email) {
	this.email = email;
}

public void setFax(String fax) {
	this.fax = fax;
}

public void setFirstname(String firstname) {
	this.firstname = firstname;
}

public void setGender(int gender) {
	this.gender = gender;
}

public void setIban(String iban) {
	this.iban = iban;
}

public void setId(int id) {
	this.id = id;
}

public void setMandatRef(String mandatRef) {
	this.mandatRef = mandatRef;
}

public void setMobile(String mobile) {
	this.mobile = mobile;
}

public void setName(String name) {
	this.name = name;
}

public void setNote(String note) {
	this.note = note;
}

public void setNr(String nr) {
	this.nr = nr;
}

public void setPayment(int payment) {
	this.payment = payment;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public void setReliability(int reliability) {
	this.reliability = reliability;
}

public void setStreet(String street) {
	this.street = street;
}

public void setSuppliernumber(String suppliernumber) {
	this.suppliernumber = suppliernumber;
}

public void setTitle(String title) {
	this.title = title;
}

public void setUseNetGross(int useNetGross) {
	this.useNetGross = useNetGross;
}

public void setVatnr(String vatnr) {
	this.vatnr = vatnr;
}

public void setVatnrvalid(int vatnrvalid) {
	this.vatnrvalid = vatnrvalid;
}

public void setWebsite(String website) {
	this.website = website;
}

public void setZip(String zip) {
	this.zip = zip;
}

}
