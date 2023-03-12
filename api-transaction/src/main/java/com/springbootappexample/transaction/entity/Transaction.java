package com.springbootappexample.transaction.entity;

import com.springbootappexample.transaction.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document("transaction")
public class Transaction {

    @Id
    private String id;
    private String description;
    private String cpf;
    private String origin;
    private Status status;
    private BigDecimal value;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime updateDate;
}
