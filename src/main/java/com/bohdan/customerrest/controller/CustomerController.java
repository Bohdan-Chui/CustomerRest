package com.bohdan.customerrest.controller;

import com.bohdan.customerrest.dto.CustomerDto;
import com.bohdan.customerrest.dto.CustomerFullDto;
import com.bohdan.customerrest.dto.CustomerIdDto;
import com.bohdan.customerrest.model.Customer;
import com.bohdan.customerrest.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    CustomerService customerService;
    ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<CustomerFullDto> saveCustomer(@Valid @RequestBody CustomerDto customer) {
        Customer response = customerService.saveActiveCustomer(modelMapper.map(customer, Customer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(response, CustomerFullDto.class));
    }

    @GetMapping
    public ResponseEntity<List<CustomerFullDto>> getCustomers() {
        List<CustomerFullDto> response = customerService.getCustomers()
                .stream()
                .map(n -> modelMapper.map(n, CustomerFullDto.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerFullDto> getCustomer(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modelMapper.map(customerService.getCustomer(id), CustomerFullDto.class));
    }

    @PatchMapping
    public ResponseEntity<CustomerFullDto> updateCustomer(@RequestBody CustomerIdDto customer) {
        Customer response = customerService.updateCustomer(modelMapper.map(customer, Customer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(response, CustomerFullDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.ok().build();

    }
}
