package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "star", nullable = false)
    private double star;

    @Column(name = "content")
    private String content;

    @Column
    private String status;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_item_id", nullable = false, unique = true)
    private OrderItem orderItem;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false) // Ánh xạ với Customer
    private Customer customer;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false) // Ánh xạ với Product
    private Product product;
}


