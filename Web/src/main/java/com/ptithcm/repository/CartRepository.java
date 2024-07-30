package com.ptithcm.repository;
import com.ptithcm.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

        @Query("SELECT c FROM Cart c WHERE c.customer.customerId = :customerId")
        Cart findByCustomerId(@Param("customerId") Long customerId);

        @Query("SELECT c.totalItem, c.totalPrice, ci.cartItemId, ci.price, ci.productDetailId, ci.quantity, ci.size, " +
                "p.image, p.productName, b.brandId " +
                "FROM CartItem ci " +
                "JOIN ci.cart c " +
                "JOIN ci.productDetail pd " +
                "JOIN pd.product p " +
                "JOIN p.brand b " +
                "WHERE c.customer.customerId = :customerId")
        List<Object[]> findCartItemDetails(Long customerId);
}
