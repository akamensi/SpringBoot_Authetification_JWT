package com.akamensi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.akamensi.jwt.AuthEntryPointJwt;
import com.akamensi.jwt.AuthTokenFilter;
import com.akamensi.services.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  {
//class is responsible for configuring Spring Security settings for the application	
	 
	 private UserDetailsServiceImpl userDetailsService;
 
	  private AuthEntryPointJwt unauthorizedHandler;
	  
	  

	public SecurityConfiguration(UserDetailsServiceImpl userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
		super();
		this.userDetailsService = userDetailsService;
		this.unauthorizedHandler = unauthorizedHandler;
	}
	
	  @Bean
	  AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	  }
	  
	  @Bean
	   DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }

	  @Bean
	   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> 
	          auth.requestMatchers("/api/auth/**").permitAll()
	              .anyRequest().authenticated());
	    
	    http.authenticationProvider(authenticationProvider());

	    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	    
	    return http.build();
	  }
	
	  @Bean  // pour le AuthService pour manager les authentifaction
	   AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	
	  @Bean //pour le AuthService pour cryte le password
	   PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	
	 @Bean// pour refreche le dossier uploads au lancement de projet
	     WebSecurityCustomizer webSecurityCustomizer() throws Exception {
	        return (web) -> web.ignoring().requestMatchers("/uploads/**");
	    }
	
	 /*
	  other requestMatchers for HTTP methods, Suppose we have the following endpoints in our application:

      /api/public - Public endpoint accessible to all users.
     /api/admin - Admin-only endpoint.
     /api/data - Endpoint to access sensitive data. :
	  
	  (auth ->
    auth.authorizeRequests(requests ->
        requests
            // Public endpoint accessible to all users
            .antMatchers(HttpMethod.GET, "/api/public").permitAll()
            
            // Admin-only endpoint restricted to admin users
            .antMatchers(HttpMethod.GET, "/api/admin").hasRole("ADMIN")
            
            // Endpoint to access sensitive data restricted to authenticated users
            .antMatchers(HttpMethod.POST, "/api/data").authenticated()
            .antMatchers(HttpMethod.PUT, "/api/data").authenticated()
            .antMatchers(HttpMethod.DELETE, "/api/data").authenticated()
            
            // All other requests require authentication
            .anyRequest().authenticated()
    )
);

	  */
	
	
}
