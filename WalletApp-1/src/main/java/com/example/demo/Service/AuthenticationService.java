package com.example.demo.Service;

import com.example.demo.Dto.UserSignIn;
import com.example.demo.Dto.UserSignInResponseToken;
import com.example.demo.Dto.UserSignUpRequest;

public interface AuthenticationService {

	String signUp(UserSignUpRequest userSignUpRequest);
	
	UserSignInResponseToken signIn(UserSignIn userSignIn);

	String signUpAdmin(UserSignUpRequest userSignUpRequest);
	
	
	
	
}
