package com.example.demo.Repo;

import com.example.demo.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Integer> {

    List<Address> findByAdressLine2IsNull();

    List<Address> findByAdressLine2IsNotNull();

}
