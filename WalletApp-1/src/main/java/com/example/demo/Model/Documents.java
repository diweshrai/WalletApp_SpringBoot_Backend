package com.example.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(initialValue = 1200, name = "docno", sequenceName = "docno")
public class Documents {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "docno")
	private int documentId;

	private int customerId;

	private String documentType;

	private String documentPath;

	private String docStatus;

	private LocalDateTime uploadedDate;

	
	
	
}
