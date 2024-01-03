package com.example.demo.Service;

import java.util.List;

import com.example.demo.Dto.PageResponse;
import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.Model.Transaction;

public interface TransactionService {

	List<Transaction> allTran();

	PageResponse pagi(int pNumber, int pSize);

	List<TransactionDtoImpl> findAllByfromAccount(int fromAcc);

}
