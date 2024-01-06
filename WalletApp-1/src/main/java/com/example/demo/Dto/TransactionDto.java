package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {

    private int transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String description;

    private int fromAccount;

    private int toAccount;

}
