package com.asn.smarthelmetproject.repository;

import com.asn.smarthelmetproject.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByCode(String code); // Add this method to find a transaction by code
    boolean existsByCode(String code);

}