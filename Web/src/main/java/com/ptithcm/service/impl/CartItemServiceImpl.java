package com.ptithcm.service.impl;

import com.ptithcm.exception.CartItemException;
import com.ptithcm.exception.UserException;
import com.ptithcm.model.Cart;
import com.ptithcm.model.CartItem;
import com.ptithcm.model.ProductDetail;
import com.ptithcm.model.User;
import com.ptithcm.repository.CartItemRepository;
import com.ptithcm.repository.CartRepository;
import com.ptithcm.service.CartItemService;
import com.ptithcm.service.ProductService;
import com.ptithcm.service.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.cache.CacheException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;
    private ProductService productService;



    @Override
    public CartItem createCartItem(CartItem cartItem) {
//        CartItem newCartItem = new CartItem();
//        newCartItem.setSize(cartItem.getSize());
//        return createdCartItem;
        return null;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id);

        User user = userService.findUserById(item.getCart().getCustomer().getCustomerId());
        if(user.getUserId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
        }
        return cartItemRepository.save(item);
    }


    @Override
    public CartItem isCartItemExist(Cart cart, ProductDetail productDetail, String size, Long userId) throws CacheException, UserException {
        CartItem cartItem= cartItemRepository.isCartItemExist(cart, productDetail, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
       CartItem cartItem = findCartItemById(cartItemId);
       cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw  new CartItemException("CartItem Not Found with id: " + cartItemId);
    }
}
