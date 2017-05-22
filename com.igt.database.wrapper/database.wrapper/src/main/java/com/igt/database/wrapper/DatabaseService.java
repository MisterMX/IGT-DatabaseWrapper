package com.igt.database.wrapper;

import java.util.List;

public interface DatabaseService {
	void createCustomer(CustomerEntity customer);
	
	List<CustomerEntity> getCustomers();
	
	void close();
}
