package com.igt.database.wrapper.ogm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.igt.database.wrapper.CustomerEntity;
import com.igt.database.wrapper.DatabaseService;

public class OGMDatabaseService implements DatabaseService {

	private final EntityManagerFactory factory;
	
	public OGMDatabaseService(String persistenceUnitName) {
		factory = Persistence.createEntityManagerFactory(persistenceUnitName);
	}
	
	public void close() {
		// TODO Auto-generated method stub
		factory.close();
	}
		
	public void createCustomer(CustomerEntity customer) {
        EntityManager entityManager = factory.createEntityManager();
        
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        entityManager.persist(customer);

        entityManager.flush();
        entityManager.close();
        transaction.commit();
	}

	public List<CustomerEntity> getCustomers() {
		EntityManager entityManager = factory.createEntityManager();
		
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        List<CustomerEntity> result = entityManager
        		.createQuery("SELECT c FROM CustomerEntity c ORDER BY c.surName ASC, c.foreName ASC", CustomerEntity.class)
        		.getResultList();
        
        transaction.commit();
        entityManager.close();
       
        return result;
	}
}
