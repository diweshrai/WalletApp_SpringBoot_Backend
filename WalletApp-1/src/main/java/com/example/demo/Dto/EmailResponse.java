package com.example.demo.Dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmailResponse {

	private String to;
    private String subject;
    private String body;
	
}
