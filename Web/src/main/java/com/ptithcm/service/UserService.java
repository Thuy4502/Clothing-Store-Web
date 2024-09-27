package com.ptithcm.service;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.User;
import com.ptithcm.request.ChangePasswordRequest;
import com.ptithcm.request.SignUpRequest;

import javax.mail.MessagingException;

public interface UserService {
    public User findUserById(Long userId) throws Exception;
    public User findUserProfileByJwt(String jwt) throws UserException;
    User createUser(SignUpRequest user) throws Exception;
    User updatePassword(String passWord,String email) throws Exception;
    String sendMail(String email,String subject , String content, String otp) throws MessagingException;
    User changePassword(String jwt, ChangePasswordRequest rq) throws Exception;
}
