package com.akamensi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akamensi.models.request.LoginRequest;
import com.akamensi.models.request.SignupRequest;
import com.akamensi.services.AuthService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private AuthService authService;
	
	  public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}


	@PostMapping("/signup")
	  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	  {
		  return authService.registerUser(signUpRequest);
	  }
	
	
	  @PostMapping("/signin")
	  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	  {
		  return authService.authenticateUser(loginRequest);
	  }

}
