package com.ptithcm.service.impl;


import com.ptithcm.model.Role;
import com.ptithcm.repository.RoleRepository;
import com.ptithcm.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) throws Exception {
        Optional<Role> find = roleRepository.findById(id);
        if(find.isPresent()) {
            return find.get();
        }
        throw new Exception("not found role with id " + id);
    }

    @Override
    public Role findByName(String role_name) {
        return roleRepository.findByName(role_name);
    }
}
