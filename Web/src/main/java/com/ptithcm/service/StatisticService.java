package com.ptithcm.service;

import com.ptithcm.response.MonthlyRevenueResponse;
import com.ptithcm.response.TopSellingResponse;

import java.util.List;

public interface StatisticService {
    List<TopSellingResponse> getTopSellingProducts(String jwt);
    List<MonthlyRevenueResponse> getMonthlyRevenueByYear(String jwt, int year);
}
