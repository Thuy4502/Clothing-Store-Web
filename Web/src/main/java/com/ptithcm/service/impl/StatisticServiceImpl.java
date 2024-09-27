package com.ptithcm.service.impl;

import com.ptithcm.repository.ProductRepository;
import com.ptithcm.repository.StaffRepository;
import com.ptithcm.repository.StatisticRepository;
import com.ptithcm.response.MonthlyRevenueResponse;
import com.ptithcm.response.TopSellingResponse;
import com.ptithcm.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    private StatisticRepository statisticRepository;

    @Override
    public List<TopSellingResponse> getTopSellingProducts(String jwt) {
        // Gọi repository để lấy dữ liệu
        List<Object[]> results = statisticRepository.findTopSellingProducts();

        // Chuyển đổi dữ liệu từ Object[] sang TopSellingResponse
        List<TopSellingResponse> topSellingProducts = results.stream()
                .map(result -> {
                    try {
                        Long productId = ((Number) result[0]).longValue();  // productId
                        String productName = (String) result[1];            // productName
                        int totalSold = ((Number) result[2]).intValue();    // totalSold

                        return new TopSellingResponse(
                                productId,
                                productName,
                                totalSold
                        );
                    } catch (ClassCastException e) {
                        throw new RuntimeException("Data type mismatch in query result", e);
                    }
                })
                .collect(Collectors.toList());

        return topSellingProducts;
    }

    @Override
    public List<MonthlyRevenueResponse> getMonthlyRevenueByYear(String jwt, int year) {
        List<Object[]> results = statisticRepository.findMonthlyRevenueByYear(year);

        // Chuyển đổi dữ liệu từ Object[] sang MonthlyRevenueResponse
        return results.stream()
                .map(result -> {
                    try {
                        String month = (String) result[0];  // month
                        Double totalRevenue = ((Number) result[1]).doubleValue();  // totalRevenue

                        return new MonthlyRevenueResponse(month, totalRevenue);
                    } catch (ClassCastException e) {
                        throw new RuntimeException("Data type mismatch in query result", e);
                    }
                })
                .collect(Collectors.toList());
    }
    }

