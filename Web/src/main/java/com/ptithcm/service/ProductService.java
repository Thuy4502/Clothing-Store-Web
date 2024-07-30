package com.ptithcm.service;

import com.ptithcm.dto.ProductRequestDTO;
import com.ptithcm.exception.ProductException;
import com.ptithcm.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequestDTO productRequest);

    public String deleteProduct(int id) throws ProductException;

    Product updateProduct(Long productId, ProductRequestDTO productDTO) throws ProductException;

    public Product findProductById(Long productId) throws ProductException;
//    public List<Product> findProductsByCategory(String category) throws ProductException;

    Page<Product> getAllProducts(String category, String colors, String gender, List<String> sizes, Double minPrice, Double maxPrice, String sort, Integer pageNumber, Integer pageSize) throws ProductException;
}
