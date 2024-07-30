package com.ptithcm.service;

import com.ptithcm.exception.ProductException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Review;
import com.ptithcm.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, Customer customer) throws ProductException;
    public List<Review> getProductsReview(Long productId);

}
