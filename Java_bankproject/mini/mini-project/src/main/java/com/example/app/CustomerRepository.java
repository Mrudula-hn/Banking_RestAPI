package com.example.app;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	 Customer findByAccountNumber(String accountNumber);
	    Optional<Customer> findByTemporaryPassword(String temporaryPassword);
	    Customer findByAccountNumberAndTemporaryPassword(String accountNumber,String temporaryPassword);
	    
	    default boolean deleteByIdSafely(Long id) {
	        try {
	            deleteById(id);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}
}
