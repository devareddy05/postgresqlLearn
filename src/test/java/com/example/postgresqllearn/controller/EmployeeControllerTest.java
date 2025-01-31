package com.example.postgresqllearn.controller;

import com.example.postgresqllearn.dto.EmployeeDto;
import com.example.postgresqllearn.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployeeControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        employeeDto = new EmployeeDto(1L, "Devendra Reddy", "Pennabadi", "devendra@gmail.com");
    }

    @Test
    void createEmployeeApiTest() throws Exception {

      when(employeeService.createEmployee(ArgumentMatchers.any(EmployeeDto.class))).thenReturn(employeeDto);
        MvcResult mvcResult=mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(employeeDto.getEmail())).andReturn();

        String response=mvcResult.getResponse().getContentAsString();
        System.out.println("Response"+response);



    }



    @Test
    void getEmployeeByIdApiTest() throws Exception {
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeDto);

        MvcResult mockresult=mockMvc.perform(get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeDto.getId()))
                .andExpect(jsonPath("$.email").value(employeeDto.getEmail())).andReturn();
        String response=mockresult.getResponse().getContentAsString();
        System.out.println("Response"+response);

    }

    @Test
    void getAllEmployeesApiTest() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(java.util.Arrays.asList(employeeDto));

        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(employeeDto.getEmail()));
    }



    @Test
    void deleteEmployeeApiTest() throws Exception {
        mockMvc.perform(delete("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee deleted successfully"));
    }

    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
