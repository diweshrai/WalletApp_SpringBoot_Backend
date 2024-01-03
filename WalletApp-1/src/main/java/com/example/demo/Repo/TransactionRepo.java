package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.Model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

	@Query("SELECT t.transactionId, t.transactionType FROM Transaction t WHERE t.fromAccount = :fromAccount")
	List<TransactionDtoImpl> findTransactionIdAndTypeByFromAccount(int fromAccount);

}
