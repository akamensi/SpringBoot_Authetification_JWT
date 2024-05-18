package com.akamensi.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.akamensi.entities.ERole;
import com.akamensi.entities.Role;
import com.akamensi.entities.User;
import com.akamensi.jwt.JwtUtils;
import com.akamensi.models.request.LoginRequest;
import com.akamensi.models.request.SignupRequest;
import com.akamensi.models.response.JwtResponse;
import com.akamensi.models.response.MessageResponse;
import com.akamensi.repositories.RoleRepository;
import com.akamensi.repositories.UserRepository;

@Service
public class AuthService {

	private AuthenticationManager authenticationManager;

	private UserRepository userRepository;

	private RoleRepository roleRepository;
	  
	private PasswordEncoder encoder;
	
	private JwtUtils jwtUtils;

	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder encoder,
			JwtUtils jwtUtils) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtUtils = jwtUtils;
	}
	
	
	  public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
		  
		  //verification d'exitance
		    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
		      return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Username is already taken!"));
		    }

		    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
		      return ResponseEntity
		          .badRequest()
		          .body(new MessageResponse("Error: Email is already in use!"));
		    }

		    // Create new user's account
		    User user = new User(signUpRequest.getUsername(), 
		               signUpRequest.getEmail(),
		               encoder.encode(signUpRequest.getPassword()));

		    Set<String> strRoles = signUpRequest.getRole();  // Ce qu'on récupère du Request
		    
		    Set<Role> roles = new HashSet<>(); // ce qu'on doit créer et sauvegarder dans la base pour chaque User

		    if (strRoles == null) {
		      Role userRole = roleRepository.findByName(ERole.USER)
		          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		      roles.add(userRole);
		    } else {
		      strRoles.forEach(role -> {
		        switch (role) {
		        case "admin":
		          Role adminRole = roleRepository.findByName(ERole.ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(adminRole);

		          break;
		        case "superadmin":
		          Role superAdminRole = roleRepository.findByName(ERole.SUPER_ADMIN)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(superAdminRole);

		          break;
		        default:
		          Role userRole = roleRepository.findByName(ERole.USER)
		              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		          roles.add(userRole);
		        }
		      });
		    }

		    user.setRoles(roles);
		    userRepository.save(user);

		    return ResponseEntity.ok(new MessageResponse("Inscription avec succès!"));
		  }
	  
	  
	  public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {

		  //1-get Authentication
			
			  Authentication authentication = authenticationManager.authenticate( new
			  UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
			 

	     //2-get token
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtUtils.generateJwtToken(authentication);
	    
	    //3-get User details
	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	    
	     //4-get roles
	    List<String> roles = userDetails.getAuthorities().stream()
	        .map(item -> item.getAuthority())
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(new JwtResponse(jwt, 
	                         userDetails.getId(), 
	                         userDetails.getUsername(), 
	                         userDetails.getEmail(), 
	                         roles));
	  }
	
	
	  
	  

}
