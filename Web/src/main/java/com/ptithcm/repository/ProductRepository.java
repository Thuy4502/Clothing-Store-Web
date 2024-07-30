package com.ptithcm.repository;

import com.ptithcm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:category IS NULL OR :category = '' OR p.category.categoryId = :category) " +
            "AND (:colors IS NULL OR :colors = '' OR p.color IN :colors) " +
            "AND (:gender IS NULL OR :gender = '' OR p.gender = :gender) " +
            "AND (:minPrice IS NULL OR p.currentPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.currentPrice <= :maxPrice) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'price_low' THEN p.currentPrice END ASC, " +
            "CASE WHEN :sort = 'price_high' THEN p.currentPrice END DESC")
    List<Product> filterProducts(@Param("category") String category,
                                 @Param("colors") String colors,
                                 @Param("gender") String gender,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice,
                                 @Param("sort") String sort);


}
