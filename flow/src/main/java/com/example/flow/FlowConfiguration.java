package com.example.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class FlowConfiguration {

    private final StepBuilderFactory stepBuilderFactory;

    private final JobBuilderFactory jobBuilderFactory;

    public FlowConfiguration(StepBuilderFactory stepBuilderFactory, JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public Step stepOne() {
        return this.stepBuilderFactory.get("step-001")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step-001");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepTwo() {
        return this.stepBuilderFactory.get("step-002")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step-002");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step stepThree() {
        return this.stepBuilderFactory.get("step-003")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("step-003");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Flow flow() {
        return new FlowBuilder<Flow>("firstFlow")
                .start(stepOne())
                .next(stepTwo())
                .build();
    }

    /**
     * This is a example how we can start a Job with a Flow.
     *
     * @return
     */
    @Bean
    public Job firstFlowExecution() {
        return this.jobBuilderFactory.get(UUID.randomUUID().toString())
                .start(flow())
                .next(stepThree())
                .end()
                .build();
    }

    /**
     * This is a example how we can end a Job with a Flow.
     *
     * @return
     */
    @Bean
    public Job lastFlowExecution() {
        return this.jobBuilderFactory.get(UUID.randomUUID().toString())
                .start(stepThree())
                .on("COMPLETED")
                .to(flow())
                .end()
                .build();
    }

}