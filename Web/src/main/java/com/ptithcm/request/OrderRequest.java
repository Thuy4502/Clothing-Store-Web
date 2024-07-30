package com.ptithcm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequest {
    private double totalAmount;
    private int totalItem;
    private String customerPhone;
    private String customerEmail;
    private String customerName;
    private String customerAddress;
    private String status;
    private Long productId;
    private Long orderItemId;
    private String productName;
    private Double price;
    private int quantity;
    private String size;
    private String reviewStatus;
    private String color;
    private String image;
    private String brand_name;
}
