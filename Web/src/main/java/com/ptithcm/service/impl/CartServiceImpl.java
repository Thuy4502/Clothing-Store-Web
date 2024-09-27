package com.ptithcm.service.impl;

import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.*;
import com.ptithcm.repository.CartItemRepository;
import com.ptithcm.repository.CartRepository;
import com.ptithcm.request.AddCartItemRequest;
import com.ptithcm.request.CartRequest;
import com.ptithcm.service.CartItemService;
import com.ptithcm.service.CartService;
import com.ptithcm.service.ProductDetailService;
import com.ptithcm.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private ProductDetailService productDetailService;
    private CartItemService cartItemService;
    private CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCreatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public String addCartItem(Long userId, AddCartItemRequest req) throws ProductException, UserException {
        Cart cart=cartRepository.findByCustomerId(userId);
        ProductDetail productDetail= productDetailService.findProductDetailBySizeNameAndProductId(req.getProductId(),req.getSize());
        CartItem isPresent = cartItemService.isCartItemExist(cart, productDetail, req.getSize(),userId);
        if(isPresent==null) {
            CartItem cartItem = new CartItem();
            cartItem.setSize(req.getSize());
            cartItem.setQuantity(1);
            cartItem.setCartId(cart.getCartId());
            cartItem.setPrice(req.getPrice());
            cartItem.setColor(req.getColor());
            cartItem.setProductDetailId(productDetail.getProductDetailId());
            cartItemRepository.save(cartItem);

        }
        return "Item added to cart";
    }

    @Override
    public Cart findCustomerCart(Long customerId) {
        Cart cart=cartRepository.findByCustomerId(customerId);
        double totalPrice=0;
        int totalItem=0;
        for(CartItem cartItem:cart.getCartItems()) {
            totalPrice+=cartItem.getPrice()*cartItem.getQuantity();
            totalItem+=cartItem.getQuantity();
        }
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public List<CartRequest> findAllCartCustomer(Long customerId) {
        List<Object[]> results = cartRepository.findCartItemDetails(customerId);

        List<CartRequest> cartItems = results.stream()
                .map(result -> new CartRequest(
                        (Integer) result[0],
                        (double) result[1],
                        (Long) result[2],
                        (double) result[3],
                        (Long) result[4],
                        (Integer) result[5],
                        (String) result[6],
                        (String) result[7],
                        (String) result[8],
                        (Long) result[9]
                ))
                .collect(Collectors.toList());

        // Tính tổng giá trị và số lượng
        double totalPrice = 0;
        int totalItem = 0;

        for (CartRequest item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
            totalItem += item.getQuantity();
        }
        Cart cart=cartRepository.findByCustomerId(customerId);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return cartItems;
    }
}
