package com.example.demo.Dto;

import java.time.LocalDate;

import com.example.demo.EnumData.CustomerType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDataRowGrids {
		private int customerId;
	    private String firstName;
	    private String lastName;
	    private String emailId;
	    private int contactNo;
	    private String Gender;
	    private LocalDate registrationDate;
	    private String addressLine1;
	    private String adressLine2;
	    private String city;
	    private String state;
	    private int pincode;
	    private LocalDate expiryDate;
	    private String customerStatus;
	    private CustomerType customerType;
}
