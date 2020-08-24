package com.example.decider;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class DeciderConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    public DeciderConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Step startStep() {
        return this.stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the Start Step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step evenStep() {
        return this.stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the Even Step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step oddStep() {
        return this.stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("This is the Odd Step");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Job jobOne(OddDecider oddDecider) {
        return this.jobBuilderFactory.get(UUID.randomUUID().toString())
                .start(startStep())
                .next(oddDecider)
                .from(oddDecider).on("ODD").to(oddStep())
                .from(oddDecider).on("EVEN").to(evenStep())
                // Start again to test the EVEN step
                .from(oddStep()).on("*").to(oddDecider)
                .end()
                .build();
    }

}