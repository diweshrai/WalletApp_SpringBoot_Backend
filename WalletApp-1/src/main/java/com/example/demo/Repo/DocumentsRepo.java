package com.example.demo.Repo;

import com.example.demo.Model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentsRepo extends JpaRepository<Documents, Integer> {

    List<Documents> findByCustomerId(int customerId);

    List<Documents> findByCustomerIdAndDocumentTypeAndDocStatus(int customerId, String documentType, String docStatus);

    List<Documents> findByCustomerIdAndDocStatus(int customerId, String docStatus);

}
