package com.ptithcm.service.impl;

import com.ptithcm.config.JwtTokenProvider;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.repository.*;
import com.ptithcm.request.ChangePasswordRequest;
import com.ptithcm.request.SignUpRequest;
import com.ptithcm.service.MailService;
import com.ptithcm.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepo;
    private final StaffRepository staffRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;
    private final CustomerRepository customerRepo;
    private final CartRepository cartRepo;
    private final MailService emailService;
    private String otpAccept = "";
    public UserServiceImpl(JwtTokenProvider jwtTokenProvider, UserRepository userRepo, StaffRepository staffRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo, CustomerRepository customerRepo, CartRepository cartRepo, MailService emailService) {

        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
        this.staffRepo = staffRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.customerRepo = customerRepo;
        this.cartRepo = cartRepo;
        this.emailService = emailService;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User>user= userRepo.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id: "+ userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String username = jwtTokenProvider.getUsernameFromJwtToken(jwt);
        User user=userRepo.findByUsername(username);

        if (user == null) {
            throw new UserException("User not found with name: "+ username);
        }
        return user;
    }

    @Override
    @Transactional
    public User createUser(SignUpRequest rq) throws Exception {
            User user = new User();
            Role role = roleRepo.findByName(rq.getRole_name());
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus("Active");
            user.setUpdated_at(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(rq.getPassword()));
            user.setRoleId(role.getRoleId());
            user.setUsername(rq.getUsername());
            User saveUser = userRepo.save(user);
            if (saveUser != null) {
                if (role.getRoleName().equals("CUSTOMER")) {
                    Customer customer = new Customer();
                    customer.setCreated_at(LocalDateTime.now());
                    customer.setEmail(rq.getEmail());
                    customer.setUser_id(saveUser.getUserId());
                    customer.setFirstName(rq.getFirstname());
                    customer.setLastName(rq.getLastname());
                    customer.setUpdated_at(LocalDateTime.now());
                    Customer saveCustomer = customerRepo.save(customer);
                    if (saveCustomer != null) {
                        Cart cart = new Cart();
                        cart.setTotalItem(0);
                        cart.setTotalPrice(0);
                        cart.setUpdatedAt(LocalDateTime.now());
                        cart.setCustomer_id(saveCustomer.getCustomerId());
                        Cart saveCart = cartRepo.save(cart);
                    }
                } else if (role.getRoleName().equals("STAFF")) {
                    Staff staff = new Staff();
                    staff.setUser_id(saveUser.getUserId());
                    staff.setHiredDate(LocalDate.now());
                    staff.setEmail(rq.getEmail());
                    staff.setFirstName(rq.getFirstname());
                    staff.setLastName(rq.getLastname());
                    Staff saveStaff = staffRepo.save(staff);
                    userRepo.save(user);
                }
            }
            return saveUser;
    }

    @Override
    @Transactional
    public User updatePassword(String passWord,String email ) throws Exception {
        Customer customer = customerRepo.findByEmail(email);
        User update = findUserById(customer.getUser_id());
        update.setPassword(passwordEncoder.encode(passWord));
        update.setUpdated_at(LocalDateTime.now());
        User save = userRepo.save(update);
        if(save != null){
            return save;
        }
        throw new Exception("update fail");

    }

    @Override
    public String sendMail(String email, String subject, String content, String otp) throws MessagingException {
        emailService.sendMail(email, subject,content);
        otpAccept = otp;
        return otpAccept;
    }

    @Override
    public User changePassword(String jwt, ChangePasswordRequest rq) throws Exception {
        User user = findUserProfileByJwt(jwt);
        if(passwordEncoder.matches(rq.getPassword(),user.getPassword())){
            user.setPassword(passwordEncoder.encode(rq.getNewPassword()));
            user.setUpdated_at(LocalDateTime.now());
            return userRepo.save(user);
        }
        throw new Exception("Password is incorrect");
    }

}
