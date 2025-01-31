package com.example.postgresqllearn.dto;

import com.example.postgresqllearn.entity.Employee;
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
public class MeetingDto {


    private String description;
    private LocalDate date;
    private LocalTime time;
    private String publicId;
    private String organizerId;
    private List<Employee> attendees ;




}
