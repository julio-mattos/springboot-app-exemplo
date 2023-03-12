package com.springbootappexample.transaction;

import com.springbootappexample.transaction.entity.Transaction;
import com.springbootappexample.transaction.enums.Status;
import com.springbootappexample.transaction.mapper.TransactionMapper;
import com.springbootappexample.transaction.openapi.model.SimpleTransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionRequest;
import com.springbootappexample.transaction.openapi.model.TransactionResponse;
import com.springbootappexample.transaction.openapi.model.TransactionUpdateRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionTestHelper {

    private TransactionTestHelper(){}

    public static List<TransactionResponse> buildListTransactionResponse() {
        var transactions =   List.of(new Transaction("001", "transaction1", "10242322808", "Client 1", Status.PENDING, new BigDecimal(100), LocalDateTime.now(), null),
                new Transaction("002", "transaction2", "12086611838", "Client 2", Status.PENDING, new BigDecimal(200), LocalDateTime.now(), null),
                new Transaction("003", "transaction3", "34015215869", "Client 3", Status.APPROVED, new BigDecimal(300), LocalDateTime.now(), null));
        return TransactionMapper.INSTANCE.map(transactions);
    }

    public static TransactionResponse buildResponse(){
        return new TransactionResponse()
                .id("0001")
                .description("transaction1")
                .cpf("10242322808")
                .origin("Client 1")
                .status(TransactionResponse.StatusEnum.PENDING)
                .value("200")
                .createdDate("2022-06-08T11:04:35");
    }

    public static TransactionRequest buildRequest(){
        return new TransactionRequest()
                .id("0001")
                .cpf("12086611838")
                .description("transaction1")
                .status(TransactionRequest.StatusEnum.PENDING)
                .value("100")
                .origin("teste")
                .createdDate("2022-06-08T11:04:35");
    }

    public static TransactionUpdateRequest buildUpdateRequest(){
        return new TransactionUpdateRequest()
                .id("0001")
                .description("update transaction")
                .status(TransactionUpdateRequest.StatusEnum.PENDING)
                .value("100");
    }

    public static SimpleTransactionResponse buildSimpleResponse(String message){
        return new SimpleTransactionResponse()
                .id("0001")
                .message(message);
    };
}