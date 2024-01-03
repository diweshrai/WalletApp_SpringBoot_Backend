package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Model.Address;

public interface AddressService {

	public CustomerDto addAddress(CustomerDto cd);

	public CustomerDto getAddress();

	List<Address> addline2isNUll();

	List<Address> addline2isNotNull();

}
