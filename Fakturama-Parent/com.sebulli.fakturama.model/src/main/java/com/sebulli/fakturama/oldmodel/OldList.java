package com.sebulli.fakturama.oldmodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 *  ###  Generated by EclipseLink Project EclipseLink Workbench 2.5.1 - Thu Nov 07 21:56:49 CET 2013.  ###
 */

@Entity
@Table(name = "List")
@ReadOnly
public class OldList {

	private String value;
	private String name;
	private boolean deleted;
	private String category;
	
	@Id
	private int id;

public OldList() {
}

public String getCategory() {
	return this.category;
}

public int getId() {
	return this.id;
}

public String getName() {
	return this.name;
}

public String getValue() {
	return this.value;
}

public boolean isDeleted() {
	return this.deleted;
}

public void setCategory(String category) {
	this.category = category;
}

public void setDeleted(boolean deleted) {
	this.deleted = deleted;
}

public void setId(int id) {
	this.id = id;
}

public void setName(String name) {
	this.name = name;
}

public void setValue(String value) {
	this.value = value;
}

}
