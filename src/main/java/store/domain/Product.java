package store.domain;

import store.constants.ErrorMessage;
import store.constants.NumberConstants;
import store.constants.StringConstants;
import store.utils.Parser;

public class Product {

    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String name, String price, String quantity, String promotion) {
        this.name = name;
        this.price = validatePrice(Parser.parseNumberToInt(price));
        this.quantity = validateQuantity(Parser.parseNumberToInt(quantity));
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

    public boolean hasPromotion() {
        return promotion != null && !promotion.equals(StringConstants.NO_PROMOTION.getString());
    }

    public boolean hasQuantity(int quantity) {
        return this.quantity >= quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
