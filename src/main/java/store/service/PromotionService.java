package store.service;

import java.util.ArrayList;
import java.util.List;
import store.constants.StringConstants;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Purchase;
import store.view.InputView;

public class PromotionService {

    private final InputView inputView = new InputView();
    private static final List<Purchase> addRegularPurchase = new ArrayList<>();

    public void setPromotion(Purchase purchase, Products products) {
        Product product = products.getPromotionProducts().get(purchase.getName());
        if (product != null) {
            purchase.setPromotion(product.getPromotion());
        }
    }

    public void promotionsAll(Purchase purchase, Promotions promotions, Products products, Cart cart) {
        Promotion promotion = promotions.getPromotions().get(purchase.getPromotion());
        if (promotion == null || !promotion.isActive()) {
            purchase.setPromotion(StringConstants.NO_PROMOTION.getString());
            return;
        }

        adjustPromotionCount(purchase, promotion, cart);
        handleInsufficientStock(purchase, cart, products, promotion);
    }

    private void adjustPromotionCount(Purchase purchase, Promotion promotion, Cart cart) {
        if (!promotion.correctCount(purchase.getQuantity())) {
            String answer = inputView.checkPromotionCountAdd(purchase);
            adjustPurchaseByAnswer(purchase, promotion, answer);
        }
    }

    private static void adjustPurchaseByAnswer(Purchase purchase, Promotion promotion, String answer) {
        if (answer.equals(StringConstants.YES.getString())) {
            int more = promotion.addCount(purchase.getQuantity());
            purchase.setQuantity(more);
        }
        if (answer.equals(StringConstants.NO.getString())) {
            int extraCount = purchase.getQuantity() - promotion.extraCount(purchase.getQuantity());
            addRegularPurchase.add(new Purchase(purchase.getName(), promotion.extraCount(purchase.getQuantity()), StringConstants.NO_PROMOTION.getString()));
            purchase.setQuantity(extraCount);
        }
    }

    private void handleInsufficientStock(Purchase purchase, Cart cart, Products products, Promotion promotion) {
        Product product = products.getPromotionProducts().get(purchase.getName());
        if (product.getQuantity() < purchase.getQuantity()) {
            int extraCount = purchase.getQuantity() - (product.getQuantity() - promotion.extraCount(product.getQuantity()));
            String answer = inputView.checkPromotionSaleNotAccept(purchase, extraCount);
            handleByAnswerInsufficientStock(purchase, cart, promotion, product, answer, extraCount);
        }
    }

    private static void handleByAnswerInsufficientStock(Purchase purchase, Cart cart, Promotion promotion, Product product, String answer,
                                                        int extraCount) {
        int promotionQuantity = purchase.getQuantity() - extraCount;

        if (answer.equals(StringConstants.YES.getString())) {
            purchase.setQuantity(promotionQuantity);
            addRegularPurchase.add(new Purchase(purchase.getName(), extraCount, StringConstants.NO_PROMOTION.getString()));
        }
        if (answer.equals(StringConstants.NO.getString())) {
            purchase.setQuantity(promotionQuantity);
        }
    }

    public void applyAddPurchaseToCart(Cart cart) {
        if (!addRegularPurchase.isEmpty()) {
            cart.getItems().addAll(addRegularPurchase);
            addRegularPurchase.clear();
        }
    }

    public static void clearAddCart() {
        addRegularPurchase.clear();
    }
}
