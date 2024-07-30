package com.ptithcm.repository;


import com.ptithcm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE username = ?1 ", nativeQuery = true)
    User findByUsername(String username);
}

