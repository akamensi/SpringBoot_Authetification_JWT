package com.akamensi.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;


import com.akamensi.entities.User;
import com.akamensi.entities.dto.UserDto;
import com.akamensi.services.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserRestController {
	
	private UserService userService;

	public UserRestController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/list")
	@ResponseBody
	public List<User> getAllUsers() {
		return (List<User>) userService.getAllUsers();
	}
	
	
	@GetMapping("/{userId}")
	@ResponseBody
	public UserDto getOneUser(@PathVariable Long userId) {
		return userService.getOneUser(userId);
	}

}
