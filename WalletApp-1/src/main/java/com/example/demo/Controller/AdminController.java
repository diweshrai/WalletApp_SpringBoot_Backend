package com.example.demo.Controller;

import com.example.demo.Dto.CustomerDataRowGrids;
import com.example.demo.Dto.CustomerRequestDto;
import com.example.demo.Dto.PaginationResponse;
import com.example.demo.Dto.PaginationReuestDto;
import com.example.demo.ImplService.CustomerServiceImplementation;
import com.example.demo.Model.AdminTable;
import com.example.demo.Service.AccountService;
import com.example.demo.Service.AddressService;
import com.example.demo.Service.AdminService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.TransactionService;
import com.itextpdf.text.DocumentException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
@RequestMapping("/admin/api")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;
    
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
    
    

    @GetMapping("/downloadCustomerDetailInPdfByCustomerId/{customerId}")
    public ResponseEntity<InputStreamResource> downloadPdfOfCustomerByCustomerId(@PathVariable int customerId) throws DocumentException {
        ByteArrayInputStream byteArrayInputStream = customerService.generatePdfForCustomerByCustomerId(customerId);

        
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customer_details.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    };
    
    
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable int customerId){
    	 
    	return new ResponseEntity<String>(customerService.deleteCustomerByCustomerID(customerId) , HttpStatus.OK);
    };
    
    @PostMapping("/activateCustomerStatusActive/{customerId}")
    public ResponseEntity<String> activateCustomerStatusActive(@PathVariable int customerId){
    	 
    	return new ResponseEntity<String>(customerService.updateCustomerStatusActive(customerId) , HttpStatus.OK);
    } 
    
    
    /*
     * Edit Customer Details By Admin
     */
    @PostMapping("/editCustomerDetailsByAdmin")
    public ResponseEntity<String> editCustomerDetailsByAdmin(@RequestBody CustomerDataRowGrids customerDataRowGrids){
    	return new ResponseEntity<String>(customerService.editCustomerDetails(customerDataRowGrids), HttpStatus.OK);
    }
    
    
    /*
     * Pagination And Sorting And Searching for Customers
     */
    @PostMapping("/pagination")
    public PaginationResponse getAllCustomerUsingPagination(@RequestBody PaginationReuestDto paginationReuestDto){
    	log.info("Calling method get all customer using pagination with request {}",paginationReuestDto);
    	return customerService.paginationAndSortingOfCustomer(paginationReuestDto);
    };
    
   /*
     * Download all Customers in excel Format
     */
    @RequestMapping("/downloadAllCustomersInExcel")
    public ResponseEntity<Resource> download() {
        String filename = "allCustomers.xlsx";
        InputStreamResource file = new InputStreamResource(customerService.getExcelOfCustomers());
        return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    }

    @RequestMapping("/downloadHeadCustomersInExcel")
    public ResponseEntity<Resource> downloadHeadersOfCustomer() {
        String filename = "CustomerHeader.xlsx";
        InputStreamResource file = new InputStreamResource(customerService.getCustomerHeaderInExcel());
        return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
    };

    

    @PostMapping("/uploadBulkCustomers")
    public ResponseEntity<?> uploadingBulkCustomers(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx"))) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only .xls and .xlsx file formats are accepted");
        }
        List<CustomerRequestDto> customers = customerService.insertBulkCustomerToDataBase(file);
        if (customers.isEmpty()) {
            return ResponseEntity.ok("All records are good and uploaded successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customers);
        }
    };
    
    
    
    
    
    
    
    
    

    @PostMapping("/addAdminToDataBase")
    public ResponseEntity<AdminTable> addAdminData(@RequestBody AdminTable adminTable) {

        AdminTable adminTable1 = adminService.addAdminToDatabase(adminTable);
        if (adminTable1 != null) {
            return ResponseEntity.ok(adminTable1);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    ;

    @GetMapping("/getAllAdminData/{adminName}/{adminPassword}")
    public ResponseEntity<List<AdminTable>> getAllAdminData(@PathVariable String adminName,
                                                            @PathVariable String adminPassword) {

        List<AdminTable> adminTableList = adminService.getAllAdminByAdminNameAndAdminPassword(adminName, adminPassword);

        if (adminTableList != null) {
            return ResponseEntity.ok(adminTableList);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

};
