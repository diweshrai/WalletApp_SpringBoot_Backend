package com.example.demo.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.EnumData.CustomerType;

import jakarta.persistence.*;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer {

	@Id
	@GeneratedValue
	private int customerId;

	private String firstName;

	private String lastName;

	private String emailId;

	private int contactNo;

	private String Gender;

	private String password;

	private LocalDate registrationDate;

	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	
	private LocalDate expiryDate;
	
	private String customerStatus;
	

	@OneToOne
	@JoinColumn(name = "addressfkk")
	private Address address;

	@OneToMany(targetEntity = Account.class, mappedBy = "customer")
	private List<Account> account = new ArrayList<>();

}
