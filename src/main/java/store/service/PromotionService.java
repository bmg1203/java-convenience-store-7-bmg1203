package store.service;

import java.util.ArrayList;
import java.util.List;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Purchase;
import store.view.InputView;

public class PromotionService {

    private static final String NO_PROMOTION = "null";
    private static final String YES = "Y";
    private static final String NO = "N";
    private final InputView inputView;
    private static final List<Purchase> addRegularPurchase = new ArrayList<>();

    public PromotionService(InputView inputView) {
        this.inputView = inputView;
    }

    //프로모션 있으면 set
    public void setPromotion(Purchase purchase, Products products) {
        Product product = products.getPromotionProducts().get(purchase.getName());
        if (product != null) {
            purchase.setPromotion(product.getPromotion());
        }
    }

    public void promotionsAll(Purchase purchase, Promotions promotions, Products products, Cart cart) {
        Promotion promotion = promotions.getPromotions().get(purchase.getPromotion());
        if (promotion == null || !promotion.isActive()) {
            purchase.setPromotion(NO_PROMOTION);
            return;
        }

        adjustPromotionCount(purchase, promotion, cart);
        handleInsufficientStock(purchase, cart, products, promotion);
    }

    private void adjustPromotionCount(Purchase purchase, Promotion promotion, Cart cart) {
        if (!promotion.correctCount(purchase.getQuantity())) {
            String answer = inputView.checkPromotionCountAdd(purchase);
            if (answer.equals(YES)) {
                int more = promotion.addCount(purchase.getQuantity());
                purchase.setQuantity(more);
            }
            if (answer.equals(NO)) {
                int extraCount = purchase.getQuantity() - promotion.extraCount(purchase.getQuantity());
                purchase.setQuantity(purchase.getQuantity() - extraCount);
                addRegularPurchase.add(new Purchase(purchase.getName(), extraCount, NO_PROMOTION));
            }
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

        if (answer.equals(YES)) {
            purchase.setQuantity(promotionQuantity);
            addRegularPurchase.add(new Purchase(purchase.getName(), extraCount, NO_PROMOTION));
        }
        if (answer.equals(NO)) {
            purchase.setQuantity(promotionQuantity);
        }
    }

    public void applyAddPurchaseToCart(Cart cart) {
        if (addRegularPurchase != null && !addRegularPurchase.isEmpty()) {
            cart.getItems().addAll(addRegularPurchase);
            addRegularPurchase.clear();
        }
    }

    public static void clearAddCart() {
        addRegularPurchase.clear();
    }
}
