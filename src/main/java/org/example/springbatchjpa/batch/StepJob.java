package org.example.springbatchjpa.batch;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StepJob extends AbstractJob {

    private final List<Step> steps;

    public StepJob(List<Step> steps, JobExecutionListener jobExecutionListener) {
        super(jobExecutionListener);
        this.steps = steps;
    }

    @Override
    void doExecute() {
        steps.forEach(Step::execute);
    }

}
