package com.ptithcm.service;

import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Cart;
import com.ptithcm.model.Customer;
import com.ptithcm.model.User;
import com.ptithcm.request.AddCartItemRequest;
import com.ptithcm.request.CartRequest;

import java.util.List;

public interface CartService {

    public Cart createCart(Customer customer);

    public String addCartItem(Long userId, AddCartItemRequest req) throws ProductException, UserException;

    public Cart findCustomerCart(Long customerId);

    List<CartRequest> findAllCartCustomer(Long customerId);
}

