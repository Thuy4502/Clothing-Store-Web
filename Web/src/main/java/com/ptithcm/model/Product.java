package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "image")
    private String image;
    private String description;
    private String gender;
    private String color;
    private String material;


    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "updated_by")
    private Long updateBy;
    @ManyToOne
    @JoinColumn(name = "updated_by", insertable = false, updatable = false)
    private Staff staff;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    private Staff createdBy;

//    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> productDetails;


    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Review> review;

//    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductPromotion> promotions;


}
