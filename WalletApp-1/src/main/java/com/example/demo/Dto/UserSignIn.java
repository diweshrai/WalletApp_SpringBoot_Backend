package com.example.demo.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class UserSignIn {

	private String emailId;
	
	private String password;
	
}
