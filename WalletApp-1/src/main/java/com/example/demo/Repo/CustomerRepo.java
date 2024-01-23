package com.example.demo.Repo;

import com.example.demo.EnumData.CustomerType;
import com.example.demo.Model.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    Customer findByEmailIdAndPassword(String email, String password);
    List<Customer> findByFirstNameContaining(String name);
    Page<Customer> findByFirstNameContaining(String name, Pageable p);
    Page<Customer> findByFirstNameIgnoreCaseContaining(String firstName, Pageable pageable);
    Optional<Customer> findByEmailId(String emailId);
    List<Customer> findByFirstNameIgnoreCase(String fnm);
    	
    Customer findByCustomerId(int customerId);
    @Query("select  c.firstName from Customer c where c.lastName=:lastname ")
    public String findFirstnamebyLastName(String lastname);

    List<Customer> findByCustomerTypeAndExpiryDateAndCustomerStatus(CustomerType customerType, LocalDate expiryDate,
                                                                    String customerStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.customerStatus = :customerStatus WHERE c.customerId = :customerId")
    int updateCustomerStatus(@Param("customerId") int customerId, @Param("customerStatus") String customerStatus);

    
}
