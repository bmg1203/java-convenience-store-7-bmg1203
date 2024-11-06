package store.domain;

import store.constants.ErrorMessage;
import store.constants.NumberConstants;

public class Product {

    private final String name;
    private final int price;
    private final int quantity;
    private final String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = validatePrice(parseToInt(price));
        this.quantity = validateQuantity(parseToInt(quantity));
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    private int parseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_ERROR.getMessage());
        }
    }

    private int validateQuantity(int quantity) {
        if(quantity < NumberConstants.MIN_QUANTITY.getCount()) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_ERROR.getMessage());
        }
        return quantity;
    }

    private int validatePrice(int price) {
        if(price < NumberConstants.MIN_PRICE.getCount()) {
            throw new IllegalArgumentException(ErrorMessage.PRODUCT_ERROR.getMessage());
        }
        return price;
    }

    public boolean hasPromition() {
        return promotion != null && !promotion.equals("null");
    }
}
