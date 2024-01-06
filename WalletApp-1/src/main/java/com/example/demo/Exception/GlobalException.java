package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> exceptionhandle(MethodArgumentNotValidException ex) {
        Map<String, String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->

                {

                    String fieldname = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    resp.put(fieldname, message);

                }

        );

        return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);

    }

//	    @ExceptionHandler(MethodArgumentNotValidException.class)
//	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
//	        // Handle validation errors for MethodArgumentNotValidException
//	        // Construct a response as needed
//	    }
//
//	    @ExceptionHandler(OtherCustomException.class)
//	    public ResponseEntity<String> handleOtherCustomException(OtherCustomException ex) {
//	        // Handle a different exception, if needed
//	        // Construct a response as needed
//	    }
}
