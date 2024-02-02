package com.example.demo.customAnotations.Implementations;

import com.example.demo.Model.Customer;
import com.example.demo.Repo.CustomerRepo;
import com.example.demo.customeAnotation.ValidCustomerId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class ValidCustomerIdValidator implements ConstraintValidator<ValidCustomerId, Integer>  {
	private final CustomerRepo customerRepo;
	
	@Override
	public boolean isValid(Integer cus, ConstraintValidatorContext context) {
		Customer customer = customerRepo.findByCustomerId(cus);

		return customer !=null;
	}
}
