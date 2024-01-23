package com.example.demo.ImplService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Config.JwtService;
import com.example.demo.Constant.ConstantFile;
import com.example.demo.Dto.UserSignIn;
import com.example.demo.Dto.UserSignInResponseToken;
import com.example.demo.Dto.UserSignUpRequest;
import com.example.demo.EnumData.CustomerType;
import com.example.demo.EnumData.Role;
import com.example.demo.Model.Address;
import com.example.demo.Model.Customer;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Service.AuthenticationService;


@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager; // Autowire the AuthenticationManager

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    
    @Value("${addedDays}")
	private int addedDays;

    @Override
    public UserSignInResponseToken signIn(UserSignIn userSignIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userSignIn.getEmailId(), userSignIn.getPassword()));

        var customer = customerRepo.findByEmailId(userSignIn.getEmailId())
                .orElseThrow(() -> new IllegalArgumentException("invalid email"));

        var jwt = jwtService.generateToken(customer);
       // String refreshToken = "hello";

        UserSignInResponseToken signInResponseToken = new UserSignInResponseToken();
        signInResponseToken.setToken(jwt);
        //signInResponseToken.setRefreshToken(refreshToken);
        signInResponseToken.setCustomerId(customer.getCustomerId());
        return signInResponseToken;
    }

	@Override
	public String signUp(UserSignUpRequest userSignUpRequest) {
		try {
			customerRepo.save(this.userSignUpReuestToCustomer(userSignUpRequest));
			return "customer added succesfully";
		} catch (Exception e) {
			return "something went wrong";
		}
	}
	
	
	private Customer userSignUpReuestToCustomer(UserSignUpRequest userSignUpRequest) {
		Customer customer = new Customer();
		Address address = new Address();
		address.setAddressLine1(userSignUpRequest.getAddressLine1());
		address.setAdressLine2(userSignUpRequest.getAdressLine2());
		address.setCity(userSignUpRequest.getCity());
		address.setPincode(userSignUpRequest.getPincode());
		address.setState(userSignUpRequest.getState());
		
		customer.setFirstName(userSignUpRequest.getFirstName());
		customer.setLastName(userSignUpRequest.getLastName());
		customer.setEmailId(userSignUpRequest.getEmailId());
		customer.setGender(userSignUpRequest.getGender());
		customer.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		customer.setContactNo(userSignUpRequest.getContactNo());
		customer.setRegistrationDate(LocalDate.now());
		customer.setExpiryDate(LocalDate.now().plusDays(addedDays));
		customer.setCustomerType(CustomerType.FREE);
		customer.setCustomerStatus(ConstantFile.STATUS_ACTIVE);
		customer.setRole(Role.USER);
		customer.setAddress(address);
		
		return customer;
	}

	@Override
	public String signUpAdmin(UserSignUpRequest userSignUpRequest) {
		try {
			customerRepo.save(this.userSignUpReuestToAdmin(userSignUpRequest));
			return "Admin added succesfully";
		} catch (Exception e) {
			return "something went wrong";
		}
	}
	
	
	
	private Customer userSignUpReuestToAdmin(UserSignUpRequest userSignUpRequest) {
		Customer customer = new Customer();
		Address address = new Address();
		address.setAddressLine1(userSignUpRequest.getAddressLine1());
		address.setAdressLine2(userSignUpRequest.getAdressLine2());
		address.setCity(userSignUpRequest.getCity());
		address.setPincode(userSignUpRequest.getPincode());
		address.setState(userSignUpRequest.getState());
		
		customer.setFirstName(userSignUpRequest.getFirstName());
		customer.setLastName(userSignUpRequest.getLastName());
		customer.setEmailId(userSignUpRequest.getEmailId());
		customer.setGender(userSignUpRequest.getGender());
		customer.setPassword(passwordEncoder.encode(userSignUpRequest.getPassword()));
		customer.setContactNo(userSignUpRequest.getContactNo());
		customer.setRegistrationDate(LocalDate.now());
		//customer.setExpiryDate(LocalDate.now().plusDays(addedDays));
		//customer.setCustomerType(CustomerType.PREMIUM);
		customer.setCustomerStatus(ConstantFile.STATUS_ACTIVE);
		customer.setRole(Role.ADMIN);
		customer.setAddress(address);
		
		return customer;
	}
	
	
}
