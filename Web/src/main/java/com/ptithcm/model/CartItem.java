package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @Column(name = "product_detail_id", nullable = false)
    private Long productDetailId;

    private String size;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column
    private String color;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", insertable = false, updatable = false)
    private ProductDetail productDetail;


}
