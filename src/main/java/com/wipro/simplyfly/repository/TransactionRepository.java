package com.wipro.simplyfly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.simplyfly.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
