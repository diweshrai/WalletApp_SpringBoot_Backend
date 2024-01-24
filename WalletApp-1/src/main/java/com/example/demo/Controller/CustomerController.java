package com.example.demo.Controller;

import com.example.demo.Dto.*;
import com.example.demo.ImplService.CustomerServiceImplementation;
import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.AddressService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.TransactionService;
import com.itextpdf.text.DocumentException;

import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    CustomerServiceImplementation customerServiceImplementation;
    @Autowired
    CustomerService customerService;
    @Autowired
    AccountService accountService;
    @Autowired
    AddressService addressService;
    @Autowired
    TransactionService transactionService;
    
    @Value("${addedDays}")
    private int addedDays;

    /*
     * Installed the Scheduler which is hiting on time which we set on the scheduler
     * here we are doing a task where if the Customer Id is expired and he still
     * didnt buy the susbcription then the customer status will be inactive
     */
    @Scheduled(cron = "30 18 11 * * ?")
    public void myScheduledTask() {
        String res = customerServiceImplementation.makeCustomerStatusInActive();
        System.out.println(res);
    };
    
  

    @PostMapping("/addCust")
    public CustomerDto addcust(@Valid @RequestBody CustomerDto customerDto) {

        return customerServiceImplementation.addCustomer(customerDto);
    }

//*********************************  Login Check ******************************************************************************

    @PostMapping("/addCustomerwithexpirydate")
    public Customer addcustWithExpiryDate(@RequestBody CustomerDto customerDto) {

        return customerService.addCustomerWithExpiryDate(customerDto, addedDays);
    }

// ************************************  Get all Accounts By Customer *******************************************************

    @GetMapping("/getall")
    public List<CustomerDto> getall() {

        return customerServiceImplementation.getAllCustomer();
    }
    
    @GetMapping("/getAllCustomer")
    public List<Customer> getAllCustomerDirect(){
    	return customerService.getAllCustomerDirect();
    }

    @GetMapping("/getAlltransaction/{cus}")
    public List<Transaction> allTransaction(@PathVariable int cus){
    	return customerService.transaction(cus);
    }
    
// ************************************************* Get All Transaction Done By Customer **************************************

    @GetMapping("/getByLastName/{lname}")
    public ResponseEntity<String> getbyLastName(@PathVariable String lname) {
        return new ResponseEntity<String>(customerServiceImplementation.findByLastName(lname), HttpStatus.OK);
    }

    ;


    @GetMapping("/login/{email}/{pass}")
    public CustomerDto login(@PathVariable String email, @PathVariable String pass) {

        return customerServiceImplementation.findByEmail(email, pass);
    }

    ;

// *******************************************  Create Account Table *****************************************************

    @GetMapping("/getAccountByCustomer/{cid}")
    public List<AccountDto> getAllAccountByCustomer(@PathVariable int cid) {
        return accountService.findByCustomer(cid);
    }

    ;

    @GetMapping("/allTransactionByCustomer/{cid}")
    public List<TransactionDto> allTrans(@PathVariable int cid) {
        return accountService.allTranByCustomer(cid);
    }

    @GetMapping("/getAllAccBy/{cid}")
    public List<Integer> allAccountOfCustomer(@PathVariable int cid) {
        return accountService.allAcountsOfCust(cid);
    }

    ;

    @PostMapping("/addAccount/{id}")
    public AccountDto addAcc(@PathVariable int id, @RequestBody AccountDto addt) {

        return accountService.createAccount(addt, id);

    }

    ;

    @GetMapping("/getAllAccount")
    public List<AccountDto> getAllAccount() {
        return accountService.showAllAccounts();
    }

// *****************************  Fund Transfer ********************************************

    @PostMapping("/DepositeMoney/{accno}/{money}/{mess}")
    public AccountDto addMoney(@PathVariable int accno, @PathVariable int money, @PathVariable String mess) {
        return accountService.depositeMoney(accno, money, mess);
    }

    ;

// *************************  Account Repo queries *************************************************

    @PostMapping("/WidhrawMoney/{accno}/{money}/{mess}")
    public String widhrawMoney(@PathVariable int accno, @PathVariable int money, @PathVariable String mess) {
        return accountService.widhrawMoney(accno, money, mess);
    }

    ;

    @PostMapping("/FundTransfer/{ac1}/{ac2}/{amount}/{mess}")
    public String fundTransfer(@PathVariable int ac1, @PathVariable int ac2, @PathVariable int amount, @PathVariable String mess) {
        return accountService.fundTransfer(ac1, ac2, amount, mess);
    }

    @GetMapping("/lessThanOpBal")
    public List<AccountDto> opbalLessThan(@RequestParam double ob) {
        return accountService.findByOPlessthan(ob);
    }

    @GetMapping("/greaterThanOpBal")
    public List<AccountDto> oplessThan(@RequestParam double ob) {
        return accountService.opGreater(ob);
    }

    @GetMapping("/DistinctOpBal")
    public List<AccountDto> DistinctOp(@RequestParam double ob) {
        return accountService.distinctOp(ob);
    }

    @GetMapping("/AscOpBal")
    public List<AccountDto> AscOpBal() {
        return accountService.ascOpBal();
    }

// *************************************************  Customer Repo **************************************

    @GetMapping("/DescOpBal")
    public List<AccountDto> DescOpBal() {
        return accountService.descOpBal();
    }

    @GetMapping("/byDate/{st}/{ed}")
    public List<AccountDto> bydatefind(@PathVariable LocalDate st, @PathVariable LocalDate ed) {
        return accountService.byDate(st, ed);
    }

// *******************************************  Address Repo******************************************************

    @GetMapping("/getByFirstName/{fnm}")
    public List<CustomerDto> firstName(@PathVariable String fnm) {
        return customerServiceImplementation.firstNameConatining(fnm);
    }

    @GetMapping("/getByFirstNameIgnoreCase/{fnm}")
    public List<CustomerDto> firstNameIgnoreCase(@PathVariable String fnm) {
        return customerServiceImplementation.firstNameIgnoreCase(fnm);
    }

    @GetMapping("/NullAddress")
    public List<Address> nullAdd() {
        return addressService.addLine2IsNUll();
    }

// ********************************************************  Transaction Service ***********************************

    @GetMapping("/NotNullAddress")
    public List<Address> notNullAdd() {
        return addressService.addLine2IsNotNull();
    }

    @GetMapping("/getAllTransc")
    public List<Transaction> allTra() {
        return transactionService.allTran();
    }

    @GetMapping("/getAllTranscByPageNumber/{pNumber}/{pSize}")
    public ResponseEntity<PageResponse> allTranByPageNumber(@PathVariable int pNumber, @PathVariable int pSize) {
        return new ResponseEntity<PageResponse>(transactionService.paginationUsingNumberAndSize(pNumber, pSize), HttpStatus.OK);
    }

    @GetMapping("/getAllTransactionByFromAccount/{fromAcc}")
    public List<TransactionDtoImpl> getAllByFromAccountCustom(@PathVariable int fromAcc) {
        return transactionService.findAllByfromAccount(fromAcc);
    }

}
