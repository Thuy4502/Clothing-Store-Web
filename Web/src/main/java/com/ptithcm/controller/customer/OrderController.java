package com.ptithcm.controller.customer;

import com.ptithcm.exception.OrderException;
import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Order;
import com.ptithcm.model.Promotion;
import com.ptithcm.model.User;
import com.ptithcm.request.OrderRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
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
@RequestMapping("/customer/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;

    @PostMapping("create")
    public ResponseEntity<Order> createOrder(
                                             @RequestBody Order req,
                                             @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        Order order =orderService.createOrder(customer, req);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);

    }

    @PostMapping("buyNow")
    public ResponseEntity<EntityResponse<Order>> buyNow(@RequestBody OrderRequest req,
                                                 @RequestHeader("Authorization") String jwt) throws UserException {
       EntityResponse res = new EntityResponse<>();
        try{
            Order order = orderService.orderBuyNow(jwt, req);
            res.setData(order);
            res.setStatus(HttpStatus.CREATED);
            res.setCode(HttpStatus.CREATED.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());

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
//        Order order = orderService.findAllOrderItems(orderId);
        List<OrderRequest> order = orderService.findAllOrderItems(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
