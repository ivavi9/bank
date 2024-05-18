package com.bank.services;


import com.bank.exceptions.CustomerNotFoundException;
import com.bank.repositories.CustomerRepository;
import com.bank.dtos.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(UUID.fromString("17a9ff54-13a5-11ef-a863-6ae025190f01"))).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerService.getCustomerByID("17a9ff54-13a5-11ef-a863-6ae025190f01");
        });
    }

    @Test
    void testCreateCustomerWithMissingFields(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("John");
        customerDTO.setLastName("Doe");
        assertThrows(IllegalArgumentException.class, ()->{
            customerService.addNewCustomer(customerDTO);
        });
    }



}
