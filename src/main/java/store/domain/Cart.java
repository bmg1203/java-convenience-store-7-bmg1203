package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constants.ErrorMessage;

public class Cart {

    private final List<Purchase> items;

    public Cart(List<Purchase> items) {
        this.items = new ArrayList<>();
        addPurchase(items);
    }

    public List<Purchase> getItems() {
        return items;
    }

    private void addPurchase(List<Purchase> items) {
        for (Purchase purchase : items) {
            if(validateItem(purchase)) {
                items.add(purchase);
            }
        }
    }

    private boolean validateItem(Purchase purchase) {
        if (items.contains(purchase)) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_PRODUCT_INPUT_ERROR.getMessage());
        }
        return true;
    }

    public Map<String, Purchase> mergeItems(List<Purchase> purchases) {
        Map<String, Purchase> items = new HashMap<>();
        for (Purchase purchase : purchases) {
            addItems(purchase, items);
        }
        return items;
    }

    private static void addItems(Purchase purchase, Map<String, Purchase> items) {
        if (items.containsKey(purchase.getName())) {
            Purchase item = items.get(purchase.getName());
            item.setQuantity(item.getQuantity() + purchase.getQuantity());
        }
        if (!items.containsKey(purchase.getName())) {
            Purchase item = new Purchase(purchase.getName(), purchase.getQuantity(), purchase.getPromotion());
            items.put(item.getName(), item);
        }
    }
}
