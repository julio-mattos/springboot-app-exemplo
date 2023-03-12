package com.springbootappexample.transaction.integration;

import com.springbootappexample.transaction.configuration.ApplicationExceptionHandler;
import com.springbootappexample.transaction.configuration.SecurityConfiguration;
import com.springbootappexample.transaction.controller.TransactionApiImpl;
import com.springbootappexample.transaction.exception.TransactionNotFoundException;
import com.springbootappexample.transaction.openapi.model.GeneralError;
import com.springbootappexample.transaction.repository.TransactionRepository;
import com.springbootappexample.transaction.service.TransactionService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import static com.springbootappexample.transaction.TransactionTestHelper.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@WithUserDetails
@ContextConfiguration(classes = {SecurityConfiguration.class, ApplicationExceptionHandler.class, TransactionApiImpl.class})
class TransactionApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    @MockBean
    private TransactionRepository repository;

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldGetAListOfTransactionResponse() throws Exception {

        var listTransaction = buildListTransactionResponse();

        when(service.list()).thenReturn(listTransaction);

        String jsonResponse = new Gson().toJson(listTransaction);

        mockMvc.perform(get("/transaction"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(service, times(1)).list();
    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldCreateATransaction() throws Exception {

        var transactionRequest = buildRequest();
        var simpleResponse = buildSimpleResponse("Transaction inserted successfully!");

        when(service.create(transactionRequest)).thenReturn(simpleResponse);

        String jsonRequest  = new Gson().toJson(transactionRequest);
        String jsonResponse = new Gson().toJson(simpleResponse);

        mockMvc.perform(post("/transaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(jsonResponse));

        verify(service, times(1)).create(transactionRequest);

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldThrowDuplicateKeyExceptionWhenPostATransaction() throws Exception {

        var transactionRequest = buildRequest();
        var generalReponse = new GeneralError().code(400).message("Your Id Already exists!");

        when(service.create(transactionRequest)).thenThrow(DuplicateKeyException.class);

        String jsonRequest  = new Gson().toJson(transactionRequest);
        String jsonResponse = new Gson().toJson(generalReponse);

        mockMvc.perform(post("/transaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(jsonResponse));

        verify(service, times(1)).create(transactionRequest);

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldUpdateATransaction() throws Exception {

        var transactionUpdateRequest = buildUpdateRequest();
        var simpleResponse = buildSimpleResponse("Transaction updated successfully!");

        when(service.update(transactionUpdateRequest)).thenReturn(simpleResponse);

        String jsonRequest  = new Gson().toJson(transactionUpdateRequest);
        String jsonResponse = new Gson().toJson(simpleResponse);

        mockMvc.perform(put("/transaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(service, times(1)).update(transactionUpdateRequest);

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldThrowNotFoundExceptionWhenUpdateATransaction() throws Exception {
        var transactionUpdateRequest = buildUpdateRequest();
        when(service.update(transactionUpdateRequest)).thenThrow(TransactionNotFoundException.class);

        String jsonRequest  = new Gson().toJson(transactionUpdateRequest);

        mockMvc.perform(put("/transaction")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service, times(1)).update(transactionUpdateRequest);

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldReturnWhenGetATransaction() throws Exception {

        var transactionResponse = buildResponse();

        when(service.get(anyString())).thenReturn(transactionResponse);

        var jsonResponse = new Gson().toJson(transactionResponse);

        mockMvc.perform(get("/transaction/" + transactionResponse.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));

        verify(service, times(1)).get(anyString());

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldThrowNotFoundExceptionWhenGetATransaction() throws Exception {

        when(service.get(anyString())).thenThrow(TransactionNotFoundException.class);

        mockMvc.perform(get("/transaction/1234")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


        verify(service, times(1)).get(anyString());

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldDeleteATransaction() throws Exception {

        var simpleResponse = buildSimpleResponse("Transaction deleted successfully!");

        when(service.delete(anyString())).thenReturn(simpleResponse);

        var jsonResponse = new Gson().toJson(simpleResponse);

        mockMvc.perform(delete("/transaction/0001")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonResponse));


        verify(service, times(1)).delete(anyString());

    }

    @Test
    @WithMockUser(value = "SpringBoot")
    void shouldThrowNotFoundExceptionWhenDeleteATransaction() throws Exception {

        when(service.delete(anyString())).thenThrow(TransactionNotFoundException.class);

        mockMvc.perform(delete("/transaction/1234")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());


        verify(service, times(1)).delete(anyString());

    }

}
