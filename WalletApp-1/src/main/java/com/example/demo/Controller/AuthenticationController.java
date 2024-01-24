package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.UserSignIn;
import com.example.demo.Dto.UserSignInResponseToken;
import com.example.demo.Dto.UserSignUpRequest;
import com.example.demo.Service.AuthenticationService;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = "*")
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;

		@PostMapping("tokenGen")
		public ResponseEntity<UserSignInResponseToken> signIn(@RequestBody UserSignIn userSignIn){
			return ResponseEntity.ok(authenticationService.signIn(userSignIn));
		}
	
		@PostMapping("signUpUser")
		public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest){
			return ResponseEntity.ok(authenticationService.signUp(userSignUpRequest));
		}
		
		@PostMapping("signUpAdmin")
		public ResponseEntity<String> signUpAdmin(@RequestBody UserSignUpRequest userSignUpRequest){
			return ResponseEntity.ok(authenticationService.signUpAdmin(userSignUpRequest));
		}
}
