package com.akamensi.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.akamensi.services.UserDetailsServiceImpl;



public class AuthTokenFilter extends OncePerRequestFilter { //class is a custom filter that extends OncePerRequestFilter to process JWT authentication for each HTTP request. 
 
	  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	  private static final String AUTHORIZATION_HEADER = "Authorization";
	  private static final String BEARER_PREFIX = "Bearer ";
	  
	  
  private JwtUtils jwtUtils;

  private UserDetailsServiceImpl userDetailsService;
  
  

  @Autowired
  public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
	super();
	this.jwtUtils = jwtUtils;
	this.userDetailsService = userDetailsService;
}
  
  


  public AuthTokenFilter() {
	super();
}




@Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = parseJwt(request); //method extracts the JWT token from the Authorization header.
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        String username = jwtUtils.getUserNameFromJwtToken(jwt); //If the token is valid, the username is extracted.

        UserDetails userDetails = userDetailsService.loadUserByUsername(username); //The user details are loaded
        UsernamePasswordAuthenticationToken authentication =
        		new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);  // is created and set in the SecurityContextHolder.
      }
    } catch (Exception e) {
        logger.error("Cannot set user authentication: {}", e.getMessage());

    }

    filterChain.doFilter(request, response);
  }
  
  

  private String parseJwt(HttpServletRequest request) {  //Extracts the JWT token from the Authorization header if it starts with "Bearer"
    String headerAuth = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER_PREFIX)) {
        return headerAuth.substring(7);
    }

    return null;
  }

}
