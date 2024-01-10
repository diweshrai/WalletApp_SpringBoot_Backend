package com.example.demo.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {

	private List<CustomerDataRowGrids> data;
    private long totalElements;
    private int totalPages;
	
	
}
