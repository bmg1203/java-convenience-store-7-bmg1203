package store.service;

import store.constants.ErrorMessage;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotions;
import store.domain.Purchase;
import store.view.InputView;

public class CartService {

    public static void hasProduct(Cart cart, Products products) {
        for (Purchase purchase : cart.getItems()) {
            if (!products.hasProduct(purchase.getName())) {
                System.out.println(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
                InputView.readItem();
            }
        }
    }

    public static void hasQuantity(Cart cart, Products products, Promotions promotions) {
        for (Purchase purchase : cart.getItems()) {
            if (products.getPromotionProducts().containsKey(purchase.getName())) {
                checkPromotionQuantity(purchase, products, promotions);
                continue;
            }
            if (products.getRegularProducts().containsKey(purchase.getName())) {
                checkRegularQuantity(purchase, products);
            }
        }
    }

    private static void checkPromotionQuantity(Purchase purchase, Products products, Promotions promotions) {
        Product promotionProduct = products.getPromotionProducts().get(purchase.getName());
        if (!promotionProduct.hasQuantity(purchase.getQuantity())) {
            //일반재고로 구매할건지 묻기
        }
    }

    private static void checkRegularQuantity(Purchase purchase, Products products) {
        Product regularProduct = products.getRegularProducts().get(purchase.getName());
        if (!regularProduct.hasQuantity(purchase.getQuantity())) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
        }
    }
}
