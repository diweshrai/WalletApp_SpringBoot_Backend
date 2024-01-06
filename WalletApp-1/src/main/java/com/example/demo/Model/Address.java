package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(initialValue = 501, name = "addg", sequenceName = "addg")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addg")
    private int addressid;

    private String addressLine1;

    private String adressLine2;

    private String city;

    private String state;

    private int pincode;

}
