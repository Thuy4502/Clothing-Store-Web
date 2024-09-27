package com.ptithcm.repository;

import com.ptithcm.model.ProductDetail;
import com.ptithcm.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s WHERE s.user.id = :userId")
    Staff findStaffByUserId(@Param("userId") Long userId);
}
