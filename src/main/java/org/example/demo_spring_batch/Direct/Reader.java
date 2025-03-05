package org.example.demo_spring_batch.Direct;

import org.example.demo_spring_batch.Employee;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class Reader {

    @Bean
    public FlatFileItemReader<Employee> EmployeeReader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/home/themechanist1/IdeaProjects/demo_spring_batch/src/main/resources/static/batch.csv"));
        reader.setLinesToSkip(1);
        reader.setName("employees");
        reader.setLineMapper(Linemapper());
        return reader;
    }
    private LineMapper<Employee> Linemapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(" ");
        tokenizer.setStrict(false);
        tokenizer.setNames(new String[]{"id", "first_name","last-name","email","age"});
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(tokenizer);
        return lineMapper;
    }
}
