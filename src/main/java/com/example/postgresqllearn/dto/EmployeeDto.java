package com.example.postgresqllearn.dto;



import com.example.postgresqllearn.entity.Department;
import com.example.postgresqllearn.entity.Meeting;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;

    @NotNull(message = "Email should not be null")
    private String email;
    private Double salary;

    private Department department;

    private List<Meeting> meetings = new ArrayList<>();


}
