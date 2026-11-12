package com.luddev.api.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity 
@Table (name = "employees")
public class Employee {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name")
    private String firstname;

    @Column (name = "last_name")
    private String lastname;

    @Column (name = "mail", unique = true, nullable = false)
    private String mail;

    private String position;
    private double salary;
    private String password;
}
