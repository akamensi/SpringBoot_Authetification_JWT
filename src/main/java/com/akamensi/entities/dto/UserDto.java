package com.akamensi.entities.dto;


import com.akamensi.entities.User;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Long id;

	private String username;

	private String email;


	
	public static UserDto toDto(User entity) {
		return  UserDto.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.email(entity.getEmail())
				.build();
	}



	
	
	
	
}
