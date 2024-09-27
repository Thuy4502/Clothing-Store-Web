package com.ptithcm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TopSellingResponse {
    private Long productId;
    private String productName;
    private int totalSold;
}
