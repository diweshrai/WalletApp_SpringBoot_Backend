package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDto {

    private int accountNo;

    private String accountType;

    private double openingBalance;

    private LocalDate openingDate;

    private String description;

}
