package com.example.demo.ImplService;

import com.example.demo.Dto.AccountDto;
import com.example.demo.Dto.TransactionDto;
import com.example.demo.Model.Account;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.example.demo.Repo.AccountRepo;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Repo.TransactionRepo;
import com.example.demo.Service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Override
    public AccountDto createAccount(AccountDto ad, int id) {
        Account acc2 = this.dtoToAccount(ad);
        Customer c11 = customerRepo.findById(id).get();
        acc2.setCustomer(c11);
        Account acc3 = accountRepo.save(acc2);
        AccountDto ad22 = this.accoutToDto(acc3);
        return ad22;
    }

    ;

    @Override
    public List<AccountDto> showAllAccounts() {
        List<Account> ac1 = accountRepo.findAll();
        List<AccountDto> acd1 = ac1.stream().map(account -> this.accoutToDto(account)).collect(Collectors.toList());
        return acd1;
    }

    ;

// *******************************  Deposite Money To Account *********************************************************	

    @Override
    public AccountDto depositeMoney(int accno, int depMoney, String mess) {
        double finalbalance = this.addBalance(depMoney, accno);
        Account acp = accountRepo.findById(accno).get();
        acp.setOpeningBalance(finalbalance);
        accountRepo.save(acp);
        Transaction tr1 = new Transaction();
        tr1.setTransactionType("Deposite");
        tr1.setTransactionDate(LocalDate.now());
        tr1.setAmount(depMoney);
        tr1.setDescription(mess);
        tr1.setFromAccount(accno);
        tr1.setToAccount(accno);
        transactionRepo.save(tr1);
        AccountDto acd = this.accoutToDto(acp);

        return acd;
    }

    ;

    private double addBalance(int money, int accno) {
        Account acc1 = accountRepo.findById(accno).get();

        double prevbal = acc1.getOpeningBalance();

        System.out.println("prev ball" + prevbal);

        double newbal = (prevbal + money);

        System.out.println("new bal" + newbal);
        return newbal;

    }

// ***********************************   Widthraw Money **********************************************************

    @Override
    public String widhrawMoney(int accno, int widMoney, String mess) {
        Account acw = this.findById(accno);

        double finalAmount = this.minusMoney(accno, widMoney);

        if (finalAmount >= 0) {

            acw.setOpeningBalance(finalAmount);

            accountRepo.save(acw);

            Transaction tr2 = new Transaction();

            tr2.setTransactionType("Widhraw");
            tr2.setTransactionDate(LocalDate.now());
            tr2.setAmount(widMoney);
            tr2.setDescription(mess);
            tr2.setFromAccount(accno);
            tr2.setToAccount(accno);

            transactionRepo.save(tr2);

            double remainbal = acw.getOpeningBalance();

            String ress = "Transaction Done.Your Remaining Balanace is" + remainbal;
            return ress;

        } else {
            double lowbal = acw.getOpeningBalance();

            String lowres = "Low Balance.Your Balance is " + lowbal + "add some Balance first";
            return lowres;

        }

    }

    ;

    private Account findById(int accno) {
        return accountRepo.findById(accno).get();
    }

    private double minusMoney(int accno, int widMoney) {
        Account acm1 = accountRepo.findById(accno).get();
        double prevMoney = acm1.getOpeningBalance();

        double minusAmount = (prevMoney - widMoney);

        return minusAmount;

    }

//*****************************************  Fund Transfer ***********************************************	

    @Override
    public String fundTransfer(int acc1, int acc2, int amount, String mess) {

        Account a1 = this.findById(acc1);

        double minus1 = this.minusMoney(acc1, amount);

        if (minus1 >= 0) {

            a1.setOpeningBalance(minus1);

            accountRepo.save(a1);

            Account a2 = this.findById(acc2);

            double plus2 = this.addBalance(amount, acc2);

            a2.setOpeningBalance(plus2);

            accountRepo.save(a2);

            Transaction tr2 = new Transaction();

            tr2.setTransactionType("FundTransfer");
            tr2.setTransactionDate(LocalDate.now());
            tr2.setAmount(amount);
            tr2.setDescription(mess);
            tr2.setFromAccount(acc1);
            tr2.setToAccount(acc2);

            transactionRepo.save(tr2);

            double remainbal = a1.getOpeningBalance();

            String resf = "Fund Transfered.Your Remaining Balance is " + remainbal;
            return resf;
        } else {

            double lowbal = a1.getOpeningBalance();

            String lowres = "Insufficient balance.Your Current balance is:" + lowbal + " add some balance first";

            return lowres;
        }

    }

    ;

// *************************************************  Find all Account for Customer ***********************************

    @Override
    public List<AccountDto> findByCustomer(int cuid) {

        Customer cf = customerRepo.findById(cuid).get();

        List<Account> acf = accountRepo.findByCustomer(cf);

        return acf.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());
    }

    ;

// ****************************************   Transaction  of a customer ************************************************

    @Override
    public List<TransactionDto> allTranByCustomer(int cuid) {

        return this.allTran(cuid);

    }

    private List<TransactionDto> allTran(int cidd) {

        List<Integer> liall = this.allAcctoCustomer(cidd);

        List<Transaction> alltran = transactionRepo.findAll();

        List<Transaction> filteredTransactions = alltran.stream()
                .filter(transaction -> liall.contains(transaction.getFromAccount())
                        || liall.contains(transaction.getToAccount()))
                .collect(Collectors.toList());

        return filteredTransactions.stream().map((e) -> this.TransactiontoDto(e)).collect(Collectors.toList());

    }

    ;

    private List<Integer> allAcctoCustomer(int cid) {
        Customer cf = customerRepo.findById(cid).get();

        List<Account> acf = accountRepo.findByCustomer(cf);

        return acf.stream().map((e) -> e.getAccountNo()).collect(Collectors.toList());

    }

    ;

    @Override
    public List<Integer> allAcountsOfCust(int cid) {
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
    public List<AccountDto> findByOPlessthan(double ob) {
        List<Account> lst1 = accountRepo.findByOpeningBalanceLessThan(ob);

        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

    @Override
    public List<AccountDto> byDate(LocalDate start, LocalDate end) {

        List<Account> lst1 = accountRepo.findByOpeningDateBetween(start, end);

        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

    @Override
    public List<AccountDto> opGreater(double obb) {

        List<Account> lst1 = accountRepo.findByOpeningBalanceGreaterThan(obb);
        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

    @Override
    public List<AccountDto> distinctOp(double ob) {

        List<Account> lst1 = accountRepo.findDistinctByOpeningBalance(ob);

        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

    @Override
    public List<AccountDto> ascOpBal() {
        List<Account> lst1 = accountRepo.findByOrderByOpeningBalanceAsc();
        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

    @Override
    public List<AccountDto> descOpBal() {
        List<Account> lst1 = accountRepo.findByOrderByOpeningBalanceDesc();
        List<AccountDto> acdlst = lst1.stream().map((acc) -> this.accoutToDto(acc)).collect(Collectors.toList());

        return acdlst;

    }

}
