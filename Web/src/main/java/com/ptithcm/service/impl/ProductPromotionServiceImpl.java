package com.ptithcm.service.impl;

import com.ptithcm.model.Product;
import com.ptithcm.model.ProductPromotion;
import com.ptithcm.model.Promotion;
import com.ptithcm.repository.ProductPromotionRepository;
import com.ptithcm.repository.ProductRepository;
import com.ptithcm.repository.PromotionRepository;
import com.ptithcm.request.PromotionRequest;
import com.ptithcm.service.ProductPromotionService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPromotionServiceImpl implements ProductPromotionService {

    private final ProductPromotionRepository productPromotionRepository;
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;

    public ProductPromotionServiceImpl(ProductPromotionRepository productPromotionRepository, PromotionRepository promotionRepository, ProductRepository productRepository) {
        this.productPromotionRepository = productPromotionRepository;
        this.promotionRepository = promotionRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductPromotion> createProductPromotion(PromotionRequest rq) {
        // Kiểm tra xem danh sách productId có rỗng không
        if (rq.getProductId() == null || rq.getProductId().isEmpty()) {
            throw new IllegalArgumentException("Product ID list cannot be null or empty");
        }

        List<Long> productIds = rq.getProductId();

        // Lấy Promotion từ ID
        Promotion promotion = promotionRepository.findById(rq.getPromotionId())
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

        // Lấy các Product từ danh sách ID
        List<Product> products = productRepository.findAllById(productIds);
        if (products.size() != productIds.size()) {
            throw new EntityNotFoundException("One or more products not found");
        }

        // Tạo và lưu các ProductPromotion
        List<ProductPromotion> productPromotions = products.stream()
                .map(product -> {
                    ProductPromotion productPromotion = new ProductPromotion();
                    productPromotion.setProduct_id(product.getProductId());
                    productPromotion.setPromotion_id(rq.getPromotionId());
                    return productPromotion;
                })
                .collect(Collectors.toList());

        productPromotionRepository.saveAll(productPromotions);

        return productPromotions;
    }
}
