package com.ptithcm.service;

import com.ptithcm.exception.UserException;
import com.ptithcm.model.ProductPromotion;
import com.ptithcm.model.Promotion;
import com.ptithcm.request.PromotionRequest;
import com.ptithcm.response.ActivePromoResponse;
import com.ptithcm.response.PromotionResponse;

import java.util.List;

public interface PromotionService {
    Promotion CreatePromotion(PromotionRequest rq, String jwt) throws UserException;
    List<Promotion> getAllPromotions(String jwt) throws UserException;
//    List<ActivePromoResponse> getActivePromotionsWithDiscount();
    List<Promotion> getActivePromotionsWithDiscount();


}
