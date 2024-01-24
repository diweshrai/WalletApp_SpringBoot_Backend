package com.example.demo.Config;


import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.EnumData.Role;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	  UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(
		@NotNull	HttpServletRequest request, 
		@NotNull	HttpServletResponse response, 
		@NotNull	FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().contains("/api/auth/")) {
		      filterChain.doFilter(request, response);
		      log.info("remove");
		      return;
		    }
		    final String authHeader = request.getHeader("Authorization");
		    final String jwt;
		    final String userEmail;
		    
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      filterChain.doFilter(request, response);
		      log.info("removee with out header"); 
		      response.sendError(HttpServletResponse.SC_FORBIDDEN, "Missing or invalid token");
		      return;
		    }
		    jwt = authHeader.substring(7);
		    userEmail = jwtService.extractUsername(jwt);
		    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
		  
		      if (jwtService.isTokenValid(jwt, userDetails) ) {
		        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
		            userDetails,
		            null,
		            userDetails.getAuthorities()
		        		 );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                if (!hasRequiredRole(request, userDetails.getAuthorities())) {
                    log.info("User does not have required role");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorized to use this API");
                    return;
                }
            } else {
                log.info("Invalid token");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
                return;
            }
        } else {
            log.info("Unable to extract user details from token");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid token");
            return;
        }
		    filterChain.doFilter(request, response);
		  }
	 private boolean hasRequiredRole(HttpServletRequest request, Collection<? extends GrantedAuthority> authorities) {
		 String path = request.getServletPath();
		    if (path.startsWith("/admin/api/")) {
		        return authorities.stream().anyMatch(grantedAuthority ->
		                grantedAuthority.getAuthority().equals(Role.ADMIN.name()));
		    } else if (path.startsWith("/users/")) {
		        return authorities.stream().anyMatch(grantedAuthority ->
		                grantedAuthority.getAuthority().equals(Role.USER.name()));
		    }
		    return true;
	    }
}
