package com.example.RentaCar.repository;
import com.example.RentaCar.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Long, Customer> {
}
