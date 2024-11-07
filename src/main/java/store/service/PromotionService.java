package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import store.domain.Promotion;
import store.domain.Promotions;

public class PromotionService {

    private final Promotions promotions;

    public PromotionService(Promotions promotions) {
        this.promotions = promotions;
    }

    public boolean isPromotionWithinPeriod(String promotionName) {
        Promotion promotion = promotions.getPromotions().get(promotionName);
        if (promotion == null) {
            return false;
        }
        return promotion.isActive();
    }

    public int calculatePromotionQuantity(int quantity, Promotion promotion) {
        int applicableQuantity = (quantity / promotion.getBuy()) * promotion.getGet();
        return applicableQuantity;
    }
}
