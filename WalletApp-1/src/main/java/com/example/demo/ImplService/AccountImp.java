package com.example.demo.ImplService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Dto.AccountDto;
import com.example.demo.Dto.TransactionDto;
import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.example.demo.Repo.AccountRepo;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Repo.TransactionRepo;
import com.example.demo.Service.AccountService;

@Service
public class AccountImp implements AccountService {

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AccountRepo arepo;

	@Autowired
	CustomerRepo crepo;

	@Autowired
	TransactionRepo trrepo;

	@Override
	public AccountDto createaccount(AccountDto ad, int id) {

		Account acc2 = this.dtoToAccount(ad);

		Customer c11 = crepo.findById(id).get();

		acc2.setCustomer(c11);

		Account acc3 = arepo.save(acc2);

		AccountDto ad22 = this.accoutToDto(acc3);

		return ad22;
	}

	@Override
	public List<AccountDto> showAllAccounts() {

		List<Account> ac1 = arepo.findAll();

		List<AccountDto> acd1 = ac1.stream().map(account -> this.accoutToDto(account)).collect(Collectors.toList());

		return acd1;
	}

// *******************************  Deposite Money To Account *********************************************************	

	@Override
	public AccountDto depositeMoney(int accno, int depMoney, String mess) {

		double finalbalance = this.addbal(depMoney, accno);

		Account acp = arepo.findById(accno).get();

		acp.setOpeningBalance(finalbalance);

		arepo.save(acp);

		Transaction tr1 = new Transaction();
		tr1.setTransactionType("Deposite");
		tr1.setTransactionDate(LocalDate.now());
		tr1.setAmount(depMoney);
		tr1.setDescription(mess);
		tr1.setFromAccount(accno);
		tr1.setToAccount(accno);

		trrepo.save(tr1);

		AccountDto acd = this.accoutToDto(acp);

		return acd;
	}

	private double addbal(int money, int accno) {
		Account acc1 = arepo.findById(accno).get();

		double prevbal = acc1.getOpeningBalance();

		System.out.println("prev ball" + prevbal);

		double newbal = (prevbal + money);

		System.out.println("new bal" + newbal);
		return newbal;

	}

// ***********************************   Widthraw Money **********************************************************

	@Override
	public String widhrawMoney(int accno, int widMoney, String mess) {
		Account acw = this.findbyid(accno);

		double finalAmount = this.minusMoney(accno, widMoney);

		if (finalAmount >= 0) {

			acw.setOpeningBalance(finalAmount);

			arepo.save(acw);

			Transaction tr2 = new Transaction();

			tr2.setTransactionType("Widhraw");
			tr2.setTransactionDate(LocalDate.now());
			tr2.setAmount(widMoney);
			tr2.setDescription(mess);
			tr2.setFromAccount(accno);
			tr2.setToAccount(accno);

			trrepo.save(tr2);

			double remainbal = acw.getOpeningBalance();

			String ress = "Transaction Done.Your Remaining Balanace is" + remainbal;
			return ress;

		}

		else {
			double lowbal = acw.getOpeningBalance();

			String lowres = "Low Balance.Your Balance is " + lowbal + "add some Balance first";
			return lowres;

		}

	};

	private Account findbyid(int accno) {
		return arepo.findById(accno).get();
	}

	private double minusMoney(int accno, int widMoney) {
		Account acm1 = arepo.findById(accno).get();
		double prevMoney = acm1.getOpeningBalance();

		double minusAmount = (prevMoney - widMoney);

		return minusAmount;

	}

//*****************************************  Fund Transfer ***********************************************	

	@Override
	public String fundTransfer(int acc1, int acc2, int amount, String mess) {

		Account a1 = this.findbyid(acc1);

		double minus1 = this.minusMoney(acc1, amount);

		if (minus1 >= 0) {

			a1.setOpeningBalance(minus1);

			arepo.save(a1);

			Account a2 = this.findbyid(acc2);

			double plus2 = this.addbal(amount, acc2);

			a2.setOpeningBalance(plus2);

			arepo.save(a2);

			Transaction tr2 = new Transaction();

			tr2.setTransactionType("FundTransfer");
			tr2.setTransactionDate(LocalDate.now());
			tr2.setAmount(amount);
			tr2.setDescription(mess);
			tr2.setFromAccount(acc1);
			tr2.setToAccount(acc2);

			trrepo.save(tr2);

			double remainbal = a1.getOpeningBalance();

			String resf = "Fund Transfered.Your Remaining Balance is " + remainbal;
			return resf;
		}

		else {

			double lowbal = a1.getOpeningBalance();

			String lowres = "Insufficient balance.Your Current balance is:" + lowbal + " add some balance first";

			return lowres;
		}

	};

// *************************************************  Find all Account for Customer ***********************************

