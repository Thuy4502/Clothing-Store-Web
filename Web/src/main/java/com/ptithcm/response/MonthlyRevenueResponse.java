package com.ptithcm.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonthlyRevenueResponse {
    private String month;
    private Double totalRevenue;

}
