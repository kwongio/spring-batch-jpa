package org.example.springbatchjpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component

public class DormantBaychjob {
    private final CustomerRepository customerRepository;

    private final EmailProvider emailProvider;

    public DormantBaychjob(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.emailProvider = new EmailProvider.Fake();
    }

    public void execute() {
        
    }
}

