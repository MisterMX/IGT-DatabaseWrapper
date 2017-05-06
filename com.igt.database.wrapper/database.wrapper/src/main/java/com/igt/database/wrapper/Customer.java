package com.igt.database.wrapper;

import java.util.Date;
import java.util.Set;

/**
 * Customer entity
 */
public class Customer {
	private int id;
	private String surName, foreName;
	private Date birthDate;
	private Set<Bill> bills;
	
	public Set<Bill> getBills() {
		return bills;
	}
	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getForeName() {
		return foreName;
	}
	public void setForeName(String foreName) {
		this.foreName = foreName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}	
}
