package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Model.Customer;

public interface CustomerService {

	public CustomerDto addCustomer(CustomerDto cd);

	public List<CustomerDto> getallCustomer();

	public CustomerDto findbyemail(String email, String pass);

	public List<CustomerDto> firstnameConatining(String fnm);

	public List<CustomerDto> firstNameIgnoreCase(String fnm);

	public String findbyLastName(String lName);
	
	public Customer addCustomerWithExpiryDate(CustomerDto customer1, int addDays);
	
	

}
