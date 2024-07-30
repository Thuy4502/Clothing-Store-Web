package com.ptithcm.service.impl;


import com.ptithcm.exception.ProductException;
import com.ptithcm.model.ProductDetail;
import com.ptithcm.repository.ProductDetailRepository;
import com.ptithcm.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

//    @Override
//    public ProductDetail createProductDetail(ProductDetailRequestDTO productDetailRequest) {
//        ProductDetail productDetail = new ProductDetail();
//        productDetail.setSize(productDetailRequest.getSize());
//        productDetail.setQuantity(productDetailRequest.getQuantity());
//        productDetail.setProduct(productDetailRequest.getProduct());
//        return productDetailRepository.save(productDetail);
//    }

    @Override
    public String deleteProductDetail(Long id) throws ProductException {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new ProductException("ProductDetail not found with id " + id));
        productDetailRepository.delete(productDetail);
        return "ProductDetail deleted successfully";
    }

    @Override
    public ProductDetail updateProductDetail(Long productDetailId, ProductDetail productDetail) throws ProductException {
        ProductDetail existingProductDetail = productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new ProductException("ProductDetail not found with id " + productDetailId));
        existingProductDetail.setSize(productDetail.getSize());
        existingProductDetail.setQuantity(productDetail.getQuantity());
        existingProductDetail.setProduct(productDetail.getProduct());
        return productDetailRepository.save(existingProductDetail);
    }

    @Override
    public ProductDetail findProductDetailById(Long productDetailId) throws ProductException {
        return productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new ProductException("ProductDetail not found with id " + productDetailId));
    }

    @Override
    public ProductDetail findProductDetailBySizeNameAndProductId(long productId, String size) {
        return productDetailRepository.findProductDetailBySizeNameAndProductId(productId,size);
    }

    @Override
    public Page<ProductDetail> getAllProductDetails(Integer pageNumber, Integer pageSize) throws ProductException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return productDetailRepository.findAll(pageable);
    }
}
