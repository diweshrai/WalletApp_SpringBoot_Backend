package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(initialValue = 2000, name = "adminSeq", sequenceName = "adminSeq")
public class AdminTable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeq")
    private int adminId;

    private String adminName;
    private String adminPassword;
}
