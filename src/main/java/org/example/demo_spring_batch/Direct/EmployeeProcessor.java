package org.example.demo_spring_batch.Direct;

import org.example.demo_spring_batch.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {


    @Override
    public Employee process(Employee Employee) throws Exception {
        try {
            return Employee;
        }
      catch (Exception e) {
            e.printStackTrace();
      }
        return Employee;
    }
    public Object process2() throws Exception {

        return null;
    }
}
