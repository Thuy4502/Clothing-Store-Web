package com.ptithcm.service;

import com.ptithcm.model.OrderItem;

import java.util.Optional;

public interface OrderItemService {
    public OrderItem createOderItem(OrderItem orderItem);
    public OrderItem findOrderItemById(Long id);

}
