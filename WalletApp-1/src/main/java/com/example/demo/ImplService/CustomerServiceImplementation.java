package com.example.demo.ImplService;

import com.example.demo.Constant.ConstantFile;
import com.example.demo.Dto.CustomerDataRowGrids;
import com.example.demo.Dto.CustomerDto;
import com.example.demo.Dto.CustomerRequestDto;
import com.example.demo.Dto.PaginationResponse;
import com.example.demo.Dto.PaginationReuestDto;
import com.example.demo.EnumData.CustomerType;
import com.example.demo.ExcelHelperPackage.CustomerExcelDownloadHelper;
import com.example.demo.ExcelHelperPackage.GettingAllDataOfCustomerFromExcel;
import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Repo.AddressRepo;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    AddressRepo addressRepo;

    @Autowired
    ModelMapper modelMapper;

    @Value("${addedDays}")
    private int addedDays;

    @Override
    public CustomerDto addCustomer(CustomerDto cd) {
        log.info("addCustomer called");
        Customer c1 = this.DtoToCustomer(cd);

        Address a11 = this.DtoToAddress(cd);

        addressRepo.save(a11);

        c1.setAddress(a11);

        customerRepo.save(c1);

        CustomerDto cdfinal = this.customerToDto(c1);

        return cdfinal;
    }

    @Override
    public List<CustomerDto> getAllCustomer() {

        List<Customer> cc = customerRepo.findAll();
        // List<CustomerDto> lst1 = lst.stream().map(user ->
        // this.customertoDto(user)).collect(Collectors.toList());

        List<CustomerDto> cdd = cc.stream().map(user -> this.customerToDto(user)).collect(Collectors.toList());

        return cdd;
    }

    private Customer DtoToCustomer(CustomerDto cd) {
        Customer cc = new Customer();
        cc.setFirstName(cd.getFirstName());
        cc.setLastName(cd.getLastName());
        cc.setEmailId(cd.getEmailId());
        cc.setContactNo(cd.getContactNo());
        cc.setGender(cd.getGender());
        cc.setPassword(cd.getPassword());
        cc.setRegistrationDate(cd.getRegistrationDate());

        return cc;

    }

    private CustomerDto customerToDto(Customer cc) {
        CustomerDto cd = new CustomerDto();
        cd.setCustomerId(cc.getCustomerId());
        cd.setFirstName(cc.getFirstName());
        cd.setLastName(cc.getLastName());
        cd.setEmailId(cc.getEmailId());
        cd.setContactNo(cc.getContactNo());
        cd.setGender(cc.getGender());
        cd.setPassword(cc.getPassword());
        cd.setRegistrationDate(cc.getRegistrationDate());
        cd.setAddressLine1(cc.getAddress().getAddressLine1());
        cd.setAdressLine2(cc.getAddress().getAdressLine2());
        cd.setCity(cc.getAddress().getCity());
        cd.setState(cc.getAddress().getState());
        cd.setPincode(cc.getAddress().getPincode());

        return cd;
    }

    private Address DtoToAddress(CustomerDto cd) {
        Address ad = new Address();
        ad.setAddressLine1(cd.getAddressLine1());
        ad.setAdressLine2(cd.getAdressLine2());
        ad.setCity(cd.getCity());
        ad.setState(cd.getState());
        ad.setPincode(cd.getPincode());

        return ad;

    }

    @SuppressWarnings("unused")
    private CustomerDto addressToDto(Address sAd) {
        CustomerDto cd = new CustomerDto();
        cd.setAddressLine1(sAd.getAddressLine1());
        cd.setAdressLine2(sAd.getAdressLine2());
        cd.setCity(sAd.getCity());
        cd.setState(sAd.getState());
        cd.setPincode(sAd.getPincode());
        return cd;
    }

    @Override
    public CustomerDto findByEmail(String email, String pass) {
        Customer cc1 = customerRepo.findByEmailIdAndPassword(email, pass);

        return this.customerToDto(cc1);
    }

