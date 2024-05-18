package com.akamensi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akamensi.entities.AuthenticationBean;

@CrossOrigin(origins = "*")
@RestController
public class BasicAuthRestController {
	@GetMapping(path = "/basicauth")
	public AuthenticationBean basicauth() {
		// verification du user
		return new AuthenticationBean("You are authenticated");
	}
}
