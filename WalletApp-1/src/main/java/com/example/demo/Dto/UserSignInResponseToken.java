package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class UserSignInResponseToken {

	
	private String token;
	
	private int customerId;

	
}