//*******************************************  Repo Query

    @Override
    public List<CustomerDto> firstNameConatining(String fnm) {

        List<Customer> cc = customerRepo.findByFirstNameContaining(fnm);

        List<CustomerDto> cdd = cc.stream().map(user -> this.customerToDto(user)).collect(Collectors.toList());

        return cdd;

    }

    @Override
    public List<CustomerDto> firstNameIgnoreCase(String fnm) {
        List<Customer> cc = customerRepo.findByFirstNameIgnoreCase(fnm);

        List<CustomerDto> cdd = cc.stream().map(user -> this.customerToDto(user)).collect(Collectors.toList());

        return cdd;

    }

    @Override
    public String findByLastName(String lName) {

        return customerRepo.findFirstnamebyLastName(lName);

    }

    @Override
    public Customer addCustomerWithExpiryDate(CustomerDto customer1, int addDays) {
        Customer c1 = this.DtoToCustomer(customer1);

        Address a11 = this.DtoToAddress(customer1);

        addressRepo.save(a11);

        c1.setAddress(a11);
        LocalDate expiryDate = this.addExpiryDate(addDays);

        c1.setExpiryDate(expiryDate);
        c1.setCustomerStatus(ConstantFile.STATUS_ACTIVE);
        c1.setCustomerType(CustomerType.FREE);

        // crepo.save(customer1);

        customerRepo.save(c1);

        return c1;
    }

    ;

    /*
     * helping methods
     */

    private LocalDate addExpiryDate(int daysToAdd) {

        LocalDate addedDate = LocalDate.now().plusDays(daysToAdd);

        return addedDate;
    }

    ;

    public String makeCustomerStatusInActive() {

        List<Customer> lstOfCustomers = customerRepo.findByCustomerTypeAndExpiryDateAndCustomerStatus(CustomerType.FREE,
                LocalDate.now(), ConstantFile.STATUS_ACTIVE);

        try {
            for (Customer cus1 : lstOfCustomers) {
                cus1.setCustomerStatus(ConstantFile.STATUS_INACTIVE);
                customerRepo.save(cus1);
            }
            return ConstantFile.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return ConstantFile.ERROR_MESS;

    }

    ;

    /*
     *
     * Download All Cusotmer Data into excel Format
     *
     */
    @Override
    public ByteArrayInputStream getExcelOfCustomers() {
        return CustomerExcelDownloadHelper.customerDataDownloadInExcel(customerRepo.findAll());
    }

    ;

    @Override
    public ByteArrayInputStream getCustomerHeaderInExcel() {
        return CustomerExcelDownloadHelper.customerHeadersDownloadInExcel();
    };

 

    @Override
    public List<CustomerRequestDto> insertBulkCustomerToDataBase(MultipartFile file) {

        List<CustomerRequestDto> fetchedCustomersFromUserExcel = GettingAllDataOfCustomerFromExcel.getTheDataOfCustomerFromExcel(file);

        List<CustomerRequestDto> checkCustomerGmailWithInExcel = this.checkEmailExistingTwiceFromExcel(fetchedCustomersFromUserExcel);

        List<CustomerRequestDto> finalCustomersToAddToDb = this
                .checkEmailFromDbAndCompareWithExcelData(checkCustomerGmailWithInExcel);

        List<CustomerRequestDto> invalidCustomers = new ArrayList<>(fetchedCustomersFromUserExcel);

        invalidCustomers.removeAll(finalCustomersToAddToDb);

        List<Customer> bulkCustomerToSave = finalCustomersToAddToDb.stream().map(user -> this.customerRequestDtoToCustomer(user))
                .collect(Collectors.toList());

        customerRepo.saveAll(bulkCustomerToSave);

        return invalidCustomers;

    };

   

    private List<CustomerRequestDto> checkEmailExistingTwiceFromExcel(List<CustomerRequestDto> customerReDtos) {
        Map<String, Integer> emailCountMap = new HashMap<>();
        List<CustomerRequestDto> finalListToAdd = new ArrayList<>();

        List<CustomerRequestDto> finalListToSendBackToCustomer = new ArrayList<>();

        for (CustomerRequestDto dto : customerReDtos) {
            String emailId = dto.getEmailId();

            if (emailCountMap.containsKey(emailId)) {

                int count = emailCountMap.get(emailId);
                emailCountMap.put(emailId, count + 1);

                if (count == 1) {
                    finalListToSendBackToCustomer.add(dto);
                }
            } else {

                emailCountMap.put(emailId, 1);
                finalListToAdd.add(dto);
            }
        }

        return finalListToAdd;
    }

    private List<CustomerRequestDto> checkEmailFromDbAndCompareWithExcelData(
            List<CustomerRequestDto> checkedCustomerGmailWithInExcel) {
        List<Customer> existingCustomers = customerRepo.findAll();

        List<String> existingEmails = existingCustomers.stream().map(Customer::getEmailId).collect(Collectors.toList());

        System.out.println(existingEmails);

        List<CustomerRequestDto> finalCustomers = checkedCustomerGmailWithInExcel.stream()
                .filter(dto -> !existingEmails.contains(dto.getEmailId())).collect(Collectors.toList());

        return finalCustomers;

    }

    private Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer();
        Address address = new Address();
        address.setAddressLine1(customerRequestDto.getAddressLine1());
        address.setAdressLine2(customerRequestDto.getAdressLine2());
        address.setCity(customerRequestDto.getCity());
        address.setPincode(customerRequestDto.getPincode());
        address.setState(customerRequestDto.getState());

        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
        customer.setPassword(customerRequestDto.getPassword());
        customer.setContactNo(customerRequestDto.getContactNo());
        customer.setEmailId(customerRequestDto.getEmailId());
        customer.setRegistrationDate(customerRequestDto.getRegistrationDate());
        customer.setExpiryDate(customerRequestDto.getRegistrationDate().plusDays(addedDays));
        customer.setCustomerType(CustomerType.FREE);
        customer.setGender(customerRequestDto.getGender());
        customer.setCustomerStatus(ConstantFile.STATUS_ACTIVE);
        customer.setAddress(address);

        return customer;

    }
    
    // Pagination and Sorting and Searching for Customers data which is shwoing to Admin Page.....
     @Override
    public PaginationResponse paginationAndSortingOfCustomer(PaginationReuestDto paginationReuestDto){
    	 Sort sorting = paginationReuestDto.getSortedType().equalsIgnoreCase("asc")? Sort.by(paginationReuestDto.getSortByFieldName()).ascending() : Sort.by(paginationReuestDto.getSortByFieldName()).descending();
    	 Pageable pageable = PageRequest.of(paginationReuestDto.getOffSet(), paginationReuestDto.getLimit(), sorting);
    	 Page<Customer> pageResult;
    	 if(paginationReuestDto.getSearchByFieldName()!=null) {
    		 pageResult = this.customerRepo.findByFirstNameIgnoreCaseContaining(paginationReuestDto.getSearchByFieldName(), pageable);
    	    } else {
    	        pageResult = this.customerRepo.findAll(pageable);
    	    }
    	    List<CustomerDataRowGrids> dataList = pageResult.getContent().stream()
    	            .map(this::paginationDataConversion)
    	            .collect(Collectors.toList());
     	return new PaginationResponse(dataList, pageResult.getTotalElements(), pageResult.getTotalPages());
        
     	 
      };   	 
    	 
    			private CustomerDataRowGrids paginationDataConversion(Customer customer) {
			
			CustomerDataRowGrids customerDataRowGrids = new CustomerDataRowGrids();
			
			customerDataRowGrids.setCustomerId(customer.getCustomerId());
			customerDataRowGrids.setFirstName(customer.getFirstName());
			customerDataRowGrids.setLastName(customer.getLastName());
			customerDataRowGrids.setGender(customer.getGender());
			customerDataRowGrids.setEmailId(customer.getEmailId());
			customerDataRowGrids.setRegistrationDate(customer.getRegistrationDate());
			customerDataRowGrids.setAddressLine1(customer.getAddress().getAddressLine1());
			customerDataRowGrids.setAdressLine2(customer.getAddress().getAdressLine2());
			customerDataRowGrids.setCity(customer.getAddress().getCity());
			customerDataRowGrids.setContactNo(customer.getContactNo());
			customerDataRowGrids.setState(customer.getAddress().getState());
			customerDataRowGrids.setPincode(customer.getAddress().getPincode());
			
			return customerDataRowGrids;
		};
		
		
		
		
		
		


}
