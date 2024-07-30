package com.ptithcm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddCartItemRequest {
    private Long productId;
    private String size;
    private int quantity;
    private double price;

}
