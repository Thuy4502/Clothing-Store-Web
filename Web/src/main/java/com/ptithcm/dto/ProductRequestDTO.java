package com.ptithcm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequestDTO {
    private String productName;
    private String image;
    private String description;
    private String gender;
    private String color;
    private String material;
    private Double currentPrice;
    private String status;
    private Long createdById;
    private Long brandId;
    private Long categoryId;
    private List<ProductDetailDTO> productDetails;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductDetailDTO {
        private String size;
        private Integer quantity;
    }
}
