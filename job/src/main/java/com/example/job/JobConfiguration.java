package com.example.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step extract() {
        return this.stepBuilderFactory.get("extract-step")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("extract-step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step transform() {
        return this.stepBuilderFactory.get("transform-step")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("transform-step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step load() {
        return this.stepBuilderFactory.get("load-step")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("load-step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get(UUID.randomUUID().toString())
                .start(extract())
                .next(transform())
                .next(load())
                .build();
    }

}
