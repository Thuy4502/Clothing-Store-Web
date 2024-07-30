package com.ptithcm.repository;


import com.ptithcm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query(value = "SELECT * FROM role WHERE role_name = ?1  ", nativeQuery = true)
    Role findByName(String role_name);
}
