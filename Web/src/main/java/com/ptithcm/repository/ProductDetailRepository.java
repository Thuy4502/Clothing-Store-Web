package com.ptithcm.repository;

import com.ptithcm.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.productId = :productId")
    List<ProductDetail> findProductDetailByProductId(@Param("productId") Long productId);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.productId = :productId and pd.size = :size")
    ProductDetail findProductDetailBySizeNameAndProductId(@Param("productId") long productId, @Param("size")  String size);
}
