package com.igt.database.wrapper.ogm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.igt.database.wrapper.CustomerEntity;
import com.igt.database.wrapper.DatabaseService;

public class OGMDatabaseService implements DatabaseService {

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("ogm-ne4j");
	
	public void createCustomer(CustomerEntity customer) {
        EntityManager entityManager = factory.createEntityManager();
        
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        entityManager.persist(customer);
        
        transaction.commit();
        entityManager.close();
	}

	public List<CustomerEntity> getCustomers() {
		EntityManager entityManager = factory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        Query query = entityManager.createQuery("from Customer c");
        
        List<CustomerEntity> customers = new ArrayList<CustomerEntity>();
        for (Object obj : query.getResultList()) {
        	customers.add((CustomerEntity)obj);
        }
        
        transaction.commit();
        entityManager.close();
       
        return customers;
	}
}
