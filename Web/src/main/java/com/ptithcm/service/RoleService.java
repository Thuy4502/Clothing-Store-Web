package com.ptithcm.service;


import com.ptithcm.model.Role;

public interface RoleService {
    Role findById(Long id) throws Exception;
    Role findByName(String role_name);
}
