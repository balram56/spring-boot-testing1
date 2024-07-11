package com.javaguide.springboot.service;

import com.javaguide.springboot.exception.ResourceNotFoundException;
import com.javaguide.springboot.model.Employee;
import com.javaguide.springboot.repository.EmployeeRepository;
import com.javaguide.springboot.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServicesTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService; //used class name

    private Employee employee;
    @BeforeEach
    public void setUp(){
        //static mock method for create mock of employeeRepository
      //  employeeRepository = Mockito.mock(EmployeeRepository.class);

        //EmployeeRepository inject in employeeSerceImpl class
      //  employeeService = new EmployeeServiceImpl(employeeRepository);
        //create a Employee object
        employee = Employee.builder()
                .id(1L)
                .firstName("Ramesh")
                .lastName("Fadatare")
                .email("ramsh@gmail.com")
                .build();
    }
      //Junit test for saveEmployee method
          @Test
          @DisplayName("Junit test for saveEmployee method")
          public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
              //given - precondition or setup
//              Employee employee = Employee.builder()
//                      .id(1L)
//                      .firstName("Ramesh")
//                      .lastName("Fadatare")
//                      .email("ramsh@gmail.com")
//                      .build();

              //Stubbing the method like findByEmail(), save() method
             given(employeeRepository.findByEmail(employee.getEmail()))
                      .willReturn(Optional.empty());
              given(employeeRepository.save(employee))
                      .willReturn(employee);
              System.out.println(employeeRepository);
              System.out.println(employeeService);

              //when - action or the behaviour that we are going test
              Employee savedEmployee = employeeService.saveEmployee(employee);
              System.out.println(savedEmployee);

              //then - verify the output
              Assertions.assertThat(savedEmployee).isNotNull();

          }
    @Test
    @DisplayName("Junit test for saveEmployee method which throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowException(){
        //given - precondition or setup
//
        //Stubbing the method like findByEmail(), save() method
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));


        //when - action or the behaviour that we are going test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
                () ->{
            employeeService.saveEmployee(employee);
                });

        //then - verify the output
        verify(employeeRepository,never()).save(any(Employee.class));
    }

      //Junit test for get all Employees method
          @Test
          @DisplayName("Junit test for get all Employees method")
          public void givenEmployeeList_whenGetAllEmployee_thenRetEmployeeList(){
              //given - precondition or setup
           Employee employee1 = Employee.builder()
                      .id(1L)
                      .firstName("Tony")
                      .lastName("Stark")
                      .email("tony@gmail.com")
                      .build();
              given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

              //when - action or the behaviour that we are going test

              List<Employee> employeeList = employeeService.getAllEmployees();
              //then - verify the output
              Assertions.assertThat(employeeList).isNotNull();
              Assertions.assertThat(employeeList.size()).isEqualTo(2);

          }
            //Junit test for get all Employee method (negative senerio)
                @Test
                public void givenEmptyEmployeeList_whenGetAllEmployee_thenReturnEmptyEmployeeList(){
                    //given - precondition or setup
                    Employee employee1 = Employee.builder()
                            .id(1L)
                            .firstName("Tony")
                            .lastName("Stark")
                            .email("tony@gmail.com")
                            .build();
                    given(employeeRepository.findAll()).willReturn(Collections.emptyList());

                    //when - action or the behaviour that we are going test
                    List<Employee> employeeList = employeeService.getAllEmployees();

                    //then - verify the output
                    Assertions.assertThat(employeeList).isEmpty();
                   assertThat(employeeList.size()).isEqualTo(0);

    }
  //Junit test for get EmployeeById method
      @Test
      public void givenEmployeeId_whenGetEmployeeById_thenreturnEmployeeObject(){
          //given - precondition or setup
          given(employeeRepository.findById(1L))
                  .willReturn(Optional.of(employee));


          //when - action or the behaviour that we are going test
          Employee savedEmployee = employeeService.getEmployeeByID(employee.getId()).get();

          //then - verify the output
          assertThat(savedEmployee).isNotNull();


      }
        //Junit test for update employee method
            @Test
            @DisplayName("Junit test for update employee method")
            public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
                //given - precondition or setup
                given(employeeRepository.save(employee)).willReturn(employee);
                employee.setEmail("ram@gmail.com");
                employee.setFirstName("Ram");

                //when - action or the behaviour that we are going test
                Employee updateEmployee = employeeService.updateEmployee(employee);


                //then - verify the output
                assertThat(updateEmployee.getEmail()).isEqualTo("ram@gmail.com");
                assertThat(updateEmployee.getFirstName()).isEqualTo("Ram");

            }
              //Junit test for delete employee method
                  @Test
                  @DisplayName("Junit test for delete employee method")
                  public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){
                      //given - precondition or setup
                      // willDoNothing() method is return void
                      long employeeId = 1L;
                      willDoNothing().given(employeeRepository).deleteById(1L);



                      //when - action or the behaviour that we are going test
                      employeeService.deleteEmployee(1L);

                      //then - verify the output
                      //becouse delete method doesnot return any things
                      verify(employeeRepository, times(1)).deleteById(employeeId);

                  }
}
