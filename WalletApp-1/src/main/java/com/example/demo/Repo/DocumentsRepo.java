package com.example.demo.Repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Documents;

public interface DocumentsRepo extends JpaRepository<Documents, Integer> {

	List<Documents> findByCustomerId(int customerId);

	List<Documents> findByCustomerIdAndDocumentTypeAndDocStatus(int customerId, String documentType, String docStatus);
	
	List<Documents> findByCustomerIdAndDocStatus(int customerId,  String docStatus);
	
	
	
	
}
