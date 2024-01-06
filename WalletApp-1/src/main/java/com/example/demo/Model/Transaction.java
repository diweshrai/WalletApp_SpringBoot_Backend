package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(initialValue = 1001, name = "tran", sequenceName = "tran")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tran")
    private int transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String description;

    private int fromAccount;

    private int toAccount;

}
