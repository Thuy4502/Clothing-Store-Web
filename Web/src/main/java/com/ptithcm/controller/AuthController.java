package com.ptithcm.controller;

import com.ptithcm.config.JwtTokenProvider;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Role;
import com.ptithcm.model.User;
import com.ptithcm.repository.UserRepository;
import com.ptithcm.request.LoginRequest;
import com.ptithcm.request.SignUpRequest;
import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.AuthResponse;
import com.ptithcm.service.CustomerService;
import com.ptithcm.service.RoleService;
import com.ptithcm.service.UserService;
import com.ptithcm.service.impl.CustomeUserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ptithcm.function.GenerateOtp.generateOTP;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final CustomeUserServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomerService customerService;

    public AuthController(UserService userService, CustomeUserServiceImpl userDetailsService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, CustomerService customerService) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody SignUpRequest rq){
        ApiResponse res = new ApiResponse();
        HttpStatus httpStatus = null;
        if(!rq.getEmail().equals("") && !rq.getLastname().equals("") && !rq.getFirstname().equals("") && !rq.getPassword().equals("") && !rq.getUsername().equals("") && !rq.getRole_name().equals("")) {
            try{
                User user = userService.createUser(rq);
                res.setCode(HttpStatus.CREATED.value());
                res.setMessage("Create user success");
                res.setStatus(HttpStatus.CREATED);
                httpStatus = HttpStatus.CREATED;
            }catch (Exception e){
                System.out.println("error" + e.getMessage());
                res.setCode(HttpStatus.CONFLICT.value());
                res.setStatus(HttpStatus.CONFLICT);
                res.setMessage("error" + e.getMessage());
                httpStatus = HttpStatus.CONFLICT;
            }
        }else {
            httpStatus = HttpStatus.CONFLICT;
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("No blank characters allowed");
            res.setStatus(httpStatus);
        }
        return new ResponseEntity<>(res,httpStatus);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandle(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateAccessToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "Signin successful");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);

    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
    @PostMapping("forgot-password")
    public ResponseEntity<ApiResponse> forgetPassword(@RequestBody SignUpRequest rq) throws Exception {
        ApiResponse res = new ApiResponse();
        String otp = generateOTP();
        String subject = "RESET PASSWORD CLOTHING STORE";
        String content = "<!DOCTYPE html>"
                + "<html lang=\"vi\">"
                + "<head>"
                + "    <meta charset=\"UTF-8\">"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "    <title>Khôi Phục Mật Khẩu</title>"
                + "    <style>"
                + "        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + "        .container { width: 100%; max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 5px; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
                + "        .header { text-align: center; margin-bottom: 20px; }"
                + "        .header h1 { color: #333333; font-size: 24px; margin: 0; }"
                + "        .content { font-size: 16px; color: #333333; line-height: 1.5; }"
                + "        .otp-code { display: block; font-size: 24px; font-weight: bold; color: #007bff; text-align: center; margin: 20px 0; }"
                + "        .footer { font-size: 14px; color: #888888; text-align: center; margin-top: 20px; }"
                + "        .footer a { color: #007bff; text-decoration: none; }"
                + "        .footer a:hover { text-decoration: underline; }"
                + "    </style>"
                + "</head>"
                + "<body>"
                + "    <div class=\"container\">"
                + "        <div class=\"header\">"
                + "            <h1>Khôi Phục Mật Khẩu</h1>"
                + "        </div>"
                + "        <div class=\"content\">"
                + "            <p>Chào <strong>" + rq.getEmail() + "</strong>,</p>"
                + "            <p>Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu từ bạn. Dưới đây là mật khẩu mới của bạn. Vui lòng đăng nhập bằng mã bên dưới</p>"
                + "            <span class=\"otp-code\">" + otp + "</span>"
                + "            <p>Nếu bạn gặp bất kỳ vấn đề nào hoặc cần hỗ trợ thêm, vui lòng liên hệ với bộ phận chăm sóc khách hàng của chúng tôi qua địa chỉ emai dưới đây hoặc số điện thoại dưới đây <strong>0363000451</strong>.</p>"
                + "        </div>"
                + "        <div class=\"footer\">"
                + "            <p>Trân trọng,</p>"
                + "            <p><strong>CLOTHING STORE</strong><br>"
                + "            <a href=\"mailto:thithuytran74@gmail.com\">thithuytran74@gmail.com</a><br>"
                + "            <a href=\"#\">CLOTHING STORE</a></p>"
                + "        </div>"
                + "    </div>"
                + "</body>"
                + "</html>";

        boolean checkEmail = customerService.checkEmailExist(rq.getEmail());

        if (checkEmail) {
            userService.sendMail(rq.getEmail(), subject, content, otp);
            userService.updatePassword(otp, rq.getEmail());
            res.setMessage("success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }else{
            res.setMessage("Email not exist");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        }

        return new ResponseEntity<>(res, res.getStatus());
    }
}
