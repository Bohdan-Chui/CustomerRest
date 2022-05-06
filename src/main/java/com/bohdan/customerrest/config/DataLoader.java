package com.bohdan.customerrest.config;

import com.bohdan.customerrest.model.Customer;
import com.bohdan.customerrest.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/*
 * Database initializer class
 * Save records to database for easy-testing api
 *
 * */
@Component
public class DataLoader implements ApplicationRunner {

    private final CustomerRepo customerRepo;

    @Autowired
    public DataLoader(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        customerRepo.save(Customer.builder().email("alexander@gmail.com").fullName("Alex")
                .phone("+3809999999999").isActive(true).build());
        customerRepo.save(Customer.builder().email("Bohdan@gmail.com").fullName("Bohdan")
                .phone("+380666666666").isActive(true).build());
        customerRepo.save(Customer.builder().email("Olha@gmail.com").fullName("Bohdan")
                .phone("+3803333333333").isActive(false).build());
        customerRepo.save(Customer.builder().email("Misha@gmail.com").fullName("Misha")
                .phone("+380888888888").isActive(true).build());
    }
}
