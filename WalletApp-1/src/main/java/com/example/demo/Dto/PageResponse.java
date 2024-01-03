package com.example.demo.Dto;

import java.util.List;

import com.example.demo.Model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse {

	private List<Transaction> content;

	private int PageNumber;
	private int PageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;

}
