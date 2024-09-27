package com.ptithcm.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PromotionRequest {

    private String promotionName;
    private List<Long> productId;
    private Long promotionId;
    private BigDecimal discountValue;
    private Date startDate;
    private Date endDate;
    private String type;
    private int value_min;
    private int value_max;
}
