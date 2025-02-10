package org.example.demo_spring_batch;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {


    @Override
    public Employee process(Employee Employee) throws Exception {
        return Employee;
    }
}
