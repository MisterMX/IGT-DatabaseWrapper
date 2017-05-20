package com.igt.database.wrapper;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
public class BillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Field(analyze = Analyze.NO)
	private int totalValue;
	
	@Field(analyze = Analyze.NO)
	private Date purchaseDate;
	
	@ManyToOne
	private CustomerEntity customer;

	
	public BillEntity() {
		
	}
	
	public BillEntity(Long id, int totalValue, Date purchaseDate, CustomerEntity customer) {
		super();
		this.id = id;
		this.totalValue = totalValue;
		this.purchaseDate = purchaseDate;
		this.customer = customer;
	}
	
	
	public CustomerEntity getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
}
