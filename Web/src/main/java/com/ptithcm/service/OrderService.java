package com.ptithcm.service;

import com.ptithcm.exception.OrderException;
import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Order;
import com.ptithcm.model.User;
import com.ptithcm.request.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(Customer customer, Order req) throws ProductException;
    public List<Order> userOrderHistory(Long userId);
    public Order placeOrder(Long orderId) throws OrderException;
    public Order confirmOrder(Long orderId) throws OrderException;
    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId, String jwt) throws OrderException, UserException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrder();
    public Order finOrderById(Long orderId) throws OrderException;
    public void deleteOrder(Long orderId) throws OrderException;
    public List<OrderRequest> findAllOrderItems(Long orderId) throws OrderException;
    Order orderBuyNow(String jwt, OrderRequest rq) throws UserException;
}
