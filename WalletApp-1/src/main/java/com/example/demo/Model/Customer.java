package com.example.demo.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

	@OneToOne
	@JoinColumn(name = "addressfkk")
	private Address address;

	@OneToMany(targetEntity = Account.class, mappedBy = "customer")
	private List<Account> account = new ArrayList<>();

}
