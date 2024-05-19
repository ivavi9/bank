package com.bank.services;

import com.bank.dtos.CustomerDTO;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.models.Customer;
import com.bank.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customer: customers){
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setPhone(customer.getPhone());
            customerDTO.setAddress(customer.getAddress());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public CustomerDTO getCustomerByID(String id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(UUID.fromString(id)).orElseThrow(()->new CustomerNotFoundException("Customer not found"));
        System.out.println("In CustomerService");
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setAddress(customer.getAddress());

        return customerDTO;
    }

    public CustomerDTO addNewCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setAddress(customerDTO.getAddress());

        if(customer.getFirstName() == null || customer.getLastName() == null || customer.getEmail() == null || customer.getPhone() == null || customer.getAddress() == null){
            throw new IllegalArgumentException("All fields are required");
        }
        if(customerRepository.getCustomerByEmail(customer.getEmail()) != null){
            throw new IllegalArgumentException("Customer with same email already exists");
        }


        customerRepository.save(customer);
        return customerDTO;
    }

    public CustomerDTO deleteCustomer(String id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(UUID.fromString(id)).orElseThrow(()->new CustomerNotFoundException("Customer not found"+id));

        if(customer.isDeleted()){
            throw new IllegalArgumentException("Customer already deleted"+id);
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setAddress(customer.getAddress());
        customer.setDeleted(true);
        customerRepository.save(customer);
        return customerDTO;
    }
}
