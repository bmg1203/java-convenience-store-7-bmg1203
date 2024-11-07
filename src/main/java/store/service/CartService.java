package store.service;

import store.constants.ErrorMessage;
import store.constants.InputPrompts;
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

    public static void hasQuantity(Cart cart, Products products, PromotionService promotionService) {
        for (Purchase purchase : cart.getItems()) {
            if (isPromotionProduct(purchase, products, promotionService)) {
                checkPromotionQuantity(purchase, products);
                continue;
            }
            if (products.getRegularProducts().containsKey(purchase.getName())) {
                checkRegularQuantity(purchase, products);
            }
        }
    }

    private static boolean isPromotionProduct(Purchase purchase, Products products, PromotionService promotionService) {
        return products.getPromotionProducts().containsKey(purchase.getName())
                && promotionService.isPromotionWithinPeriod(products.getPromotionProducts().get(purchase.getName()).getPromotion());
    }

    private static void checkPromotionQuantity(Purchase purchase, Products products) {
        Product promotionProduct = products.getPromotionProducts().get(purchase.getName());
        if (!promotionProduct.hasQuantity(purchase.getQuantity())) {
            InputView.checkPromotionSaleNotAccept(purchase);
        }
    }

    private static void checkRegularQuantity(Purchase purchase, Products products) {
        Product regularProduct = products.getRegularProducts().get(purchase.getName());
        if (!regularProduct.hasQuantity(purchase.getQuantity())) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
        }
    }
}
