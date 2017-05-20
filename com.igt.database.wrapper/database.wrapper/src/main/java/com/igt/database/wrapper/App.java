package com.igt.database.wrapper;

import java.util.List;

import com.igt.database.wrapper.ogm.OGMDatabaseService;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		DatabaseService databaseService = new OGMDatabaseService("ogm-neo4j");
		
		List<CustomerEntity> customers =  databaseService.getCustomers();
		
		System.out.println(String.format("%d customer(s)", customers.size()));
	}
}
