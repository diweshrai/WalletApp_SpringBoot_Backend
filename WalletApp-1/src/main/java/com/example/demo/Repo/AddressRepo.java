package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Address;

public interface AddressRepo extends JpaRepository<Address, Integer> {

	List<Address> findByAdressLine2IsNull();

	List<Address> findByAdressLine2IsNotNull();

}
