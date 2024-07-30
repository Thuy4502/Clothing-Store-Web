package com.ptithcm.controller.staff;

import com.ptithcm.dto.ProductRequestDTO;
import com.ptithcm.exception.ProductException;
import com.ptithcm.model.Product;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("staff/product")
public class StaffProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Product>> createProduct(
            @RequestBody ProductRequestDTO productRequest,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Xử lý token
        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : null;
        if (token == null) {
            // Tạo phản hồi khi token không có
            EntityResponse response = new EntityResponse();
            response.setMessage("Unauthorized: Token is missing");
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }

        try {
            // Gọi dịch vụ để tạo sản phẩm
            Product createdProduct = productService.createProduct(productRequest);

            // Tạo phản hồi thành công với thông tin sản phẩm
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(createdProduct);
            response.setMessage("Product created successfully");
            response.setCode(HttpStatus.CREATED.value());
            response.setStatus(HttpStatus.CREATED);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            // Tạo phản hồi thất bại với thông báo lỗi
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(null);
            response.setMessage("Error creating product: " + e.getMessage());
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/update/{productId}")
    public ResponseEntity<EntityResponse<Product>> updateProduct(
            @RequestBody ProductRequestDTO productRequest,
            @PathVariable("productId") Long productId,
            @RequestHeader("Authorization") String authorizationHeader) {

        // Xử lý token
        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : null;
        if (token == null) {
            // Tạo phản hồi khi token không có
            EntityResponse<Product> response = new EntityResponse<>();
            response.setMessage("Unauthorized: Token is missing");
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            // Gọi dịch vụ để cập nhật sản phẩm
            Product updatedProduct = productService.updateProduct(productId, productRequest);

            // Tạo phản hồi thành công với thông tin sản phẩm
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(updatedProduct);
            response.setMessage("Product updated successfully");
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Tạo phản hồi thất bại với thông báo lỗi
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(null);
            response.setMessage("Error updating product: " + e.getMessage());
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


}
