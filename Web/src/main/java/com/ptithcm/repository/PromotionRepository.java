package com.ptithcm.repository;

import com.ptithcm.model.Promotion;
import com.ptithcm.response.PromotionResponse; // Sử dụng PromotionResponse đúng package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query(value = "SELECT DISTINCT p.promotion_id, p.promotion_name, p.status, p.discount_value, p.start_date, p.end_date, " +
            "CONCAT(s.first_name, ' ', s.last_name) AS StaffName " +
            "FROM promotion p " +
//            "JOIN product_promotions pp ON p.promotion_id = pp.promotion_id " +
            "JOIN staff s ON p.staff_id = s.staff_id", nativeQuery = true)
    List<Object[]> getAllPromotions();


    @Query("SELECT p.promotionName " +
            "FROM Promotion p " +
            "JOIN ProductPromotion pp ON pp.promotion.promotionId = p.promotionId " +
            "WHERE p.status = 'Active'")
    List<Object[]> findActivePromotionsWithDiscount();

    List<Promotion> findByStatus(String status);



}
