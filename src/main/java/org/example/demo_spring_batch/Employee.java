package org.example.demo_spring_batch;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employees")
@Data
public class Employee {
    @Id
    private Long id;
    private String name;
    private String department;
    private Double salary;
}
