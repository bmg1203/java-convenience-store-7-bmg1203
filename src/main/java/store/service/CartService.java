package store.service;

import store.domain.Cart;
import store.domain.Products;
import store.domain.Purchase;

public class CartService {

    public Products productsReduce(Products products, Cart cart) {
        for (Purchase purchase : cart.getItems()) {
            if (purchase.getPromotion().equals("null")) {
                products.getRegularProducts().get(purchase.getName()).updateQuantity(purchase.getQuantity());
            }
            if (!purchase.getPromotion().equals("null")) {
                products.getPromotionProducts().get(purchase.getName()).updateQuantity(purchase.getQuantity());
            }
        }
        return products;
    }
}
