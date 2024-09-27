package com.ptithcm.service.impl;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.User;
import com.ptithcm.repository.CustomerRepository;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
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

    @Override
    @Transactional
    public Customer updateCustomer(String jwt, Customer customer) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer find = findCustomerByUserId(user.getUserId());
        find.setAddress(customer.getAddress());
        find.setEmail(customer.getEmail());
        find.setDateOfBirth(customer.getDateOfBirth());
        find.setFirstName(customer.getFirstName());
        find.setLastName(customer.getLastName());
        find.setPhoneNumber(customer.getPhoneNumber());
        find.setGender(customer.getGender());
        return customerRepository.save(find);
    }

    @Override
    public List<Customer> getAllCustomer(String jwt) {
        try {
            List<Customer> customers = customerRepository.findAll();
            return customers;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customers", e);
        }
    }

}
