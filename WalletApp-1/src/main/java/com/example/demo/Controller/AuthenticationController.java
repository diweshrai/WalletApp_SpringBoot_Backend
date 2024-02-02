package com.example.demo.Controller;

import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.EmailRequest;
import com.example.demo.Dto.UserSignIn;
import com.example.demo.Dto.UserSignInResponseToken;
import com.example.demo.Dto.UserSignUpRequest;

import com.example.demo.Service.AuthenticationService;
import com.example.demo.Service.QService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	private final QService quartzSechedulingService;

	@PostMapping("tokenGen")
	public ResponseEntity<UserSignInResponseToken> signIn(@RequestBody UserSignIn userSignIn)
			throws SchedulerException {
		UserSignInResponseToken responseToken = authenticationService.signIn(userSignIn);
		
		if (!(responseToken == null)) {
			quartzSechedulingService.scheduleEmailJob(userSignIn.getEmailId());
			return ResponseEntity.ok(responseToken);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new UserSignInResponseToken("User not found or invalid credentials", 0));

	}

	@PostMapping("signUpUser")
	public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest userSignUpRequest) {
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setEmail(userSignUpRequest.getEmailId());

		return ResponseEntity.ok(authenticationService.signUp(userSignUpRequest));
	}

	@PostMapping("signUpAdmin")
	public ResponseEntity<String> signUpAdmin(@RequestBody UserSignUpRequest userSignUpRequest) {
		return ResponseEntity.ok(authenticationService.signUpAdmin(userSignUpRequest));
	}
}
