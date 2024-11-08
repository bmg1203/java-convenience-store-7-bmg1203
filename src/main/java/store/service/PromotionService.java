package store.service;

import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Purchase;
import store.view.InputView;

public class PromotionService {

    private final InputView inputView;

    public PromotionService(InputView inputView) {
        this.inputView = inputView;
    }

    public boolean isPromotionWithinPeriod(String promotionName, Promotions promotions) {
        Promotion promotion = promotions.getPromotions().get(promotionName);
        if (promotion == null) {
            return false;
        }
        return promotion.isActive();
    }

    //프로모션 종류별 혜택 개수 맞는지 확인
    //프로모션 재고 부족시 일반 재고(정상가)로 구매건지 확인
    //나머진 그냥 구매로 굳히기(카트에 들어가는 것인 Purchase에도 프로모션 넣자)
    public void promotionsAll(Purchase purchase, Promotions promotions, Products products, Cart cart) {
        //종류별 혜택 개수 확인
        Promotion promotion = promotions.getPromotions().get(purchase.getName());
        Product product = products.getPromotionProducts().get(purchase.getName());
        purchase.setPromotion(promotion.getName());

        //프로모션 개수만큼 가져왔는가?
        if (!promotion.correctCount(purchase.getQuantity())) {
            String answer = inputView.checkMorePurchase();
            if (answer.equals("Y")) {
                int more = promotion.addCount(purchase.getQuantity());
                purchase.setQuantity(more);
            }
            if (answer.equals("N")) {
                purchase.setQuantity(purchase.getQuantity() - promotion.extraCount(purchase.getQuantity()));
            }
        }

        //프로모션 재고가 부족한가?
        if (product.getQuantity() < purchase.getQuantity()) {
            String answer = inputView.checkPromotionSaleNotAccept(purchase, promotion.extraCount(purchase.getQuantity()));
            if (answer.equals("Y")) {
                int extraCount = promotion.extraCount(purchase.getQuantity());
                purchase.setQuantity(purchase.getQuantity() - extraCount);
                //일반 상품으로 추가
                cart.getItems().add(new Purchase(purchase.getName(), purchase.getQuantity(), promotion.getName()));
            }
            if (answer.equals("N")) {
                purchase.setQuantity(purchase.getQuantity() - promotion.extraCount(purchase.getQuantity()));
            }
        }
    }

    public int calculatePromotionQuantity(int quantity, Promotion promotion) {
        int applicableQuantity = (quantity / promotion.getBuy()) * promotion.getGet();
        return applicableQuantity;
    }
}
