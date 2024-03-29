package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;

@SuppressWarnings({"deprecation", "unused"})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDto {

    private int customerId;
    @NotBlank
    @NotEmpty(message = "First name Not Be Empty")
    // @Size(min = 2 , max = 15 , message = "Minimun 2 letter and max 15")
    private String firstName;

    @NotEmpty(message = "Last name Can not be Empty..... Enter Some Valid Value")
    private String lastName;

    @Email(message = "Email should be in proper Format. Ex=abc@gmail.com")
    private String emailId;

    // @Pattern(regexp = "^[0-9]+$", message = "Only numeric digits (0-9) are
    // allowed")
    // @Size(min = 10 , max=10 , message= "Enter 10 Digit Phone number, min 10
    // max10")
    private int contactNo;

    @NotEmpty(message = "enter gender")
    private String Gender;

    @NotEmpty(message = "enter password")
    private String password;

    // @NotEmpty(message="enter registration date")
    private LocalDate registrationDate;

    @NotEmpty(message = "Enter add line 1")
    private String addressLine1;

    @NotEmpty(message = "Enter add line 2")
    private String adressLine2;

    @NotEmpty(message = "Enter City ")
    private String city;

    @NotEmpty(message = "enter state")
    private String state;

    // @Pattern(regexp = "^[0-9]+$", message = "Only numeric digits (0-9) are
    // allowed")
    // @Size(min=6 , max=6 , message ="Enter 6 digits pin code")
    private int pincode;

    public CustomerDto(@NotBlank @NotEmpty(message = "First name Not Be Empty") String firstName,
                       @NotEmpty(message = "Last name Can not be Empty..... Enter Some Valid Value") String lastName,
                       @Email(message = "Email should be in proper Format. Ex=abc@gmail.com") String emailId, int contactNo,
                       @NotEmpty(message = "enter gender") String gender, @NotEmpty(message = "enter password") String password,
                       LocalDate registrationDate, @NotEmpty(message = "Enter add line 1") String addressLine1,
                       @NotEmpty(message = "Enter add line 2") String adressLine2, @NotEmpty(message = "Enter City ") String city,
                       @NotEmpty(message = "enter state") String state, int pincode) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.contactNo = contactNo;
        Gender = gender;
        this.password = password;
        this.registrationDate = registrationDate;
        this.addressLine1 = addressLine1;
        this.adressLine2 = adressLine2;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

}
