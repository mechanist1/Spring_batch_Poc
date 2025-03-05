package org.example.demo_spring_batch.Direct;


import org.example.demo_spring_batch.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
public class JobLauncher {

    private org.example.demo_spring_batch.EmployeeRepository EmployeeRepository;
    private Writer writer;
    private Reader reader;
    private EmployeeProcessor processor;
    private JobRepository jobRepository;
    private PlatformTransactionManager platformTransactionManager;



    public Step importStep() throws Exception {
        return new StepBuilder("Step1",jobRepository)
                .<Employee,Employee>chunk(30,platformTransactionManager).reader(reader.EmployeeReader())
                .writer(writer.write())
                .taskExecutor(taskExecutor())
                .build();

    }
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(10);
        return taskExecutor;
    }
    @Bean
    public Job runJob() throws Exception {
        return new JobBuilder("myJob",jobRepository)
                .start(importStep())
                .build();
    }

}
