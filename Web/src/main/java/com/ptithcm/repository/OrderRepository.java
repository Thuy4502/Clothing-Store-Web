package com.ptithcm.repository;

import com.ptithcm.model.Order;
import com.ptithcm.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.customer.customerId=:customerId")
    List<Order> getUserOrders(@Param("customerId") Long customerId);

//    @Query("SELECT oi.price, oi.quantity, oi.size, p.color, p.image, b.brand_name FROM " +
//            "order_item oi" +
//            "JOIN productDetail pd ON  pd.product_detail_id = oi.product_detail_id" +
//            "JOIN product p ON pd.product_id = p.product_id" +
//            "JOIN orders o ON o.order_id = oi.order_id" +
//            "JOIN brand b ON p.brand_id = b.brand_id" +
//            "WHERE oi.order_id = 22")
//    List<OrderItem> getAllOrderItems(Long orderItem);


    @Query("SELECT o.totalAmount, o.totalItem, o.customerName, o.customerAddress, o.customerPhone, o.customerEmail, o.status, p.productId, oi.orderItemId,  " +
            " p.productName, oi.price, oi.quantity, oi.size, oi.reviewStatus, p.color, p.image, b.brandName " +
            "FROM OrderItem oi " +
            "JOIN oi.productDetail pd " +
            "JOIN pd.product p " +
            "JOIN p.brand b " +
            "JOIN oi.order o " +
            "WHERE o.orderId = :orderId")
    List<Object[]> findAllOrderItem(@Param("orderId") Long orderId);


}
