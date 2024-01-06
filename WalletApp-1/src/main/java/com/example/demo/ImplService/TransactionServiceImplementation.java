package com.example.demo.ImplService;

import com.example.demo.Dto.PageResponse;
import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.Model.Transaction;
import com.example.demo.Repo.TransactionRepo;
import com.example.demo.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImplementation implements TransactionService {

    @Autowired
    TransactionRepo trepo;

    @Override
    public List<Transaction> allTran() {

        List<Transaction> lst = trepo.findAll();

        return lst;
    }

    @Override
    public PageResponse paginationUsingNumberAndSize(int pNumber, int pSize) {

        PageRequest p = PageRequest.of(pNumber, pSize);

        Page<Transaction> pagelst = this.trepo.findAll(p);

        List<Transaction> tlst = pagelst.getContent();

        PageResponse presp = new PageResponse();

        presp.setContent(tlst);
        presp.setPageNumber(pagelst.getNumber());
        presp.setPageSize(pagelst.getSize());
        presp.setTotalElements(pagelst.getTotalElements());
        presp.setTotalPages(pagelst.getTotalPages());
        presp.setLastPage(pagelst.isLast());

        return presp;
    }

    @Override
    public List<TransactionDtoImpl> findAllByfromAccount(int fromAcc) {

        List<TransactionDtoImpl> obj = trepo.findTransactionIdAndTypeByFromAccount(fromAcc);

        return obj;

    }

}
