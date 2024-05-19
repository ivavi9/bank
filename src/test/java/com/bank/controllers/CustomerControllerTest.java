package com.bank.controllers;


import com.bank.dtos.CustomerDTO;
import com.bank.exceptions.CustomerNotFoundException;
import com.bank.exceptions.GlobalExceptionHandler;
import com.bank.models.Customer;
import com.bank.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Test
    void testCreateCustomer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("somethingemail@gmail.com");
        customer.setPhone("123456789");
        customer.setAddress("1234, Some Street, Some City, Some Country");


        when(customerService.addNewCustomer(any(CustomerDTO.class))).thenReturn(customer);
        mockMvc.perform(post("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"firstName\": \"John\",\n" +
                        "    \"lastName\": \"Doe\",\n" +
                        "    \"email\": \"somethingemail@gmail.com\",\n" +
                        "    \"phone\": \"123456789\",\n" +
                        "    \"address\": \"1234, Some Street, Some City, Some Country\"\n }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("somethingemail@gmail.com"))
                .andExpect(jsonPath("$.phone").value("123456789"))
                .andExpect(jsonPath("$.address").value("1234, Some Street, Some City, Some Country"));

    }

    @Test
    void testDeleteCustomerNotFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        String customerID = "17a9ff54-13a5-11ef-a863-6ae025190f14";
        doThrow(new CustomerNotFoundException("Customer not found")).when(customerService).deleteCustomer(customerID);

        mockMvc.perform(delete("/api/v1/customers/{customerID}", customerID))
                .andExpect(status().isNotFound());

    }
}
