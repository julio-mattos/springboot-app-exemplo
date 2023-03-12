package com.springbootappexample.transaction.service;

import com.springbootappexample.transaction.entity.Transaction;
import com.springbootappexample.transaction.exception.TransactionNotFoundException;
import com.springbootappexample.transaction.mapper.TransactionMapper;
import com.springbootappexample.transaction.openapi.model.SimpleTransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionRequest;
import com.springbootappexample.transaction.openapi.model.TransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionUpdateRequest;
import com.springbootappexample.transaction.repository.TransactionRepository;

import com.springbootappexample.transaction.constants.StringConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    public final TransactionRepository repository;

    public List<TransactionResponse> list(){
        log.info("Starting find transaction...");

        var transactions = repository.findAll();
        log.info("Transactions found: {}", transactions.toString());

        return TransactionMapper.INSTANCE.map(transactions);
    }

    public SimpleTransactionResponse create(TransactionRequest transactionRequest){
        log.info("Creating a transaction with request: {}", transactionRequest.toString());

        var transaction = repository.insert(TransactionMapper.INSTANCE.map(transactionRequest));
        log.info("Transaction created: {}", transaction);

        return new SimpleTransactionResponse()
                .id(transaction.getId())
                .message(StringConstants.INSERTED);
    }

    public SimpleTransactionResponse update(TransactionUpdateRequest transactionUpdateRequest){
        var transaction = TransactionMapper.INSTANCE.map(transactionUpdateRequest);

        log.info("Update a transaction with request: {}", transaction.toString());

        repository.findById(transaction.getId())
                .ifPresentOrElse(
                        t -> {
                    updateTransaction(t, transaction);
                    repository.save(t);
                    log.info("Transaction updated: {}", t);
                },
                        () -> {
                            log.error("Transaction not found: {}", transaction.getId());
                            throw new TransactionNotFoundException(StringConstants.NOTFOUND);
                    });

        return new SimpleTransactionResponse().id(transaction.getId()).message(StringConstants.UPDATED);
    }

    public TransactionResponse get(String id){

        log.info("Find transaction by id: {}", id);
        var transaction = repository.findById(id);

        if (transaction.isEmpty()) {
            log.error("Transaction not found by id: {}", id);
            throw new TransactionNotFoundException(StringConstants.NOTFOUND);
        }

        log.info("Transaction found: {}", transaction);

        return TransactionMapper.INSTANCE.map(transaction.get());

    }

    public SimpleTransactionResponse delete(String id){

        log.info("Tryng to delete a transaction with id: {}", id);

        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> {
                            log.error("Transaction not found: {}", id);
                            throw new TransactionNotFoundException(StringConstants.NOTFOUND);
                        });

        return new SimpleTransactionResponse().id(id).message(StringConstants.DELETED);
    }

    private void updateTransaction(Transaction saved, Transaction coming){

        if (coming.getDescription() != null)
            saved.setDescription(coming.getDescription());

        if (coming.getStatus() != null)
            saved.setStatus(coming.getStatus());

        if(coming.getValue() != null)
            saved.setValue(coming.getValue());

        saved.setUpdateDate(LocalDateTime.now());
    }

}
