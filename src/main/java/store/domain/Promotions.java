package store.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Promotions {

    private final Map<String, Promotion> promotions = new HashMap<>();

    public Promotions(List<Promotion> promotionInput) {
        for (Promotion promotion : promotionInput) {
            promotions.put(promotion.getName(), promotion);
        }
    }

    public Map<String, Promotion> getPromotions() {
        return promotions;
    }

    public boolean isWithinPeriod(String name) {
        Promotion promotion = promotions.get(name);
        return promotion.isActive();
    }
}
