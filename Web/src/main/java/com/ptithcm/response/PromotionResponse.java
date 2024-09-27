package com.ptithcm.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionResponse {
    private Long promotionId;
    private String promotionName;
    private BigDecimal discountValue;
    private String startDate;
    private String endDate;
    private String status;
    private String staffName;

}
