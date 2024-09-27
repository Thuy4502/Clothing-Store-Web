package com.ptithcm.controller;

import com.ptithcm.config.JwtTokenProvider;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Customer;
import com.ptithcm.model.Staff;
import com.ptithcm.model.User;
import com.ptithcm.request.ChangePasswordRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.StaffService;
import com.ptithcm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StaffService staffService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/profile")
    public ResponseEntity<EntityResponse> getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        EntityResponse res = new EntityResponse<>();
        User user = userService.findUserProfileByJwt(jwt);
        if(user.getRole().getRoleName().equals("CUSTOMER")){
            Customer customer = customerService.findCustomerByUserId(user.getUserId());
            res.setData(customer);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success get customer");
        }else if(user.getRole().getRoleName().equals("STAFF")){
            Staff staff = staffService.findStaffByUserId(user.getUserId());
            res.setData(staff);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success get customer");
        }
        if (jwt == null) {
            throw new UserException("JWT token is missing");
        }

        // Xác thực và lấy thông tin người dùng từ JWT
      return new ResponseEntity<>(res,res.getStatus());
    }

    @PutMapping("/profile/update")
    public ResponseEntity<EntityResponse> updateProfileByJwt(@RequestHeader("Authorization") String jwt, @RequestBody Customer customer){
        EntityResponse res = new EntityResponse();
        try{
            Customer update = customerService.updateCustomer(jwt,customer);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
            res.setData(update);
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("error " + e.getMessage());
            res.setData(null);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/change/password")
    public ResponseEntity<ApiResponse> changePasswordByJwt(@RequestHeader("Authorization") String jwt, @RequestBody ChangePasswordRequest rq){
        ApiResponse res = new ApiResponse();
        try{
            User update = userService.changePassword(jwt,rq);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
            res.setMessage("success");
        }catch (Exception e){
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
