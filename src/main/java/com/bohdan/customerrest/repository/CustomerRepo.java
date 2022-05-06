package com.bohdan.customerrest.repository;

import com.bohdan.customerrest.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> getCustomerByEmail(String s);
    List<Customer> getCustomersByIsActive(Boolean isActive);
}
