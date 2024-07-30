package com.ptithcm.controller;

import com.ptithcm.config.JwtTokenProvider;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.User;
import com.ptithcm.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/profile")
    public User getUserProfile(@RequestHeader("Authorization") String jwt) throws UserException {
        // Lấy JWT từ header Authorization
        if (jwt == null) {
            throw new UserException("JWT token is missing");
        }

        // Xác thực và lấy thông tin người dùng từ JWT
        return userService.findUserProfileByJwt(jwt);
    }
}
