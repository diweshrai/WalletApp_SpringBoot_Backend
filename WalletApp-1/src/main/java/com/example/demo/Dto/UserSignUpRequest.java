package com.example.demo.Dto;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpRequest {

	
	private String firstName;
    private String lastName;
    private String emailId;
    private int contactNo;
    private String Gender;
    private String password;

    private String addressLine1;
    private String adressLine2;
    private String city;
    private String state;
    private int pincode; 	
}
