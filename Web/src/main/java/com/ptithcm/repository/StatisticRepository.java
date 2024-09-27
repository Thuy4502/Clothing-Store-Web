package com.ptithcm.repository;

import com.ptithcm.model.Brand;
import com.ptithcm.response.MetricCountResponse;
import com.ptithcm.response.TopSellingResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Brand, Long> {
    @Query(value = "SELECT p.product_id AS productId, " +
            "       p.product_name AS productName, " +
            "       COUNT(oi.order_item_id) AS totalSold " +
            "FROM order_item oi " +
            "JOIN product_detail pd ON pd.product_detail_id = oi.product_detail_id " +
            "JOIN product p ON p.product_id = pd.product_id " +
            "GROUP BY p.product_id, p.product_name " +
            "ORDER BY totalSold DESC " +
            "LIMIT 3", nativeQuery = true)
    List<Object[]> findTopSellingProducts();

    @Query("SELECT FUNCTION('DATE_FORMAT', o.orderDate, '%m') AS month, " +
            "SUM(oi.quantity * oi.price) AS totalRevenue " +
            "FROM Order o " +
            "JOIN o.orderItems oi " +
            "WHERE FUNCTION('YEAR', o.orderDate) = :year " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.orderDate, '%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', o.orderDate, '%m')")
    List<Object[]> findMonthlyRevenueByYear(int year);



}
