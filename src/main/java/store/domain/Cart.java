package store.domain;

import java.util.ArrayList;
import java.util.List;
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
}