	@Override
	public List<AccountDto> findbyCustomer(int cuid) {

		Customer cf = crepo.findById(cuid).get();

		List<Account> acf = arepo.findByCustomer(cf);

		// List<Integer> allaccno = acf.stream().map((e)->
		// e.getAccountNo()).collect(Collectors.toList());

		List<AccountDto> acdlst = acf.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	};

// ****************************************   Transaction  of a customer ************************************************

	@Override
	public List<TransactionDto> alltranByCustomer(int cuid) {

		return this.alltran(cuid);

	}

	private List<TransactionDto> alltran(int cidd) {

		List<Integer> liall = this.allAcctoCustomer(cidd);

		List<Transaction> alltran = trrepo.findAll();

		List<Transaction> filteredTransactions = alltran.stream()
				.filter(transaction -> liall.contains(transaction.getFromAccount())
						|| liall.contains(transaction.getToAccount()))
				.collect(Collectors.toList());

		List<TransactionDto> lstfinall = filteredTransactions.stream().map((e) -> this.TransactiontoDto(e))
				.collect(Collectors.toList());

		return lstfinall;

	};

	private List<Integer> allAcctoCustomer(int cid) {
		Customer cf = crepo.findById(cid).get();

		List<Account> acf = arepo.findByCustomer(cf);

		List<Integer> allaccno = acf.stream().map((e) -> e.getAccountNo()).collect(Collectors.toList());

		return allaccno;
	};

	@Override
	public List<Integer> allacountsOfCust(int cid) {
		return this.allAcctoCustomer(cid);
	}

// **************************************  Current Date **********************************************

//	private String CurrnetTime() {
//	
//    LocalDate currentDate = LocalDate.now();
//
//    // Define a formatter for the desired format
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    // Format the current date as a string
//    String formattedDate = currentDate.format(formatter);
//	
//    return formattedDate;
//	}

// **************************************************Model Mapper******************	

	@SuppressWarnings("unused")
	private Transaction dtoToTransaction(TransactionDto tdt) {
		return this.modelMapper.map(tdt, Transaction.class);
	}

	@SuppressWarnings("unused")
	private TransactionDto TransactiontoDto(Transaction trr) {
		return this.modelMapper.map(trr, TransactionDto.class);
	}

	private Account dtoToAccount(AccountDto adt) {
		Account ac1 = this.modelMapper.map(adt, Account.class);

		return ac1;

	}

	private AccountDto accoutToDto(Account acc) {
		AccountDto acd = this.modelMapper.map(acc, AccountDto.class);
		return acd;

	}

//****************************************************  Different repo Queries	****************

	@Override
	public List<AccountDto> findbyOPlessthan(double ob) {
		List<Account> lst1 = arepo.findByOpeningBalanceLessThan(ob);

		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

	@Override
	public List<AccountDto> byDate(LocalDate start, LocalDate end) {

		List<Account> lst1 = arepo.findByOpeningDateBetween(start, end);

		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

	@Override
	public List<AccountDto> opgreater(double obb) {

		List<Account> lst1 = arepo.findByOpeningBalanceGreaterThan(obb);
		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

	@Override
	public List<AccountDto> distinctOp(double ob) {

		List<Account> lst1 = arepo.findDistinctByOpeningBalance(ob);

		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

	@Override
	public List<AccountDto> ascOpBal() {
		List<Account> lst1 = arepo.findByOrderByOpeningBalanceAsc();
		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

	@Override
	public List<AccountDto> DescOpBal() {
		List<Account> lst1 = arepo.findByOrderByOpeningBalanceDesc();
		List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

		return acdlst;

	}

}
