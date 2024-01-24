package com.example.demo.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.demo.EnumData.Role;
import com.itextpdf.text.List;

import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Collections;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

	@Autowired
    JwtAuthenticationFilter jwtAuthFilter;
   
	@Autowired
	AuthenticationProvider authenticationProvider;
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      return  http
    		  .cors().and()
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                req.requestMatchers("/api/auth/tokenGen")
                                .permitAll()
                                .requestMatchers("/admin/api/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers( "/users/**" , "/users/images/**").hasAnyAuthority(Role.USER.name())
                                .anyRequest()
                                .authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    } }