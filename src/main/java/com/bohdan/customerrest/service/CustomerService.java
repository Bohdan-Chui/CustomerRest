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
        if (customerRepo.getCustomerByEmail(customer.getEmail()).isPresent()) {
            throw new EntityExistsException("Customer is already present with email - " + customer.getEmail());
        }
        return customerRepo.save(customer);
    }

    public List<Customer> getActiveCustomers() {
        return customerRepo.getCustomersByIsActive(true);
    }

    public Customer getCustomer(Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No user with id - " + id));
    }

    public void deleteCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.orElseThrow(
                () -> new NoSuchElementException("no user with id - " + id));

        customer.setIsActive(false);
        customerRepo.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        Customer repoCustomer = customerRepo.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("No user with id - " + customer.getId()));

        if (customer.getFullName() != null && customer.getPhone() != null) {
            repoCustomer.setFullName(customer.getFullName());
            repoCustomer.setPhone(customer.getPhone());
            return customerRepo.save(repoCustomer);
        } else {
            throw new IllegalArgumentException("Field name or phone is empty");
        }
    }

    public Customer patchUpdateCustomer(Customer customer) {
        Customer repoCustomer = customerRepo.findById(customer.getId())
                .orElseThrow(() -> new NoSuchElementException("No user with id - " + customer.getId()));

        if (customer.getFullName() != null) {
            repoCustomer.setFullName(customer.getFullName());
        }
        if (customer.getPhone() != null) {
            repoCustomer.setPhone(customer.getPhone());
        }
        return customerRepo.save(repoCustomer);
    }
}
