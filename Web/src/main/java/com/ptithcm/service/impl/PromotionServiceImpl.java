package com.ptithcm.service.impl;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.repository.ProductPromotionRepository;
import com.ptithcm.repository.ProductRepository;
import com.ptithcm.repository.PromotionRepository;
import com.ptithcm.request.OrderRequest;
import com.ptithcm.request.PromotionRequest;
import com.ptithcm.response.ActivePromoResponse;
import com.ptithcm.response.PromotionResponse;
import com.ptithcm.service.PromotionService;
import com.ptithcm.service.StaffService;
import com.ptithcm.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {
    private final UserService userService;
    private final StaffService staffService;
    private final PromotionRepository repository;
    private final ProductRepository productRepository;
    private final ProductPromotionRepository productPromotionRepository;
    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(UserService userService, StaffService staffService, PromotionRepository repository, ProductRepository productRepository, ProductPromotionRepository productPromotionRepository, PromotionRepository promotionRepository) {
        this.userService = userService;
        this.staffService = staffService;
        this.repository = repository;
        this.productRepository = productRepository;
        this.productPromotionRepository = productPromotionRepository;
        this.promotionRepository = promotionRepository;
    }

    @Override
    @Transactional
    public Promotion CreatePromotion(PromotionRequest rq, String jwt) throws UserException {
        try {
            User user = userService.findUserProfileByJwt(jwt);
            Staff staff = staffService.findStaffByUserId(user.getUserId());
            Promotion create = new Promotion();
            create.setPromotionName(rq.getPromotionName());
            create.setStatus("Active");
            create.setStaff_id(staff.getStaffId());
            create.setType(rq.getType());
            create.setStartDate(rq.getStartDate());
            create.setEndDate(rq.getEndDate());
            create.setDiscountValue(rq.getDiscountValue());
            create.setValue_max(rq.getValue_max());
            create.setValue_min((rq.getValue_min()));
            Promotion save = repository.save(create);
            return save;
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi
            throw new RuntimeException("Failed to create promotion", e);
        }
    }


    @Override
    public List<Promotion> getAllPromotions(String jwt) throws UserException {
        List<Promotion> promotions = promotionRepository.findAll();
//        List<Object[]> results = promotionRepository.getAllPromotions();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        List<PromotionResponse> promotions = results.stream()
//                .map(result -> {
//                    try {
//                        // Extract fields from the result array
//                        Long promotionId = ((Number) result[0]).longValue();
//                        String promotionName = (String) result[1];
//                        String status = (String) result[2];
//                        BigDecimal discountValue = (BigDecimal) result[3];
//                        String startDate = dateFormat.format((Date) result[4]);
//                        String endDate = dateFormat.format((Date) result[5]);
//                        String staffName = (String) result[6];
//
//                        // Create and return a new PromotionResponse object
//                        return new PromotionResponse(
//                                promotionId,
//                                promotionName,
//                                discountValue,
//                                startDate,
//                                endDate,
//                                status,
//                                staffName
//                        );
//                    } catch (ClassCastException e) {
//                        throw new RuntimeException("Data type mismatch in query result", e);
//                    } catch (Exception e) {
//                        e.printStackTrace(); // For debugging
//                        throw new RuntimeException("Error processing result", e);
//                    }
//                })
//                .collect(Collectors.toList());

        return promotions;
    }

    @Override
    public List<Promotion> getActivePromotionsWithDiscount() {
        return promotionRepository.findByStatus("Active");
    }


//    @Override
//    public List<ActivePromoResponse> getActivePromotionsWithDiscount() {
//        List<Object[]> results = promotionRepository.findActivePromotionsWithDiscount();
//        List<ActivePromoResponse> promotions = new ArrayList<>();
//
//        for (Object[] result : results) {
//            String promotionName = (String) result[0];
//            BigDecimal discountValue = (BigDecimal) result[1];
//            promotions.add(new ActivePromoResponse(promotionName, discountValue));
//        }
//        return promotions;
//    }


}
