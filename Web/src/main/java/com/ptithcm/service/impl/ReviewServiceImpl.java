package com.ptithcm.service.impl;

import com.ptithcm.exception.ProductException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.OrderItem;
import com.ptithcm.model.Product;
import com.ptithcm.model.Review;
import com.ptithcm.repository.ReviewRepository;
import com.ptithcm.request.ReviewRequest;
import com.ptithcm.service.OrderItemService;
import com.ptithcm.service.OrderService;
import com.ptithcm.service.ProductService;
import com.ptithcm.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private ProductService productService;
    private OrderItemService orderItemService;

    @Override
    public Review createReview(ReviewRequest req, Customer customer) throws ProductException {
        Product product = productService.findProductById(req.getProductId());
        OrderItem orderItem = orderItemService.findOrderItemById(req.getOrderItemId()); // Ensure you have a service method for this

        Review review = new Review();
//        review.setProduct(product);
        review.setProductId(product.getProductId());
        review.setCustomer(customer);
//        review.setCreatedBy(customer.getCustomerId());
        review.setStatus(req.getStatus());
        review.setStar(req.getStar());
        review.setContent(req.getContent());
        review.setCreateAt(LocalDateTime.now());
        review.setOrderItem(orderItem);
        orderItem.setReviewStatus("REVIEWED");// Set the OrderItem object

        return reviewRepository.save(review);

    }

    @Override
    public List<Review> getProductsReview(Long productId) {

        return reviewRepository.getAllProductReview(productId);
    }
}
