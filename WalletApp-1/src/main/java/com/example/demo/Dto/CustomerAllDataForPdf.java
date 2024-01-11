package com.example.demo.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAllDataForPdf {

		private String firstName;
	    private String lastName;
	    private String emailId;
	    private int contactNo;
	    private String Gender;
	    private LocalDate registrationDate;
	    private LocalDate expiryDate;
	   
	    private String addressLine1;
	    private String adressLine2;
	    private String city;
	    private String state;
	    private int pincode;
	    
	    private String transactionType;
	    private LocalDate transactionDate;
	    private double amount;
	    private String description;
	    private int fromAccount;
	    private int toAccount;

	    private int accountNo;
	    private String accountType;
	    private double openingBalance;
	    private LocalDate openingDate;
	    private String accountDescription;

};

