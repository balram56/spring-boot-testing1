package com.javaguide.springboot.repository;

import com.javaguide.springboot.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    //Inject EmployeeRepository
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    public void setUp(){
        employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fathedar")
                .email("ramesh@gmail.com")
                .build();
    }

    //Junit test for save employee operation
    @DisplayName(("Junit test for save employee operation"))
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){

        //step 1- given- precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();

        //step 2- when-test action or behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);


        //step 3- then-Verify or validate output
       assertThat(savedEmployee).isNotNull();
      assertThat(savedEmployee.getId()).isGreaterThan(0);

    }
    //Junit test for
    @DisplayName("Junit for get all employee list")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        //given -precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Ramesh")
//                .email("ramesh@gmail.com")
//                .build();
        Employee employee1 = Employee.builder()
                .firstName("Balram")
                .lastName("Kumar")
                .email("balram@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //step 2- when-test action or behaviour that we are going test
       List<Employee>  employeeList = employeeRepository.findAll();

        //step 3- then-Verify or validate output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //junit for get employee by id operation
     //Junit test for
         @Test
         @DisplayName("get employee by id operation")
         public void givenEmployeeObject_whenFindById_thenEmployeeObject(){
//             //given -precondition or setup
//             Employee employee = Employee.builder()
//                     .firstName("Ramesh")
//                     .lastName("Ramesh")
//                     .email("ramesh@gmail.com")
//                     .build();
            employeeRepository.save(employee);

             //step 2- when-test action or behaviour that we are going test
           Employee employeeDB =  employeeRepository.findById(employee.getId()).get();
             //step 3- then-Verify or validate output
             assertThat(employeeDB).isNotNull();
         }
//Junit test for query method

     //Junit test for get employee by email operation
         @Test
         @DisplayName("Junit test for get employee by email operation")
         public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
             //given -precondition or setup
//             Employee employee = Employee.builder()
//                     .firstName("Ramesh")
//                     .lastName("Ramesh")
//                     .email("ramesh@gmail.com")
//                     .build();
             employeeRepository.save(employee);

             //step 2- when-test action or behaviour that we are going test
             Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
             //step 3- then-Verify or validate output
             assertThat(employeeDB).isNotNull();
         }
    //Junit test for update employee opertion

         @Test
         public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployee(){
             //given -precondition or setup
//             Employee employee = Employee.builder()
//                     .firstName("Ramesh")
//                     .lastName("Ramesh")
//                     .email("ramesh@gmail.com")
//                     .build();
             employeeRepository.save(employee);
             //step 2- when-test action or behaviour that we are going test
             Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
             //changes or update employee details
             savedEmployee.setEmail("ram@gmail.com");
             savedEmployee.setFirstName("Ram");
             Employee updatedEmployee = employeeRepository.save(savedEmployee);
             //step 3- then-Verify or validate output
             assertThat(updatedEmployee.getEmail()).isEqualTo("ram@gmail.com");
             assertThat(updatedEmployee.getFirstName()).isEqualTo("Ram");
         }
          //Junit test for delete employee operation
              @Test
              public void givenEmployeeObject_whenDelete_thenRemoveEmployeeObject(){
                  //given -precondition or setup
//                  Employee employee = Employee.builder()
//                          .firstName("Ramesh")
//                          .lastName("Fathedar")
//                          .email("ramesh@gmail.com")
//                          .build();
                  employeeRepository.save(employee);

                  //step 2- when-test action or behaviour that we are going test
                  employeeRepository.deleteById(employee.getId());
                  Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
                  //step 3- then-Verify or validate output
                  assertThat(employeeOptional).isEmpty();
              }
                //Junit test for custom query using jpql with index
                    @Test
                    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
                        //given - precondition or setup
//                        Employee employee = Employee.builder()
//                                .firstName("Ramesh")
//                                .lastName("Fathedar")
//                                .email("ramesh@gmail.com")
//                                .build();
                        employeeRepository.save(employee);
                        String firstName = "Ramesh";
                        String lastName = "Fathedar";

                        //when - action or the behaviour that we are going test
                Employee savedEmployee = employeeRepository.findByJPQL(firstName,lastName);
                        //then - verify the output
                        assertThat(savedEmployee).isNotNull();

                    }

    //Junit test for custom query using jpql with named Param
    @Test
    @DisplayName("Junit test for custom query using jpql with named Param")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParam_thenReturnEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Fathedar")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fathedar";

        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName,lastName);
        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
      //Junit test for custom query using native sql with index
          @Test
          @DisplayName("Junit test for custom query using native sql with index")
          public void givenFirstNameAndLastName_whenFindBYNativeSQL_thenRetunEmployeeObject(){
              //given - precondition or setup
//              Employee employee = Employee.builder()
//                      .firstName("Ramesh")
//                      .lastName("Fathedar")
//                      .email("ramesh@gmail.com")
//                      .build();
              employeeRepository.save(employee);
              String firstName = "Ramesh";
              String lastName = "Fathedar";
              //when - action or the behaviour that we are going test
      Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());
              //then - verify the output
              assertThat(savedEmployee).isNotNull();

          }
    //Junit test for custom query using native sql with index
    @Test
    @DisplayName("Junit test for custom query using native sql with index")
    public void givenFirstNameAndLastName_whenFindBYNativeSQLNamedParam_thenRetunEmployeeObject(){
        //given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Ramesh")
//                .lastName("Fathedar")
//                .email("ramesh@gmail.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Ramesh";
        String lastName = "Fathedar";
        //when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamedParams(employee.getFirstName(), employee.getLastName());
        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
}
