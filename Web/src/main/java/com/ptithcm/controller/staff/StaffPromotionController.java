package com.ptithcm.controller.staff;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.request.PromotionRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.PromotionResponse;
import com.ptithcm.service.ProductPromotionService;
import com.ptithcm.service.PromotionService;
import com.ptithcm.service.StaffService;
import com.ptithcm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/promotion")
public class StaffPromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private UserService userService;
    @Autowired
    private StaffService staffService;

    private final  ProductPromotionService productPromotionService;

    public StaffPromotionController(ProductPromotionService productPromotionService) {
        this.productPromotionService = productPromotionService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addPromotionByStaff(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PromotionRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            Promotion create = promotionService.CreatePromotion(rq,authorizationHeader);
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

    @PostMapping("/add/detail")
    public ResponseEntity<ApiResponse> addPromotionProductByStaff(@RequestHeader("Authorization") String authorizationHeader, @RequestBody PromotionRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            List<ProductPromotion> create = productPromotionService.createProductPromotion(rq);
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



    @GetMapping("/getAll")
    public ResponseEntity<List<Promotion>> getAllOrders(@RequestHeader("Authorization") String authorizationHeader) throws UserException {
        List<Promotion> promotions = promotionService.getAllPromotions(authorizationHeader);
        return new ResponseEntity<>(promotions, HttpStatus.OK);
    }

}
