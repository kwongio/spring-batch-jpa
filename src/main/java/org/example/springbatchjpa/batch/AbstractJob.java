package org.example.springbatchjpa.batch;


import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component

public abstract class AbstractJob implements Job {
    private final JobExecutionListener jobExecutionListener;

    public AbstractJob(JobExecutionListener jobExecutionListener) {
        this.jobExecutionListener = Objects.requireNonNullElseGet(jobExecutionListener, () -> new JobExecutionListener() {
            @Override
            public void beforeJob(JobExecution jobExecution) {
            }

            @Override
            public void afterJob(JobExecution jobExecution) {
            }
        });
    }

    @Override
    public JobExecution execute() {

        final JobExecution jobExecution = new JobExecution();
        jobExecution.setStatus(BatchStatus.STARTING);
        jobExecution.setStartTime(LocalDateTime.now());

        jobExecutionListener.beforeJob(jobExecution);

        try {

            doExecute();
            jobExecution.setStatus(BatchStatus.COMPLETED);

        } catch (Exception e) {
            jobExecution.setStatus(BatchStatus.FAILED);
        }

        jobExecution.setEndTime(LocalDateTime.now());

        jobExecutionListener.afterJob(jobExecution);

        return jobExecution;
    }

    abstract void doExecute();
}
