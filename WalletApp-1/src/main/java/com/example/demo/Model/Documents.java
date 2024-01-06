package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
