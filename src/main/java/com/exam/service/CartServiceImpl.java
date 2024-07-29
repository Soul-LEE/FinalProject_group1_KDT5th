package com.exam.service;

import com.exam.entity.Carts;
import com.exam.entity.CartItems;
import com.exam.entity.Products;
import com.exam.config.CartsMapper;
import com.exam.config.CartItemsMapper;
import com.exam.config.ProductsMapper;
import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import com.exam.dto.ProductsDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    CartsMapper cartMapper;
    CartItemsMapper cartItemsMapper;
    ProductsMapper productsMapper;

    @Autowired
    public CartServiceImpl(CartsMapper cartMapper, CartItemsMapper cartItemsMapper, ProductsMapper productsMapper) {
        this.cartMapper = cartMapper;
        this.cartItemsMapper = cartItemsMapper;
        this.productsMapper = productsMapper;
    }

    @Override
    public CartsDTO getCartById(int cartId) {
        return cartMapper.findCartById(cartId);
    }

    @Override
    public List<CartItemsDTO> getItemsByCartId(int cartId) {
        return cartItemsMapper.findItemsByCartId(cartId);
    }

    @Override
    @Transactional
    public int addItem(CartItemsDTO item) {
        // 제품 존재 여부 확인
        ProductsDTO product = productsMapper.findProductById(item.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("유효하지 않은 제품 ID입니다.");
        }

        // 카트 존재 여부 확인
        CartsDTO cart = cartMapper.findCartById(item.getCartId());
        if (cart == null) {
            throw new IllegalArgumentException("유효하지 않은 카트 ID입니다.");
        }

        // 카트에 아이템 추가
        return cartItemsMapper.addItem(item);
    }

    @Override
    @Transactional
    public void updateItemAmount(int cartItemId, int amount) {
        CartItemsDTO item = cartItemsMapper.findItemById(cartItemId);
        if (item == null) {
            throw new IllegalArgumentException("해당 카트 아이템을 찾을 수 없습니다.");
        }
        item.setAmount(amount);
        cartItemsMapper.updateItem(item);
    }

    @Override
    @Transactional
    public void removeItemFromCart(int cartItemId) {
        cartItemsMapper.deleteItem(cartItemId);
    }

    @Override
    @Transactional
    public void clearCart(int cartId) {
        cartItemsMapper.deleteItemsByCartId(cartId);
    }
}
