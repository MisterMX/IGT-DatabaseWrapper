package com.igt.database.wrapper.ogm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;

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
        transaction.commit();
        entityManager.close();
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

	@Override
	public int clearCustomers() {
		EntityManager entityManager = factory.createEntityManager();
	
		EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        
        int deletedCount = entityManager.createQuery("DELETE FROM").executeUpdate();

        transaction.commit();
        
        entityManager.close();
        
        return deletedCount;
	}
}
