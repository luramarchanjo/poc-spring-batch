package com.example.split;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.UUID;

@Configuration
public class SplitConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final ThreadTasklet threadTasklet;

    public SplitConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                              ThreadTasklet threadTasklet) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.threadTasklet = threadTasklet;
    }

    @Bean
    public Flow firstFlow() {
        return new FlowBuilder<Flow>("first-flow")
                .start(stepBuilderFactory.get("first-flow-step-one").tasklet(this.threadTasklet).build())
                .build();
    }

    @Bean
    public Flow secondFlow() {
        return new FlowBuilder<Flow>("second-flow")
                .start(stepBuilderFactory.get("second-flow-step-one").tasklet(this.threadTasklet).build())
                .next(stepBuilderFactory.get("second-flow-step-two").tasklet(this.threadTasklet).build())
                .build();
    }

    @Bean
    public Job splitJob() {
        return this.jobBuilderFactory.get(UUID.randomUUID().toString())
                .start(firstFlow())
                .split(new SimpleAsyncTaskExecutor())
                .add(secondFlow())
                .end()
                .build();
    }

}