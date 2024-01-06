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
@SequenceGenerator(initialValue = 101, name = "accno", sequenceName = "accno")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accno")
    private int accountNo;

    private String accountType;

    private double openingBalance;

    private LocalDate openingDate;

    private String description;

    @ManyToOne
    @JoinColumn(name = "customerfkk")
    private Customer customer;

}
