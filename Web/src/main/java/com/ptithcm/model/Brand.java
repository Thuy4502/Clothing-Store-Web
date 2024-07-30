package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="brand_id")
    private Long brandId;

    @Column(name="brand_name")
    private String brandName;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column
    private Long created_by;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false,updatable = false)
    private Staff createdBy;

    @JsonIgnore
    @OneToMany(mappedBy = "brand")
    private List<Product> products;



}
