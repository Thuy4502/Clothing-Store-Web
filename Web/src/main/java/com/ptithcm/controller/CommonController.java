package com.ptithcm.controller;

import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Order;
import com.ptithcm.model.Product;
import com.ptithcm.model.Promotion;
import com.ptithcm.model.Review;
import com.ptithcm.response.ActivePromoResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.OrderService;
import com.ptithcm.service.ProductService;
import com.ptithcm.service.PromotionService;
import com.ptithcm.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("all")
public class CommonController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private ReviewService reviewService;


    @GetMapping("/product/getAll")
    public ResponseEntity<Page<Product>> getAll(
            @RequestParam(required = false, defaultValue = "") String category,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "price_low") String sort,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) throws ProductException {
           Page<Product> res=productService.getAllProducts(category, colors, gender, sizes, minPrice, maxPrice, sort, pageNumber, pageSize);
           return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }
    @GetMapping("product/id/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }


    @PutMapping("order/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt) throws Exception{
        Order order = orderService.canceledOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("promtion/active")
    public ResponseEntity<EntityResponse<List<ActivePromoResponse>>> getActivePromotion() {
        EntityResponse res = new EntityResponse<>();

        try{
            List<Promotion> activePromotions = promotionService.getActivePromotionsWithDiscount();
            res.setData(activePromotions);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());

    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<EntityResponse<List<Review>>> getProductReview(@PathVariable Long productId) {
        EntityResponse<List<Review>> res = new EntityResponse<>();

        if (reviewService != null) {
            try {
                List<Review> reviews = reviewService.getProductsReview(productId);
                res.setData(reviews);
                res.setStatus(HttpStatus.OK);
                res.setCode(HttpStatus.OK.value());
                res.setMessage("Reviews fetched successfully");
                return new ResponseEntity<>(res, HttpStatus.OK);
            } catch (Exception e) {
                // Handle any exceptions that might occur and set error response
                res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
                res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                res.setMessage("Error fetching reviews: " + e.getMessage());
                return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Return an error response if reviewService is not initialized
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setMessage("ReviewService is not initialized");
            return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Promotion>> getAllOrders(@RequestHeader("Authorization") String authorizationHeader) throws UserException {
        List<Promotion> promotions = promotionService.getAllPromotions(authorizationHeader);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }



}
