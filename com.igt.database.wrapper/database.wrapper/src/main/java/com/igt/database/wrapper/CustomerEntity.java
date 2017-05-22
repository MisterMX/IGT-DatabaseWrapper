package com.igt.database.wrapper;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field(analyze = Analyze.NO)
	private String surName, foreName;
	
	@Field(analyze = Analyze.NO)
	private Date birthDate;
	
	@OneToMany(mappedBy = "customer")
	private Set<BillEntity> bills;
	
	
	public CustomerEntity() {
		
	}
	
	public CustomerEntity(String surName, String foreName, Date birthDate, Set<BillEntity> bills) {
		this.surName = surName;
		this.foreName = foreName;
		this.birthDate = birthDate;
		this.bills = bills;
	}

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public Set<BillEntity> getBills() {
		return bills;
	}
	public void setBills(Set<BillEntity> bills) {
		this.bills = bills;
	}
}
