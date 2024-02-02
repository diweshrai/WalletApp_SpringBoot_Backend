package com.example.demo.Service;


import com.example.demo.Dto.CustomerDataRowGrids;
import com.example.demo.Dto.CustomerDto;
import com.example.demo.Dto.CustomerRequestDto;
import com.example.demo.Dto.PaginationResponse;
import com.example.demo.Dto.PaginationReuestDto;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Transaction;
import com.itextpdf.text.DocumentException;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    public CustomerDto addCustomer(CustomerDto cd);
    public List<CustomerDto> getAllCustomer();
    public CustomerDto findByEmail(String email, String pass);
    public List<CustomerDto> firstNameConatining(String fnm);
    public List<CustomerDto> firstNameIgnoreCase(String fnm);
    public String findByLastName(String lName);
    public Customer addCustomerWithExpiryDate(CustomerDto customer1, int addDays);
    public ByteArrayInputStream getExcelOfCustomers();
    public ByteArrayInputStream getCustomerHeaderInExcel();
    public List<CustomerRequestDto> insertBulkCustomerToDataBase(MultipartFile file);
    public PaginationResponse paginationAndSortingOfCustomer(PaginationReuestDto paginationReuestDto);
    public String deleteCustomerByCustomerID(int customerId);
    public String updateCustomerStatusActive( int customerId);
    public String editCustomerDetails(CustomerDataRowGrids customerDataRowGrids);
    public List<Customer> getAllCustomerDirect();
    public ByteArrayInputStream generatePdfForCustomerByCustomerId(int customerId) throws DocumentException;
    public List<Transaction> transaction(int cus);
    public Optional<Customer> findByEmail(String emailId);
    
     };
