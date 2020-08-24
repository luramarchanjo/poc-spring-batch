package com.example.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ThreadTasklet implements Tasklet {

    private final Logger log = LoggerFactory.getLogger(ThreadTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        var threadName = Thread.currentThread().getName();
        var stepName = chunkContext.getStepContext().getStepName();
        log.info("[{}] This Tasklet has been executed on thread=[{}]", stepName, threadName);
        return RepeatStatus.FINISHED;
    }

}