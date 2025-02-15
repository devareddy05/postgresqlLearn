package com.example.postgresqllearn.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meeting extends BaseEntity {

    private String description;
    private LocalDate date;
    private LocalTime time;

    @ManyToMany
    @JoinTable(
            name = "employee_meeting",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> attendees = new ArrayList<>();


    private String organizerId;

}

