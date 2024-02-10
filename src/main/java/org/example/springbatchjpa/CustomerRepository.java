package org.example.springbatchjpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepository extends JpaRepository<Customer, Long> {
}
