package com.example.app;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomer_AccountNumber(String accountNumber);
}
