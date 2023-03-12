package com.springbootappexample.transaction.mapper;

import com.springbootappexample.transaction.entity.Transaction;
import com.springbootappexample.transaction.openapi.model.TransactionRequest;
import com.springbootappexample.transaction.openapi.model.TransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionResponse map(Transaction source);

    Transaction map(TransactionRequest source);

    Transaction map(TransactionUpdateRequest source);

    List<TransactionResponse> map(List<Transaction> list);



}
