package com.bank.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;



@Getter
@Setter
public class CustomerDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
