package org.example.springbatchjpa.application.step;


import org.example.springbatchjpa.batch.Job;
import org.example.springbatchjpa.batch.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StepExampleBatchConfiguration {


    @Bean
    public Job stepExample(
            Step step1,
            Step step2,
            Step step3
    ) {
        return new StepJobBuilder()
                .start(step1)
                .next(step2)
                .next(step3)
                .build();
    }

    @Bean
    public Step step1() {
        return new Step(() -> System.out.println("step1"));
    }

    @Bean
    public Step step2() {
        return new Step(() -> System.out.println("step2"));
    }

    @Bean
    public Step step3() {
        return new Step(() -> System.out.println("step3"));
    }
}
