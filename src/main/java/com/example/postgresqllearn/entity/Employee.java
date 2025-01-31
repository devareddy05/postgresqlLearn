package com.example.postgresqllearn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee") //change // relations
public class Employee extends BaseEntity {

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="salary")
    private Double salary;

    @Column(name="email_id",nullable = false,unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "department_public_id", nullable = false)
    private Department department;


    @ManyToMany(mappedBy = "attendees")
    private List<Meeting> meetings = new ArrayList<>();
}
