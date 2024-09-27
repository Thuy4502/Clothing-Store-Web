package com.ptithcm.service.impl;

import com.ptithcm.model.Role;
import com.ptithcm.model.User;
import com.ptithcm.repository.UserRepository;
import com.ptithcm.service.RoleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomeUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;


    public CustomeUserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        String roleName = "";
        try {
            Role role = roleService.findById(user.getRoleId());
            roleName = role.getRoleName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(roleName));
        System.err.println(roleName);
        System.err.println(authorities);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
