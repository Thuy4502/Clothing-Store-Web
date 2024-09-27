package com.ptithcm.service;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer findByEmail(String email);
    boolean checkEmailExist(String email);
    Customer findCustomerByUserId(Long userId);
    Customer updateCustomer(String jwt, Customer customer) throws UserException;
    List<Customer> getAllCustomer(String jwt);
}
