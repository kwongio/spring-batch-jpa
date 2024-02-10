package org.example.springbatchjpa;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DormantBatchJob {
    private final CustomerRepository customerRepository;

    private final EmailProvider emailProvider;

    public DormantBatchJob(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    @Transactional
    public JobExecution execute() {
        final JobExecution jobExecution = new JobExecution();
        jobExecution.setStatus(BatchStatus.STARTING);
        jobExecution.setStartTime(LocalDateTime.now());
        try {
            List<Customer> customers = customerRepository.findAll()
                    .parallelStream()
                    .filter(customer -> customer.getStatus() == Customer.Status.NORMAL)
                    .filter(customer -> LocalDate.now().minusYears(1).isAfter(customer.getLoginAt().toLocalDate()))
                    .toList();

            customers.parallelStream().forEach(customer -> customer.setStatus(Customer.Status.DORMANT));
            customerRepository.saveAll(customers);

            customers.parallelStream().forEach(customer -> emailProvider.send(customer.getEmail(), "휴먼전환 안내메일입니다.", "내용"));

            jobExecution.setStatus(BatchStatus.COMPLETED);
        } catch (Exception e) {
            jobExecution.setStatus(BatchStatus.FAILED);
        }
        jobExecution.setEndTime(LocalDateTime.now());
        emailProvider.send("admin@fastcampus.com", "배치 완료 알림", "DormantBatchJob이 수행되었습니다. status :" + jobExecution.getStatus());
        return jobExecution;
    }
}


