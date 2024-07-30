package com.ptithcm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartRequest {
    private Integer totalItem;
    private double totalPrice;
    private Long cartItemId;
    private double price;
    private Long productDetailId;
    private Integer quantity;
    private String size;
    private String image;
    private String productName;
    private Long brandId;
}
