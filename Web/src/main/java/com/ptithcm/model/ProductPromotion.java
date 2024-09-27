package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_promotions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_promotion_id")
    private Long productPromotionId;

    @Column
    private Long product_id;

    @Column
    private  Long promotion_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id",insertable = false,updatable = false)
    private Product product;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "promotion_id",insertable = false,updatable = false)
    private Promotion promotion;








}
