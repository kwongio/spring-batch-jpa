package org.example.springbatchjpa;

import org.example.springbatchjpa.batch.BatchStatus;
import org.example.springbatchjpa.batch.JobExecution;
import org.example.springbatchjpa.batch.TaskletJob;
import org.example.springbatchjpa.customer.Customer;
import org.example.springbatchjpa.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class DormantBatchJobTest {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TaskletJob dormantBatchJob;

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @DisplayName("로그인 시간이 일년을 경과한 고객이 세명이고, 일년 이내에 로그인한 고객이 다섯명이면 3명의 고객이 휴먼전환대상이다.")
    @Test
    void test() {
        saveCustomer(366);
        saveCustomer(366);
        saveCustomer(366);

        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);

        JobExecution jobExecution = dormantBatchJob.execute();

        long dormantCount = customerRepository.findAll().stream().filter(customer -> customer.getStatus() == Customer.Status.DORMANT).count();
        assertThat(dormantCount).isEqualTo(3);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);;

    }


    @DisplayName("고객이 열명이 있지만 모두 다 휴먼전환대상이 아니면 휴면전환 대상은 0명이다.")
    @Test
    void test1() {
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);
        saveCustomer(1);

        JobExecution jobExecution = dormantBatchJob.execute();

        long dormantCount = customerRepository.findAll().stream().filter(customer -> customer.getStatus() == Customer.Status.DORMANT).count();
        assertThat(dormantCount).isZero();
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);;

    }


    @DisplayName("고객이 없는 경우에도 배치는 정상동작해야한다.")
    @Test
    void test2() {
        JobExecution jobExecution = dormantBatchJob.execute();

        long dormantCount = customerRepository.findAll().stream().filter(customer -> customer.getStatus() == Customer.Status.DORMANT).count();
        assertThat(dormantCount).isZero();
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);;
    }


    @DisplayName("고객이 열명이 있지만 모두 다 휴먼전환대상이면(1년 경과한사람) 휴면전환 대상은 10명이다.")
    @Test
    void test3() {
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);

        JobExecution jobExecution = dormantBatchJob.execute();

        long dormantCount = customerRepository.findAll().stream().filter(customer -> customer.getStatus() == Customer.Status.DORMANT).count();
        assertThat(dormantCount).isEqualTo(10);
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);;

    }


    private void saveCustomer(long loginMinusDays) {
        final String uuid = UUID.randomUUID().toString();
        final Customer test = new Customer(uuid, uuid + "@fastcampus.com");
        test.setLoginAt(LocalDateTime.now().minusDays(loginMinusDays));
        customerRepository.save(test);

    }
}