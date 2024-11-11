package store.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constants.ErrorMessage;

public class Cart {

    private List<Purchase> items;

    public Cart(List<Purchase> items) {
        this.items = new ArrayList<>();
        addPurchase(new ArrayList<>(items));
    }

    public List<Purchase> getItems() {
        return items;
    }

    private void addPurchase(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            if(validateItem(purchase)) {
                this.items.add(purchase);
            }
        }
    }

    private boolean validateItem(Purchase purchase) {
        if (items.stream().anyMatch(item -> item.getName().equals(purchase.getName()))) {
            throw new IllegalArgumentException(ErrorMessage.DUPLICATE_PRODUCT_INPUT_ERROR.getMessage());
        }
        return true;
    }

    public Map<String, Purchase> mergeItems(List<Purchase> purchases) {
        Map<String, Purchase> itemsMap = new HashMap<>();
        for (Purchase purchase : purchases) {
            addItems(purchase, itemsMap);
        }
        return itemsMap;
    }

    private static void addItems(Purchase purchase, Map<String, Purchase> itemsMap) {
        Purchase existingItem = itemsMap.get(purchase.getName());

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + purchase.getQuantity());
        }
        if (existingItem == null) {
            Purchase item = new Purchase(purchase.getName(), purchase.getQuantity(), purchase.getPromotion());
            itemsMap.put(item.getName(), item);
        }
    }
}
