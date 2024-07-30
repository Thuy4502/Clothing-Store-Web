package com.ptithcm.service;

import com.ptithcm.exception.CartItemException;
import com.ptithcm.exception.ProductException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Cart;
import com.ptithcm.model.CartItem;
import com.ptithcm.model.ProductDetail;
import com.ptithcm.model.User;
import com.ptithcm.request.AddCartItemRequest;
import org.hibernate.cache.CacheException;

import javax.servlet.http.PushBuilder;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception;
    public CartItem isCartItemExist(Cart cart, ProductDetail productDetail, String size, Long userId) throws CacheException, UserException;
    public void removeCartItem(Long userId, Long cartItemId) throws Exception;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
