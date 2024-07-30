package com.ptithcm.service.impl;

import com.ptithcm.exception.OrderItemNotFoundException;
import com.ptithcm.model.OrderItem;
import com.ptithcm.repository.OrderItemRepository;
import com.ptithcm.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired

    public OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
    public OrderItem findOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem with ID " + id + " not found"));
    }

}
