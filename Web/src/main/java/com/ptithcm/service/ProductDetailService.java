package com.ptithcm.service;

import com.ptithcm.exception.ProductException;
import com.ptithcm.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

public interface ProductDetailService {
//    ProductDetail createProductDetail(ProductDetailRequestDTO productDetailRequest);

    public String deleteProductDetail(Long id) throws ProductException;
    public ProductDetail updateProductDetail(Long productDetailId, ProductDetail productDetail) throws ProductException;
    public ProductDetail findProductDetailById(Long productDetailId) throws ProductException;
    ProductDetail findProductDetailBySizeNameAndProductId(long productId,String size);

    Page<ProductDetail> getAllProductDetails(Integer pageNumber, Integer pageSize) throws ProductException;
}
