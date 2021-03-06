package com.sebulli.fakturama.oldmodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 *  ###  Generated by EclipseLink Project EclipseLink Workbench 2.5.1 - Thu Nov 07 21:56:49 CET 2013.  ###
 */

@Entity
@Table(name = "Texts")
@ReadOnly
public class OldTexts {

	private String text;
	private String name;
	private boolean deleted;
	private String category;
	@Id
	private int id;

public OldTexts() {
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

public String getText() {
	return this.text;
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

public void setText(String text) {
	this.text = text;
}

}
