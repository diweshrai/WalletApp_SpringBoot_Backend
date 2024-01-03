package com.example.demo.ImplService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Constant.ConstantFile;
import com.example.demo.Dto.CustomerDto;
import com.example.demo.EnumData.CustomerType;
import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Repo.AddressRepo;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerImpl implements CustomerService {

    @Autowired
    CustomerRepo crepo;

    @Autowired
    AddressRepo arepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CustomerDto addCustomer(CustomerDto cd) {
        log.info("addCustomer called");
        Customer c1 = this.DtoToCustomer(cd);

        Address a11 = this.DtoToAddress(cd);

        arepo.save(a11);

        c1.setAddress(a11);

        crepo.save(c1);

        CustomerDto cdfinal = this.customerToDto(c1);

        return cdfinal;
    }

    @Override
    public List<CustomerDto> getallCustomer() {

        List<Customer> cc = crepo.findAll();
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
    public CustomerDto findbyemail(String email, String pass) {
        Customer cc1 = crepo.findByEmailIdAndPassword(email, pass);

        return this.customerToDto(cc1);
    }


//*******************************************  Repo Query

    @Override
    public List<CustomerDto> firstnameConatining(String fnm) {

        List<Customer> cc = crepo.findByFirstNameContaining(fnm);

        List<CustomerDto> cdd = cc.stream().map(user -> this.customerToDto(user)).collect(Collectors.toList());

        return cdd;

    }

    @Override
    public List<CustomerDto> firstNameIgnoreCase(String fnm) {
        List<Customer> cc = crepo.findByFirstNameIgnoreCase(fnm);

        List<CustomerDto> cdd = cc.stream().map(user -> this.customerToDto(user)).collect(Collectors.toList());

        return cdd;

    }

    @Override
    public String findbyLastName(String lName) {

        return crepo.findFirstnamebyLastName(lName);

    }


    @Override
    public Customer addCustomerWithExpiryDate(CustomerDto customer1, int addDays) {
        Customer c1 = this.DtoToCustomer(customer1);

        Address a11 = this.DtoToAddress(customer1);

        arepo.save(a11);

        c1.setAddress(a11);

        LocalDate expiryDate = this.addExpiryDate(addDays);

        c1.setExpiryDate(expiryDate);
        c1.setCustomerStatus(ConstantFile.STATUS_ACTIVE);
        c1.setCustomerType(CustomerType.FREE);

        //	crepo.save(customer1);

        crepo.save(c1);


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

        List<Customer> lstOfCustomers = crepo.findByCustomerTypeAndExpiryDateAndCustomerStatus(CustomerType.FREE, LocalDate.now(), ConstantFile.STATUS_ACTIVE);

        try {
            for (Customer cus1 : lstOfCustomers) {
                cus1.setCustomerStatus(ConstantFile.STATUS_INACTIVE);
                crepo.save(cus1);
            }
            return ConstantFile.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();

        }

        return ConstantFile.ERROR_MESS;

    }

    ;


}
