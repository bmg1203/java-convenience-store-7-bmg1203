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
    private static final List<Purchase> addPurchase = new ArrayList<>();

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

    //프로모션 종류별 혜택 개수 맞는지 확인
    //프로모션 재고 부족시 일반 재고(정상가)로 구매건지 확인
    //나머진 그냥 구매로 굳히기(카트에 들어가는 것인 Purchase에도 프로모션 넣자)
    public void promotionsAll(Purchase purchase, Promotions promotions, Products products, Cart cart) {
        Promotion promotion = promotions.getPromotions().get(purchase.getPromotion());
        if (promotion == null) {
            return;
        }

        adjustPromotionCount(purchase, promotion);
        handleInsufficientStock(purchase, cart, products, promotion);
    }

    private void adjustPromotionCount(Purchase purchase, Promotion promotion) {
        if (!promotion.correctCount(purchase.getQuantity())) {
            String answer = inputView.checkPromotionCountAdd(purchase);
            if (answer.equals(YES)) {
                int more = promotion.addCount(purchase.getQuantity());
                purchase.setQuantity(more);
            }
            if (answer.equals(NO)) {
                purchase.setQuantity(purchase.getQuantity() - promotion.extraCount(purchase.getQuantity()));
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
            // 일반 상품으로 추가
            addPurchase.add(new Purchase(purchase.getName(), extraCount, "null"));
        }
        if (answer.equals(NO)) {
            purchase.setQuantity(promotionQuantity);
        }
    }

    public void applyAddPurchaseToCart(Cart cart) {
        if (addPurchase != null && !addPurchase.isEmpty()) {
            cart.getItems().addAll(addPurchase);
            addPurchase.clear();
        }
    }

    public static void clearAddCart() {
        addPurchase.clear();
    }
}
