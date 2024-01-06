package com.example.demo.Service;

import com.example.demo.Dto.CustomerDto;
import com.example.demo.Model.Address;

import java.util.List;

public interface AddressService {

    public CustomerDto addAddress(CustomerDto cd);

    public CustomerDto getAddress();

    List<Address> addLine2IsNUll();

    List<Address> addLine2IsNotNull();

}
