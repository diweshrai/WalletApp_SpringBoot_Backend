package com.example.demo.Service;

import com.example.demo.Dto.PageResponse;
import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.Model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> allTran();

    PageResponse paginationUsingNumberAndSize(int pNumber, int pSize);

    List<TransactionDtoImpl> findAllByfromAccount(int fromAcc);

}
