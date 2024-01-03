package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	Customer findByEmailIdAndPassword(String email, String password);

	List<Customer> findByFirstNameContaining(String name);

	List<Customer> findByFirstNameIgnoreCase(String fnm);

	@Query("select  c.firstName from Customer c where c.lastName=:lastname ")
	public String findFirstnamebyLastName(String lastname);

}
