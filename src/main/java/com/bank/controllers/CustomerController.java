package com.bank.controllers;


import com.bank.dtos.CustomerDTO;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @GetMapping("/")
    public List<CustomerDTO> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerbyID(@PathVariable String id) throws CustomerNotFoundException {
        CustomerDTO customerDTO = customerService.getCustomerByID(id);
        return new ResponseEntity<>(customerDTO , HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<CustomerDTO> addNewCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO newCustomerDTO = customerService.addNewCustomer(customerDTO);
        return new ResponseEntity<>(newCustomerDTO, HttpStatus.CREATED);
    }


}
