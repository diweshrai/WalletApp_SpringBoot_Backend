package com.example.demo.Repo;

import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t.transactionId, t.transactionType FROM Transaction t WHERE t.fromAccount = :fromAccount")
    List<TransactionDtoImpl> findTransactionIdAndTypeByFromAccount(int fromAccount);

}
