package com.sebulli.fakturama.oldmodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 *  ###  Generated by EclipseLink Project EclipseLink Workbench 2.5.1 - Thu Nov 07 21:56:49 CET 2013.  ###
 */

@Entity
@Table(name = "Properties")
@ReadOnly
public class OldProperties {

	private String value;
	private String name;
	@Id
	private int id;

public OldProperties() {
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
