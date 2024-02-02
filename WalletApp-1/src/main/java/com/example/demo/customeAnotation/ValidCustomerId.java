package com.example.demo.customeAnotation;

import java.lang.annotation.*;
import com.example.demo.customAnotations.Implementations.ValidCustomerIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidCustomerIdValidator.class)
@ReportAsSingleViolation
public @interface ValidCustomerId {
	 String message() default "Invalid customer ID";

	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default { };
}
