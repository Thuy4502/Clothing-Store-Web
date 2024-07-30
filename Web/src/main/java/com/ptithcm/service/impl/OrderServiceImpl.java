package com.ptithcm.service.impl;

import com.ptithcm.exception.OrderException;
import com.ptithcm.model.*;
import com.ptithcm.repository.CartRepository;
import com.ptithcm.repository.OrderItemRepository;
import com.ptithcm.repository.OrderRepository;
import com.ptithcm.repository.UserRepository;
import com.ptithcm.request.OrderRequest;
import com.ptithcm.service.CartItemService;
import com.ptithcm.service.CartService;
import com.ptithcm.service.OrderService;
import com.ptithcm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;
    private UserRepository userRepository;
    private OrderItemRepository orderItemRepository;
    private CartService cartService;


    @Override
    public Order createOrder(Customer customer, Order req) {
        Cart cart = cartService.findCustomerCart(customer.getCustomerId());
        List<OrderItem> orderItems= new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setProductDetail(item.getProductDetail());
            orderItem.setSize(item.getSize());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        Order createdOrder = new Order();
        createdOrder.setCustomer_id(customer.getCustomerId());
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalAmount(cart.getTotalPrice());
        createdOrder.setTotalItem(cart.getTotalItem());
        createdOrder.setStatus("PENDING");
        createdOrder.setCustomer_id(customer.getCustomerId());
        createdOrder.setCustomerName(req.getCustomerName());
        createdOrder.setCustomerAddress(req.getCustomerAddress());
        createdOrder.setCustomerPhone(req.getCustomerPhone());
        createdOrder.setCustomerEmail(req.getCustomerEmail());
        createdOrder.setOrderDate(LocalDateTime.now());
        Order savedOrder=orderRepository.save(createdOrder);

        for (OrderItem item: orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }
        return savedOrder;
    }


    @Override
    public List<Order> userOrderHistory(Long userId) {
        List<Order> orders = orderRepository.getUserOrders(userId);
        return orders;
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
//        Order order = orderRepository.findAllById(orderId);

        return null;
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
        order.setStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
        order.setStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order canceledOrder(Long orderId) throws OrderException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Order finOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new OrderException("Order not found with ID: " + orderId);
        }
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = finOrderById(orderId);
        orderRepository.delete(order);
    }

    @Override
    public List<OrderRequest> findAllOrderItems(Long orderId) {
        // Lấy dữ liệu từ repository bằng cách sử dụng câu truy vấn JPQL
        List<Object[]> results = orderRepository.findAllOrderItem(orderId);

        // Chuyển đổi dữ liệu từ Object[] sang OrderRequest
        List<OrderRequest> orderItems = results.stream()
                .map(result -> {
                    try {
                        // Ensure that the types match correctly with the database schema
                        double totalAmount = (Double) result[0];          // totalAmount
                        int totalItem = ((Number) result[1]).intValue();  // totalItem
                        String customerName = (String) result[2];         // customerName
                        String customerAddress = (String) result[3];      // customerAddress
                        String customerPhone = (String) result[4];        // customerPhone
                        String customerEmail = (String) result[5];        // customerEmail
                        String status = (String) result[6];
                        Long productId = (Long) result[7];
                        Long orderItemId = (Long) result[8];
                        String productName = (String) result[9];          // productName
                        double price = (Double) result[10];                // price
                        int quantity = ((Number) result[11]).intValue();   // quantity
                        String size = (String) result[12];                 // size
                        String reviewStatus = (String) result[13];
                        String color = (String) result[14];                // color
                        String image = (String) result[15];                // image
                        String brandName = (String) result[16];           // brandName

                        return new OrderRequest(
                                totalAmount,
                                totalItem,
                                customerPhone,
                                customerEmail,
                                customerName,
                                customerAddress,
                                status,
                                productId,
                                orderItemId,
                                productName,
                                price,
                                quantity,
                                size,
                                reviewStatus,
                                color,
                                image,
                                brandName
                        );
                    } catch (ClassCastException e) {
                        throw new RuntimeException("Data type mismatch in query result", e);
                    }
                })
                .collect(Collectors.toList());

        return orderItems;

    }


}
