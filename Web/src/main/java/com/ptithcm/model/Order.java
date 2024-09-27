package com.ptithcm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @Column(name="order_date")
    private LocalDateTime orderDate;

    @Column(name="total_item")
    private int totalItem;

    @Column(name="total_amount")
    private double totalAmount;

    @Column(name="status")
    private String status;

    @Column(name="customer_name")
    private String customerName;

    @Column(name="customer_email")
    private String customerEmail;

    @Column(name="customer_phone")
    private String customerPhone;

    @Column(name="customer_address")
    private String customerAddress;

    @Column
    private Long accept_by;

    @Column
    private Long customer_id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="accept_by",insertable=false,updatable=false)
    private Staff acceptedBy;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="customer_id",insertable=false,updatable=false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @JsonIgnore
    @OneToOne(mappedBy = "orders")
    private Bill bill;
}
