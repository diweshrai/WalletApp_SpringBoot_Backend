package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.Dto.AccountDto;
import com.example.demo.Dto.TransactionDto;

public interface AccountService {

	public AccountDto createaccount(AccountDto ad, int id);

	public List<AccountDto> showAllAccounts();

	public AccountDto depositeMoney(int accno, int depMoney, String mess);

	public String widhrawMoney(int accno, int widMoney, String mess);

	public String fundTransfer(int acc1, int acc2, int amount, String mess);

	public List<AccountDto> findbyCustomer(int cuid);

	public List<TransactionDto> alltranByCustomer(int cuid);

	public List<Integer> allacountsOfCust(int cid);

	public List<AccountDto> findbyOPlessthan(double ob);

	public List<AccountDto> byDate(LocalDate start, LocalDate end);

	public List<AccountDto> opgreater(double obb);

	public List<AccountDto> distinctOp(double ob);

	public List<AccountDto> ascOpBal();

	public List<AccountDto> DescOpBal();

}
