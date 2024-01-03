package com.example.demo.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
