package com.ptithcm.controller.customer;

import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Review;
import com.ptithcm.model.User;
import com.ptithcm.request.ReviewRequest;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.ReviewService;
import com.ptithcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.UncategorizedScriptException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/review")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        Review review=reviewService.createReview(req, customer);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getProductsReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
