package store.domain;

import java.util.List;

public class TotalPrice {

    private int totalCount = 0;
    private int totalPrice = 0;
    private int promotionPrice = 0;
    private int memberShipPrice = 0;

    public TotalPrice(List<Purchase> purchases, Products products) {
        getTotalPrice(purchases, products);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPromotionPrice() {
        return promotionPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setPromotionPrice(int promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public int getMemberShipPrice() {
        return memberShipPrice;
    }

    public void setMemberShipPrice(int memberShipPrice) {
        this.memberShipPrice = memberShipPrice;
    }

    private void getTotalPrice(List<Purchase> purchases, Products products) {
        for (Purchase purchase : purchases) {
            int price = products.getRegularProducts().get(purchase.getName()).getPrice();
            this.totalPrice += purchase.getQuantity() * price;
            this.totalCount += purchase.getQuantity();
        }
    }

    public int getPromotionSalePrice(List<Purchase> purchases, Promotions promotions, Products products) {
        int promotionPrice = 0;
        for (Purchase purchase : purchases) {
            if (purchase.getPromotion().equals("null")) {
                continue;
            }
            Promotion promotion = promotions.getPromotions().get(purchase.getPromotion());
            int price = products.getRegularProducts().get(purchase.getName()).getPrice();
            promotionPrice += price * promotion.freeCount(purchase.getQuantity());
        }
        return promotionPrice;
    }

    public int getMembershipSalePrice(List<Purchase> purchases, Promotions promotions, Products products) {
        int membershipPrice = totalPrice - getPromotionSalePrice(purchases, promotions, products);
        if (membershipPrice > 8000) {
            return 8000;
        }
        return membershipPrice;
    }
}
