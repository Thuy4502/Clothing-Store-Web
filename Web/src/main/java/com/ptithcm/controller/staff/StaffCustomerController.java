package com.ptithcm.controller.staff;

import com.ptithcm.model.CartItem;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Promotion;
import com.ptithcm.model.User;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("staff/customer")
public class StaffCustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAll")
    public ResponseEntity<EntityResponse> getAllCustomer(@RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse res = new EntityResponse();
        try{
            List<Customer> customers = customerService.getAllCustomer(jwt);
            res.setData(customers);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        }catch (Exception e){
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res,res.getStatus());
    }
}
