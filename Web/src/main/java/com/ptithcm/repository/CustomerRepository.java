package com.ptithcm.repository;

import com.ptithcm.model.Customer;
import com.ptithcm.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer WHERE email = ?1  ", nativeQuery = true)
    Customer findByEmail(String email);

    @Query(value = "SELECT * FROM Customer WHERE user_id = ?1  ", nativeQuery = true)
    Customer findCustomerByUserId(Long userId);
}
