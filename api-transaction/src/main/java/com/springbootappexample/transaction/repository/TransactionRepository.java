package com.springbootappexample.transaction.repository;

import com.springbootappexample.transaction.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
