package com.springbootappexample.transaction.test;

import com.springbootappexample.transaction.controller.TransactionApiImpl;
import com.springbootappexample.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static com.springbootappexample.transaction.TransactionTestHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class TransactionApiImplUnitTest {

    TransactionService service = Mockito.mock(TransactionService.class);
    TransactionApiImpl transactionApi = new TransactionApiImpl(service);

    @Test
    void shouldReturnTransactionList(){

        var listTransaction = buildListTransactionResponse();

        when(service.list()).thenReturn(listTransaction);
        assertEquals(listTransaction, transactionApi.transactionList().getBody() );
    }

    @Test
    void shouldCreateTransaction(){

        var simpleResponse = buildSimpleResponse("Transaction inserted successfully!");
        when(service.create(buildRequest())).thenReturn(simpleResponse);

        var response = transactionApi.post(buildRequest()).getBody();
        assertEquals(simpleResponse, response);

    }

    @Test
    void shouldUpdateTransaction(){

        var simpleResponse = buildSimpleResponse("Transaction updated successfully!");
        when(service.update(buildUpdateRequest())).thenReturn(simpleResponse);

        var response = transactionApi.put(buildUpdateRequest()).getBody();
        assertEquals(simpleResponse, response);

    }

    @Test
    void shouldReturnTransactionById(){

        var transaction = buildResponse();
        when(service.get(anyString())).thenReturn(transaction);

        var transactionResponse = transactionApi.get(anyString()).getBody();
        assertEquals(transaction, transactionResponse);

    }

    @Test
    void shouldDeleteTransaction(){

        var simpleResponse = buildSimpleResponse("Transaction delete successfully!");
        when(service.delete(anyString())).thenReturn(simpleResponse);

        var response = transactionApi.delete(anyString()).getBody();
        assertEquals(simpleResponse, response);

    }

}
