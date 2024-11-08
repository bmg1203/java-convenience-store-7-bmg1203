package store.domain;

import store.constants.ErrorMessage;
import store.constants.NumberConstants;
import store.utils.Parser;

public class Purchase {

    private final String name;
    private int quantity;
    private String promotion;

    public Purchase(String name, String quantity) {
        this.name = name;
        this.quantity = validateQuantity(Parser.parseNumberToInt(quantity));
        this.promotion = "null";
    }

    public Purchase(String name, int quantity, String promotion) {
        this.name = name;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    private int validateQuantity(int count) {
        if (count <= NumberConstants.MIN_QUANTITY.getCount()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_POSITIVE_INPUT_ERROR.getMessage());
        }
        return count;
    }
}
