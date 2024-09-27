package com.ptithcm.controller.staff;

import com.ptithcm.dto.ProductRequestDTO;
import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Product;
import com.ptithcm.model.Staff;
import com.ptithcm.model.User;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.ProductService;
import com.ptithcm.service.StaffService;
import com.ptithcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("staff/product")
public class StaffProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;

    @PostMapping("/add")
    public ResponseEntity<EntityResponse<Product>> createProduct(
            @RequestBody ProductRequestDTO productRequest,
            @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : null;
        if (token == null) {
            EntityResponse response = new EntityResponse();
            response.setMessage("Unauthorized: Token is missing");
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }

        try {
            Product createdProduct = productService.createProduct(productRequest);
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(createdProduct);
            response.setMessage("Product created successfully");
            response.setCode(HttpStatus.CREATED.value());
            response.setStatus(HttpStatus.CREATED);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
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

        String token = authorizationHeader.startsWith("Bearer ") ? authorizationHeader.substring(7) : null;
        if (token == null) {
            EntityResponse<Product> response = new EntityResponse<>();
            response.setMessage("Unauthorized: Token is missing");
            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setStatus(HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            Product updatedProduct = productService.updateProduct(productId, productRequest);
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(updatedProduct);
            response.setMessage("Product updated successfully");
            response.setCode(HttpStatus.OK.value());
            response.setStatus(HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            EntityResponse<Product> response = new EntityResponse<>();
            response.setData(null);
            response.setMessage("Error updating product: " + e.getMessage());
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId,
                                                            @RequestHeader("Authorization") String jwt) throws ProductException, UserException, UserException {
        System.err.println("Delete product controller ....");
        User user = userService.findUserProfileByJwt(jwt);
        Staff staff = staffService.findStaffByUserId(user.getUserId());
        System.err.println("Xoa nguoi dung " + staff.getStaffId());
        String msg = productService.deleteProduct(productId, staff.getStaffId());
        System.err.println("delete product controller .... msg " + msg);

        ApiResponse res = new ApiResponse();
        res.setCode(HttpStatus.CREATED.value());
        res.setMessage(msg);
        res.setStatus(HttpStatus.OK);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


}
