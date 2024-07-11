package com.javaguide.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaguide.springboot.model.Employee;
import com.javaguide.springboot.repository.EmployeeRepository;
import com.javaguide.springboot.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

      //Junit test for create Employee object
          @Test
          public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
              //given - precondition or setup
              Employee employee = Employee.builder()
                      .firstName("Ramesh")
                      .lastName("Fatdare")
                      .email("ramesh@gmail.com")
                      .build();
              //need to mock the method or stubbing
              BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                      .willAnswer((invocation)-> invocation.getArguments());


              //when - action or the behaviour that we are going test

              //call restapi and mock
             ResultActions response = mockMvc.perform(post("/api/employees")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(employee)));
              //then - verify the output result or Assert statement
              response.andExpect(MockMvcResultMatchers.status().isCreated())
                      .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                              CoreMatchers.is(employee.getFirstName())))
                      .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                              CoreMatchers.is(employee.getLastName())))
                      .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                              CoreMatchers.is(employee.getEmail())));


          }

}
