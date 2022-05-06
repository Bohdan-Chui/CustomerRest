package com.bohdan.customerrest.service;

import com.bohdan.customerrest.exception.CustomerDeletedException;
import com.bohdan.customerrest.model.Customer;
import com.bohdan.customerrest.repository.CustomerRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
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

    /*
     * Save customer to database
     * -if customer in database is present and is active, throw EntityExistsException,
     * -if customer in database is present and NOT active then delete record in database
     * and put new record with active customer
     * -if customer unavailable in database create new record in database
     *
     * @param: active customer
     * */

    public Customer saveCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerRepo.getCustomerByEmail(customer.getEmail());
        if (customerOptional.isPresent()) {
            if (Boolean.TRUE.equals(customerOptional.get().getIsActive())) {
                throw new EntityExistsException("Customer is already present with email - " + customer.getEmail());
            } else {
                customerRepo.delete(customerOptional.get());
                log.info("Customer deleted from database : " + customer);
            }
        }
        Customer customerResult = customerRepo.save(customer);
        log.info("Customer saved: " + customerResult);
        return customerResult;
    }

    public List<Customer> getActiveCustomers() {
        return customerRepo.getCustomersByIsActive(true);
    }

    /*
    * Return customer by id if it is Active else threw exception
    *
    * @throw CustomerDeletedException
    * */
    public Customer getCustomer(Long id) {
        Customer customerResult = getActiveCustomer(id);

        if (Boolean.FALSE.equals(customerResult.getIsActive())) {
            throw new CustomerDeletedException(customerResult.getId());
        }
        return customerResult;
    }

    public Customer getActiveCustomer(Long id){
        Customer repoCustomer = customerRepo.findById(id).orElseThrow(() -> new NoSuchElementException("No customer with id - " + id));
        if(Boolean.FALSE.equals(repoCustomer.getIsActive())){
            throw new CustomerDeletedException("No customer with " + id);
        }
        return repoCustomer;
    }


    /*
     * Set param isActive false to customer in database
     *
     * @param customer id
     * @throw NoSuchElementException
     * * */
    public void disableCustomerById(Long id) {
        Customer customer = getActiveCustomer(id);
        customer.setIsActive(false);
        Customer customerResult = customerRepo.save(customer);
        log.info("customer deactivated : " + customerResult);
    }

    public Customer putUpdateCustomer(Customer customer) {
        Customer repoCustomer = getActiveCustomer(customer.getId());

        if (customer.getFullName() != null && customer.getPhone() != null) {
            repoCustomer.setFullName(customer.getFullName());
            repoCustomer.setPhone(customer.getPhone());
            Customer customerResult = customerRepo.save(repoCustomer);
            log.info("Customer updated : " + customerResult);
            return customerResult;
        } else {
            throw new IllegalArgumentException("Field name or phone is empty");
        }
    }
    public Customer patchUpdateCustomer(Customer customer) {
        Customer repoCustomer = getActiveCustomer(customer.getId());

        if (customer.getFullName() != null) {
            repoCustomer.setFullName(customer.getFullName());
        }
        if (customer.getPhone() != null) {
            repoCustomer.setPhone(customer.getPhone());
        }
        Customer customerResult = customerRepo.save(repoCustomer);
        log.info("Customer updated : " + customerResult);
        return customerResult;
    }
}

