package com.ptithcm.service;

import com.ptithcm.model.Customer;

public interface CustomerService {
    Customer findByEmail(String email);
    boolean checkEmailExist(String email);
    Customer findCustomerByUserId(Long userId);
}
