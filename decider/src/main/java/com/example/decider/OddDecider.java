package com.example.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OddDecider implements JobExecutionDecider {

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (counter.incrementAndGet() % 2 == 0) {
            return new FlowExecutionStatus("EVEN");
        } else {
            return new FlowExecutionStatus("ODD");
        }
    }

}
