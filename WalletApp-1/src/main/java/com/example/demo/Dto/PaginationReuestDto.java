package com.example.demo.Dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationReuestDto {
	
	private String searchByFieldName;
	
	private int offSet;
	
	private int limit;
	private String sortedType; 
	
	private String sortByFieldName;
	
	
}
