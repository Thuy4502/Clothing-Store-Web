package com.ptithcm.controller.customer;

import com.ptithcm.exception.OrderException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Order;
import com.ptithcm.model.User;
import com.ptithcm.request.OrderRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.OrderService;
import com.ptithcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Currency;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrder();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<Order> confirmOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        Order order = orderService.confirmOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<Order> shipOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> deliverOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Order deleted");
        res.setStatus(HttpStatus.OK);
        res.setCode(200);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Order> createOrder(
                                             @RequestBody Order req,
                                             @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        Order order =orderService.createOrder(customer, req);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);

    }

    @GetMapping("/userOrderHistory")
    public ResponseEntity<List<Order>> userOrderHistory(
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        List<Order> orders=orderService.userOrderHistory(customer.getCustomerId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<List<OrderRequest>> findOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws UserException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
//        Order order = orderService.finOrderById(orderId);
        List<OrderRequest> order = orderService.findAllOrderItems(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
