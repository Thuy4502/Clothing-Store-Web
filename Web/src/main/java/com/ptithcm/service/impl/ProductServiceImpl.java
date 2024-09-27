package com.ptithcm.service.impl;

import com.ptithcm.dto.ProductRequestDTO;
import com.ptithcm.exception.ProductException;
import com.ptithcm.model.*;
import com.ptithcm.repository.BrandRepository;
import com.ptithcm.repository.CategoryRepository;
import com.ptithcm.repository.ProductRepository;
import com.ptithcm.repository.StaffRepository;
import com.ptithcm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Product createProduct(ProductRequestDTO productRequest) {
        Staff createdBy = staffRepository.findById(productRequest.getCreatedById())
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        Brand brand = brandRepository.findById(productRequest.getBrandId())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setImage(productRequest.getImage());
        product.setDescription(productRequest.getDescription());
        product.setGender(product.getGender());
        product.setColor(productRequest.getColor());
        product.setMaterial(productRequest.getMaterial());
        product.setCurrentPrice(productRequest.getCurrentPrice());

        // Sử dụng giá trị của trường status từ DTO nếu có, nếu không thì đặt giá trị mặc định
        product.setStatus(productRequest.getStatus() != null ? productRequest.getStatus() : "1");
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        product.setCreatedBy(createdBy);
        product.setBrand(brand);
        product.setCategory(category);

        List<ProductDetail> productDetails = productRequest.getProductDetails().stream()
                .map(detailRequest -> {
                    ProductDetail detail = new ProductDetail();
                    detail.setSize(detailRequest.getSize());
                    detail.setQuantity(detailRequest.getQuantity());
                    detail.setProduct(product);
                    return detail;
                }).collect(Collectors.toList());

        product.setProductDetails(productDetails);

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(int id) throws ProductException {
        return "";
    }

    public Product updateProduct(Long productId, ProductRequestDTO productRequest) throws ProductException {
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found with id: " + productId));

        p.setProductName(productRequest.getProductName());
        p.setImage(productRequest.getImage());
        p.setDescription(productRequest.getDescription());
        p.setColor(productRequest.getColor());
        p.setMaterial(productRequest.getMaterial());
        p.setGender(productRequest.getGender());
        p.setCurrentPrice(productRequest.getCurrentPrice());
        p.setStatus(productRequest.getStatus());

        if (productRequest.getBrandId() != null) {
            Brand brand = brandRepository.findById(productRequest.getBrandId())
                    .orElseThrow(() -> new ProductException("Brand not found with id: " + productRequest.getBrandId()));
            p.setBrand(brand);
        }

        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ProductException("Category not found with id: " + productRequest.getCategoryId()));
            p.setCategory(category);
        }

        List<ProductRequestDTO.ProductDetailDTO> productDetails = productRequest.getProductDetails();
        if (productDetails != null) {
            Set<String> newDetailSizes = productDetails.stream()
                    .map(ProductRequestDTO.ProductDetailDTO::getSize)
                    .collect(Collectors.toSet());

            List<ProductDetail> existingDetails = p.getProductDetails();
            existingDetails.removeIf(detail -> !newDetailSizes.contains(detail.getSize()));

            for (ProductRequestDTO.ProductDetailDTO detailDTO : productDetails) {
                ProductDetail existingDetail = existingDetails.stream()
                        .filter(detail -> detail.getSize().equals(detailDTO.getSize()))
                        .findFirst()
                        .orElse(null);

                if (existingDetail != null) {
                    existingDetail.setQuantity(detailDTO.getQuantity());
                } else {
                    // Thêm chi tiết sản phẩm mới nếu chưa tồn tại
                    ProductDetail newDetail = new ProductDetail();
                    newDetail.setSize(detailDTO.getSize());
                    newDetail.setQuantity(detailDTO.getQuantity());
                    newDetail.setProduct(p);
                    p.getProductDetails().add(newDetail);
                }
            }
        }

        return productRepository.save(p);
    }







    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isPresent()) {
            return opt.get();
        }throw new ProductException("Product not found with id-"+productId);
    }

    @Override
    public String deleteProduct(Long productId, Long staffId) throws ProductException {
        Product product = findProductById(productId);
        System.err.println("Delete product " + product.getProductId() + " - " + productId);
        product.setStatus("Inactive");
        product.setUpdateBy(staffId);
        productRepository.save(product);
        return "Stopped Selling Success";
    }

    @Override
    public Page<Product> getAllProducts(String category, String colors, String gender, List<String> sizes, Double minPrice, Double maxPrice, String sort, Integer pageNumber, Integer pageSize) throws ProductException {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, colors, gender, minPrice, maxPrice, sort);

//        if (colors != null && !colors.isEmpty()) {
//            products = products.stream()
//                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
//                    .collect(Collectors.toList());
//        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProducts;
    }

}
