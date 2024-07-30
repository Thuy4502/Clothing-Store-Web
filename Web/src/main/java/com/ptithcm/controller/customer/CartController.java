package com.ptithcm.controller.customer;

import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.repository.CartRepository;
import com.ptithcm.request.AddCartItemRequest;
import com.ptithcm.request.CartRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.CartItemService;
import com.ptithcm.service.CartService;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final  UserService userService;
    private final CartItemService cartItemService;
    private final CustomerService customerService;

    public CartController(CartService cartService, UserService userService, CartItemService cartItemService , CustomerService customerService) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.customerService = customerService;
    }

    @PostMapping("/addItem")
    public ResponseEntity<ApiResponse> addItem(@RequestBody AddCartItemRequest req, @RequestHeader ("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        cartService.addCartItem(customer.getCustomerId(), req);
        ApiResponse response = new ApiResponse();
        response.setMessage("Item is added to cart");
        response.setStatus(HttpStatus.OK);
        response.setCode(HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getCart")
    public ResponseEntity<EntityResponse> getCustomerCart(@RequestHeader("Authorization") String jwt) {
        try {
            // Verify and get user from JWT
            User user = userService.findUserProfileByJwt(jwt);

            // Find customer using user ID (optional based on your logic)
            Customer customer = customerService.findCustomerByUserId(user.getUserId());

            // Find cart for the customer
            List<CartRequest> cart = cartService.findAllCartCustomer(customer.getCustomerId());

            EntityResponse response = new EntityResponse();
            response.setMessage("Cart retrieved successfully");
            response.setStatus(HttpStatus.OK);
            response.setCode(HttpStatus.OK.value());
            response.setData(cart);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (UserException e) {
            // Handle specific exceptions and provide meaningful responses
            EntityResponse response = new EntityResponse();
            response.setMessage(e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            EntityResponse response = new EntityResponse();
            response.setMessage("An unexpected error occurred " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCartItem/{cartItemId}")
    public ResponseEntity<EntityResponse> updateCartItem(@RequestBody CartItem req,
                                                         @RequestHeader ("Authorization") String jwt,  @PathVariable Long cartItemId)
            throws Exception {
        try{
            User user = userService.findUserProfileByJwt(jwt);
            Customer customer = customerService.findCustomerByUserId(user.getUserId());
            CartItem updatedCartItem= cartItemService.updateCartItem(customer.getCustomerId(), cartItemId, req);
            EntityResponse response = new EntityResponse();
            response.setMessage("Update cart item successfully");
            response.setStatus(HttpStatus.OK);
            response.setCode(HttpStatus.OK.value());
            response.setData(updatedCartItem);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch ( Exception e) {
            EntityResponse response = new EntityResponse();
            response.setMessage("An unexpected error occurred " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeCartItem/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long cartItemId) throws UserException, ProductException {
        try{
            User user = userService.findUserProfileByJwt(jwt);
            Customer customer = customerService.findCustomerByUserId(user.getUserId());
            cartItemService.removeCartItem(customer.getCustomerId(), cartItemId);
            ApiResponse response = new ApiResponse();
            response.setMessage("Remove item successfully");
            response.setStatus(HttpStatus.OK);
            response.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch ( Exception e) {
            ApiResponse response = new ApiResponse();
            response.setMessage("An unexpected error occurred " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
