package com.ptithcm.controller.customer;

import com.ptithcm.response.ApiResponse;
import com.ptithcm.response.EntityResponse;
import com.ptithcm.response.MonthlyRevenueResponse;
import com.ptithcm.response.TopSellingResponse;
import com.ptithcm.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("staff/statistic/")
public class StatisticController {
    @Autowired
    private StatisticService statisticService;

    @GetMapping("/topSelling")
    public ResponseEntity<EntityResponse> getTopSellingProducts(
            @RequestHeader("Authorization") String authorizationHeader) {

        EntityResponse response = new EntityResponse();
        try {
            List<TopSellingResponse> topSellingProducts = statisticService.getTopSellingProducts(authorizationHeader);
            response.setData(topSellingProducts);
            response.setMessage("Top selling products retrieved successfully");
            response.setStatus(HttpStatus.OK);
            response.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to retrieve top selling products" + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/monthlyRevenue/{year}")
    public ResponseEntity<EntityResponse> getMonthlyRevenueByYear(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable(value = "year") Integer year) {

        EntityResponse response = new EntityResponse();
        try {
            List<MonthlyRevenueResponse> monthlyRevenue = statisticService.getMonthlyRevenueByYear(authorizationHeader, year);
            response.setData(monthlyRevenue);
            response.setMessage("Monthly revenue retrieved successfully");
            response.setStatus(HttpStatus.OK);
            response.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to retrieve monthly revenue: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
