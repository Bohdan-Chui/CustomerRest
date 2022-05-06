package com.bohdan.customerrest.controller;

import com.bohdan.customerrest.dto.SaveCustomerDto;
import com.bohdan.customerrest.dto.CustomerResponseDto;
import com.bohdan.customerrest.dto.CustomerPatchDto;
import com.bohdan.customerrest.dto.CustomerPutDto;
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
    public ResponseEntity<CustomerResponseDto> saveCustomer(@Valid @RequestBody SaveCustomerDto customer) {
        Customer response = customerService.saveActiveCustomer(modelMapper.map(customer, Customer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(response, CustomerResponseDto.class));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getCustomers() {
        List<CustomerResponseDto> response = customerService.getActiveCustomers()
                .stream()
                .map(n -> modelMapper.map(n, CustomerResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(modelMapper.map(customerService.getCustomer(id), CustomerResponseDto.class));
    }

    @PutMapping
    public ResponseEntity<CustomerResponseDto> updateCustomer(@Valid @RequestBody CustomerPutDto customer) {
        Customer response = customerService.updateCustomer(modelMapper.map(customer, Customer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(response, CustomerResponseDto.class));
    }

    @PatchMapping
    public ResponseEntity<CustomerResponseDto> patchUpdateCustomer(@Valid @RequestBody CustomerPatchDto customer) {
        Customer response = customerService.patchUpdateCustomer(modelMapper.map(customer, Customer.class));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(response, CustomerResponseDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.disableCustomerById(id);
        return ResponseEntity
                .ok()
                .build();

    }
}
