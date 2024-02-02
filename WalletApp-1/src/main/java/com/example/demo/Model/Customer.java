package com.example.demo.Model;

import com.example.demo.EnumData.CustomerType;
import com.example.demo.EnumData.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Customer implements Serializable , UserDetails{

    @Id
    @GeneratedValue
    private int customerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private int contactNo;
    private String Gender;
    private String password;
    private LocalDate registrationDate;
    
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private LocalDate expiryDate;
    private String customerStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressfkk")
    @Transient
    private Address address;

    @OneToMany(targetEntity = Account.class, mappedBy = "customer")
    private List<Account> account = new ArrayList<>();

    /*
     * Userdetails methods implemetation
     */
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return emailId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	} 
}
