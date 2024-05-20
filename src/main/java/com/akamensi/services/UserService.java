package com.akamensi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akamensi.entities.User;
import com.akamensi.entities.dto.UserDto;
import com.akamensi.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
    
    public UserDto getOneUser(long id) {
     Optional<User> u = this.userRepository.findById(id);
     if(u.isPresent()) {
    	 return UserDto.toDto(u.get());
     }else {
    	 return null;
     }
     }
    
    
    
    }


