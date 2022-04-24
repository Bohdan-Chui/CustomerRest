package com.bohdan.customerrest.service;

import com.bohdan.customerrest.model.Customer;
import com.bohdan.customerrest.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;

    @Autowired
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer saveActiveCustomer(Customer customer) {
        if (customer.getIsActive() == null) {
            customer.setIsActive(true);
        }
        return saveCustomer(customer);
    }

    public Customer saveCustomer(Customer customer) {
        if(customerRepo.getCustomerByEmail(customer.getEmail()).isPresent())
            throw new EntityExistsException("Customer is already present with this email");
        return customerRepo.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepo.findById(id).orElseThrow(()->new NoSuchElementException("no user with this id"));
    }

    public void deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.orElseThrow(
                ()->new NoSuchElementException("no user with this id"));

        customer.setIsActive(false);
        customerRepo.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        Customer savedCustomer = customerRepo.findById(customer.getId())
                .orElseThrow(()->new NoSuchElementException("no user with this id"));

        savedCustomer.setFullName(customer.getFullName());
        savedCustomer.setPhone(customer.getPhone());
        return customerRepo.save(savedCustomer);
    }
}
