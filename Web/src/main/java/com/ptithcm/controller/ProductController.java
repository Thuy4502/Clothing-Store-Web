package com.ptithcm.controller;

import com.ptithcm.exception.ProductException;
import com.ptithcm.model.Product;
import com.ptithcm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
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
    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }



}
