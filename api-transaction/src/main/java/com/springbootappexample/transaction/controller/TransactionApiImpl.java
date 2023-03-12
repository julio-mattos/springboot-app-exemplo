package com.springbootappexample.transaction.controller;

import com.springbootappexample.transaction.openapi.api.TransactionApi;
import com.springbootappexample.transaction.openapi.model.SimpleTransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionRequest;
import com.springbootappexample.transaction.openapi.model.TransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionUpdateRequest;
import com.springbootappexample.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class TransactionApiImpl implements TransactionApi {

    private final TransactionService transaction;

    @Override
    public ResponseEntity<List<TransactionResponse>> transactionList(){
        return ResponseEntity.ok(transaction.list());
    }

    @Override
    public ResponseEntity<SimpleTransactionResponse> post(TransactionRequest transactionRequest){
       return ResponseEntity
               .created(URI.create("/transaction"))
               .body(transaction.create(transactionRequest));
    }

    @Override
    public ResponseEntity<SimpleTransactionResponse> delete(String id){
        return ResponseEntity.ok(transaction.delete(id));
    }

    @Override
    public ResponseEntity<TransactionResponse> get(String id){
        return ResponseEntity.ok(transaction.get(id));
    }

    @Override
    public ResponseEntity<SimpleTransactionResponse> put(TransactionUpdateRequest transactionUpdateRequest) {
        return ResponseEntity.ok(transaction.update(transactionUpdateRequest));
    }
}
