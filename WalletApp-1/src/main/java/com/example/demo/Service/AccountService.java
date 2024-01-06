package com.example.demo.Service;

import com.example.demo.Dto.AccountDto;
import com.example.demo.Dto.TransactionDto;

import java.time.LocalDate;
import java.util.List;

public interface AccountService {

    public AccountDto createAccount(AccountDto ad, int id);

    public List<AccountDto> showAllAccounts();

    public AccountDto depositeMoney(int accno, int depMoney, String mess);

    public String widhrawMoney(int accno, int widMoney, String mess);

    public String fundTransfer(int acc1, int acc2, int amount, String mess);

    public List<AccountDto> findByCustomer(int cuid);

    public List<TransactionDto> allTranByCustomer(int cuid);

    public List<Integer> allAcountsOfCust(int cid);

    public List<AccountDto> findByOPlessthan(double ob);

    public List<AccountDto> byDate(LocalDate start, LocalDate end);

    public List<AccountDto> opGreater(double obb);

    public List<AccountDto> distinctOp(double ob);

    public List<AccountDto> ascOpBal();

    public List<AccountDto> descOpBal();

}
