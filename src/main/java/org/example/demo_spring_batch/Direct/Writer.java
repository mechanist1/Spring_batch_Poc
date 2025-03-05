package org.example.demo_spring_batch.Direct;

import org.example.demo_spring_batch.Employee;
import org.example.demo_spring_batch.EmployeeRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class Writer {

    private EmployeeRepository employeeRepository;

    public Writer(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Bean
    public RepositoryItemWriter<Employee> write() {
        RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
        writer.setRepository(employeeRepository);
        writer.setMethodName("save");
        return writer;

    }
}
