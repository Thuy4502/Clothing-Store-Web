package com.ptithcm.service;

import com.ptithcm.model.ProductPromotion;
import com.ptithcm.request.PromotionRequest;

import java.util.List;

public interface ProductPromotionService {

    List<ProductPromotion> createProductPromotion(PromotionRequest rq);
}
