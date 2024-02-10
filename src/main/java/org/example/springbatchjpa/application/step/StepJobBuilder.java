package org.example.springbatchjpa.application.step;

import org.example.springbatchjpa.batch.JobExecutionListener;
import org.example.springbatchjpa.batch.Step;
import org.example.springbatchjpa.batch.StepJob;

import java.util.ArrayList;
import java.util.List;

public class StepJobBuilder {
    private final List<Step> steps;
    public JobExecutionListener jobExecutionListener;


    public StepJobBuilder() {
        this.steps = new ArrayList<>();

    }

    public StepJobBuilder start(Step step) {
        if (steps.isEmpty()) {
            steps.add(step);
        } else {
            steps.set(0, step);
        }
        return this;
    }

    public StepJobBuilder next(Step step) {
        steps.add(step);
        return this;
    }

    public StepJobBuilder listener(JobExecutionListener jobExecutionListener) {
        this.jobExecutionListener = jobExecutionListener;
        return this;
    }

    public StepJob build() {
        return new StepJob(steps, jobExecutionListener);
    }

}
