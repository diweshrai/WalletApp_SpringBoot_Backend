package com.example.demo.Contro;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.AccountDto;
import com.example.demo.Dto.CustomerDto;
import com.example.demo.Dto.PageResponse;
import com.example.demo.Dto.TransactionDto;
import com.example.demo.Dto.TransactionDtoImpl;
import com.example.demo.ImplService.CustomerImpl;
import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.AddressService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.TransactionService;

import jakarta.validation.Valid;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")

public class CustomerContr {

    @Autowired
    CustomerImpl cimpl;

    @Autowired
    CustomerService cserv;

    @Value("${addedDays}")
    private int addedDays;


    /*
    Installed the Scheduler which is hiting on time which we set on the scheduler here we are doing a task where if the
    Customer Id is expired and he still didnt buy the susbcription then the customer status will be inactive
     */
    @Scheduled(cron = "30 39 21 * * ?")
    public void myScheduledTask() {


        String res = cimpl.makeCustomerStatusInActive();

        System.out.println(res);

    }

    @PostMapping("/addCust")
    public CustomerDto addcust(@Valid @RequestBody CustomerDto cd) {

        CustomerDto cdto = cimpl.addCustomer(cd);

        return cdto;

    }


    @PostMapping("/addCustomerwithexpirydate")
    public Customer addcustWithExpiryDate(@RequestBody CustomerDto cd) {

        Customer cc = cserv.addCustomerWithExpiryDate(cd, addedDays);

        return cc;

    }


    @GetMapping("/getall")
    public List<CustomerDto> getall() {

        return cimpl.getallCustomer();
    }

    @GetMapping("/getByLastName/{lname}")
    public ResponseEntity<String> getbyLastName(@PathVariable String lname) {
        String res = cimpl.findbyLastName(lname);

        return new ResponseEntity<String>(res, HttpStatus.OK);

    }

//*********************************  Login Check ******************************************************************************

    @GetMapping("/login/{email}/{pass}")
    public CustomerDto login(@PathVariable String email, @PathVariable String pass) {

        return cimpl.findbyemail(email, pass);
    }

// ************************************  Get all Accounts By Customer *******************************************************

    @GetMapping("/getAccountByCustomer/{cid}")
    public List<AccountDto> getAllAccountByCustomer(@PathVariable int cid) {
        List<AccountDto> resacc = accserv.findbyCustomer(cid);

        return resacc;

    }

// ************************************************* Get All Transaction Done By Customer **************************************

    @GetMapping("/allTransactionByCustomer/{cid}")
    public List<TransactionDto> allTrans(@PathVariable int cid) {
        return accserv.alltranByCustomer(cid);
    }

    ;

    @GetMapping("/getAllAccBy/{cid}")
    public List<Integer> allAccofC(@PathVariable int cid) {

        return accserv.allacountsOfCust(cid);
    }

    ;

// *******************************************  Create Account Table *****************************************************

    @Autowired
    AccountService accserv;

    @PostMapping("/addAccount/{id}")
    public AccountDto addAcc(@PathVariable int id, @RequestBody AccountDto addt) {

        AccountDto ad1 = accserv.createaccount(addt, id);

        return ad1;

    }

    @GetMapping("/getAllAccount")
    public List<AccountDto> getAllAccount() {
        List<AccountDto> acd1 = accserv.showAllAccounts();

        return acd1;
    }

    @PostMapping("/DepositeMoney/{accno}/{money}/{mess}")
    public AccountDto addMoney(@PathVariable int accno, @PathVariable int money, @PathVariable String mess) {
        System.out.println("accno is " + accno + "money" + money);
        AccountDto acdd = accserv.depositeMoney(accno, money, mess);
        return acdd;
    }

    @PostMapping("/WidhrawMoney/{accno}/{money}/{mess}")
    public String widhrawMoney(@PathVariable int accno, @PathVariable int money, @PathVariable String mess) {
        System.out.println("accno is " + accno + "money" + money);

        return accserv.widhrawMoney(accno, money, mess);
    }

// *****************************  Fund Transfer ********************************************

    @PostMapping("/FundTransfer/{ac1}/{ac2}/{amount}/{mess}")
    public String fundTransfer(@PathVariable int ac1, @PathVariable int ac2, @PathVariable int amount,
                               @PathVariable String mess) {

        return accserv.fundTransfer(ac1, ac2, amount, mess);

    }

// *************************  Account Repo queries *************************************************

    @GetMapping("/lessThanOpBal")
    public List<AccountDto> opbalLessThan(@RequestParam double ob) {
        return accserv.findbyOPlessthan(ob);
    }

    @GetMapping("/greaterThanOpBal")
    public List<AccountDto> oplessThan(@RequestParam double ob) {
        return accserv.opgreater(ob);
    }

    @GetMapping("/DistinctOpBal")
    public List<AccountDto> DistinctOp(@RequestParam double ob) {
        return accserv.distinctOp(ob);
    }

    @GetMapping("/AscOpBal")
    public List<AccountDto> AscOpBal() {
        return accserv.ascOpBal();
    }

    @GetMapping("/DescOpBal")
    public List<AccountDto> DescOpBal() {
        return accserv.DescOpBal();
    }

    @GetMapping("/byDate/{st}/{ed}")
    public List<AccountDto> bydatefind(@PathVariable LocalDate st, @PathVariable LocalDate ed) {

        return accserv.byDate(st, ed);
    }

// *************************************************  Customer Repo **************************************

    @GetMapping("/getByFirstName/{fnm}")
    public List<CustomerDto> firstName(@PathVariable String fnm) {
        return cimpl.firstnameConatining(fnm);
    }

    @GetMapping("/getByFirstNameIgnoreCase/{fnm}")
    public List<CustomerDto> firstNameIgnoreCase(@PathVariable String fnm) {
        return cimpl.firstNameIgnoreCase(fnm);
    }

// *******************************************  Address Repo******************************************************

    @Autowired
    AddressService adserv;

    @GetMapping("/NullAddress")
    public List<Address> nullAdd() {
        return adserv.addline2isNUll();
    }

    @GetMapping("/NotNullAddress")
    public List<Address> notNullAdd() {
        return adserv.addline2isNotNull();
    }

// ********************************************************  Transaction Service ***********************************

    @Autowired
    TransactionService tserv;

    @GetMapping("/getAllTransc")
    public List<Transaction> allTra() {
        return tserv.allTran();
    }

    @GetMapping("/getAllTranscByPageNumber/{pNumber}/{pSize}")
    public ResponseEntity<PageResponse> allTranByPageNumber(@PathVariable int pNumber, @PathVariable int pSize) {

        PageResponse p = tserv.pagi(pNumber, pSize);
        return new ResponseEntity<PageResponse>(p, HttpStatus.OK);
    }

    @GetMapping("/getAllTransactionByFromAccount/{fromAcc}")
    public List<TransactionDtoImpl> getAllByFromAccountCustom(@PathVariable int fromAcc) {

        return tserv.findAllByfromAccount(fromAcc);

    }

}
