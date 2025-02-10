package org.example.demo_spring_batch;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private EmployeeRepository employeeRepository;

    private JobRepository jobRepository;

    private PlatformTransactionManager platformTransactionManager;

    public BatchConfig(EmployeeRepository employeeRepository, JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }


    @Bean
    public FlatFileItemReader<Employee> EmployeeReader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/home/themechanist1/IdeaProjects/demo_spring_batch/src/main/resources/static/batch.csv"));
        reader.setLinesToSkip(1);
        reader.setName("employees");
        reader.setLineMapper(Linemapper());
        return reader;
    }

    @Bean
    public EmployeeProcessor EmployeeProcessor(){
        EmployeeProcessor processor = new EmployeeProcessor();
        return processor;
    }

    @Bean
    public RepositoryItemWriter<Employee> write() {
        RepositoryItemWriter<Employee> writer = new RepositoryItemWriter<>();
        writer.setRepository(employeeRepository);
        writer.setMethodName("save");
        return writer;

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
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }

    @Bean
    public Step importStep(){
        return new StepBuilder("Step1",jobRepository)
                .<Employee,Employee>chunk(3,platformTransactionManager).reader(EmployeeReader())
                .processor(EmployeeProcessor())
                .writer(write())
                .taskExecutor(taskExecutor())
                .build();

    }
    @Bean
    public Job runJob(){
        return new JobBuilder("myJob",jobRepository)
                .start(importStep())
                .build();
    }
}
