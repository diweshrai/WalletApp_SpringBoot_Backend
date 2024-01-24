package com.example.demo.ImplService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.CustomerRepo;
import com.example.demo.Service.UserService;

@Service
public class UserServiceImplementation  implements UserService{

	@Autowired 
	CustomerRepo customerRepo;
	
	@Override
	public UserDetailsService userDetailsService() {
		
		
		return new UserDetailsService() {
		
			@Override
			public UserDetails loadUserByUsername(String username) {
				return customerRepo.findByEmailId(username)
						.orElseThrow(()-> new UsernameNotFoundException("user Not found"));
			}
		};
		
	}
	
}
