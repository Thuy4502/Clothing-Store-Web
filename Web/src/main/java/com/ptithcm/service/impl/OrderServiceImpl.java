package com.ptithcm.service.impl;

import com.ptithcm.exception.OrderException;
import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.repository.*;
import com.ptithcm.request.OrderRequest;
import com.ptithcm.service.*;
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
    private UserService userService;
    private StaffService staffService;
    private OrderItemRepository orderItemRepository;
    private CartService cartService;
    private BillRepository billRepository;
    private ProductDetailRepository productDetailRepository;
    private CustomerService customerService;


    @Override
    public Order createOrder(Customer customer, Order req) throws ProductException {
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
            item.setOrder_id(savedOrder.getOrderId());
            ProductDetail detail = productDetailRepository.findProductDetailBySizeNameAndProductId(item.getProductDetail().getProduct().getProductId(), item.getSize());
            item.setProduct_detail_id(detail.getProductDetailId());
            detail.setQuantity(detail.getQuantity()- item.getQuantity());
            productDetailRepository.save(detail);
            orderItemRepository.save(item);
        }
        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cart.setTotalItem(0);
        cartRepository.save(cart);

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
    public Order deliveredOrder(Long orderId, String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Staff staff = staffService.findStaffByUserId(user.getUserId());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with ID: " + orderId));
        order.setStatus("DELIVERED");
        Order save = orderRepository.save(order);
        Bill bill = new Bill();
        bill.setOrder_id(save.getOrderId());
        bill.setCreated_at(LocalDateTime.now());
        bill.setCreated_by(staff.getStaffId());
        billRepository.save(bill);

        return save;
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

        List<Object[]> results = orderRepository.findAllOrderItem(orderId);

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

    @Override
    public Order orderBuyNow(String jwt, OrderRequest rq) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setCustomerName(rq.getCustomerName());
        order.setCustomer_id(customer.getCustomerId());
        order.setCustomerEmail(rq.getCustomerEmail());
        order.setCustomerAddress(rq.getCustomerAddress());
        order.setStatus("PENDING");
        order.setCustomerPhone(rq.getCustomerPhone());
        order.setTotalItem(rq.getTotalItem());
        order.setTotalAmount(rq.getTotalAmount());
        Order save = orderRepository.save(order);
        if(save != null){
            OrderItem orderItem = new OrderItem();
            ProductDetail productDetail = productDetailRepository.findProductDetailBySizeNameAndProductId(rq.getProductId(),rq.getSize());
            orderItem.setQuantity(rq.getQuantity());
            orderItem.setSize(rq.getSize());
            orderItem.setPrice(rq.getPrice());
            orderItem.setColor(rq.getColor());
            orderItem.setProduct_detail_id(productDetail.getProductDetailId());
            orderItem.setOrder_id(save.getOrderId());
            productDetail.setQuantity(productDetail.getQuantity()- rq.getQuantity());
            productDetailRepository.save(productDetail);
            orderItemRepository.save(orderItem);
        }
        return save;
    }




}
