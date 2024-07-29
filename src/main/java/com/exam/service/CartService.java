package com.exam.service;

import com.exam.dto.CartItemsDTO;
import com.exam.dto.CartsDTO;
import java.util.List;

public interface CartService {
    CartsDTO getCartById(int cartId);
    List<CartItemsDTO> getItemsByCartId(int cartId);
    int addItem(CartItemsDTO item);
    void updateItemAmount(int cartItemId, int amount);
    void removeItemFromCart(int cartItemId);
    void clearCart(int cartId);
}