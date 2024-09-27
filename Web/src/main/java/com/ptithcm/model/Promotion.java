package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="promotion_id")
    private Long promotionId;

    @Column(name="promotion_name")
    private String promotionName;

    @Column(name="status")
    private String status;

    @Column
    private Long staff_id;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "discount_value")

    private BigDecimal discountValue;


    @Column
    private int value_min;
    @Column
    private int value_max;

    @Column
    private String type;

    @Column(name = "end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name="staff_id",insertable = false,updatable = false)
    private Staff staff;

    @OneToMany(mappedBy = "promotion")
    private List<ProductPromotion> productPromotions;

}
