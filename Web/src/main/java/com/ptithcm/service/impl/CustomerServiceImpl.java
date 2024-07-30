package com.ptithcm.service.impl;

import com.ptithcm.model.Customer;
import com.ptithcm.repository.CustomerRepository;
import com.ptithcm.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public boolean checkEmailExist(String email) {
        Customer customer = findByEmail(email);
        if(customer != null) {
            return true;
        }
        return false;
    }

    @Override
    public Customer findCustomerByUserId(Long userId) {
        return customerRepository.findCustomerByUserId(userId);
    }
}
