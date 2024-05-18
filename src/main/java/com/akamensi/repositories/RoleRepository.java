package com.akamensi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akamensi.entities.ERole;
import com.akamensi.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	 Optional<Role> findByName(ERole name);
}
