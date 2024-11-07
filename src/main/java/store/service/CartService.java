package store.service;

import store.constants.ErrorMessage;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Purchase;
import store.view.InputView;

public class CartService {

    private final InputView inputView;

    public CartService(InputView inputView) {
        this.inputView = inputView;
    }

    public void hasProduct(Cart cart, Products products) {
        for (Purchase purchase : cart.getItems()) {
            if (!products.hasProduct(purchase.getName())) {
                System.out.println(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
                inputView.readItem();
            }
        }
    }

    public void hasQuantity(Cart cart, Products products, PromotionService promotionService) {
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

    private void checkPromotionQuantity(Purchase purchase, Products products) {
        Product promotionProduct = products.getPromotionProducts().get(purchase.getName());
        if (!promotionProduct.hasQuantity(purchase.getQuantity())) {
            inputView.checkPromotionSaleNotAccept(purchase);
        }
    }

    private void checkRegularQuantity(Purchase purchase, Products products) {
        Product regularProduct = products.getRegularProducts().get(purchase.getName());
        if (!regularProduct.hasQuantity(purchase.getQuantity())) {
            throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
        }
    }
}
