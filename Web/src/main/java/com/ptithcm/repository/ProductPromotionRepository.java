package com.ptithcm.repository;

import com.ptithcm.model.ProductPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPromotionRepository extends JpaRepository<ProductPromotion,Long> {
}
