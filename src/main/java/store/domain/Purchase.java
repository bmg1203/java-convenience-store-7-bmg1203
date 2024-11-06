package store.domain;

import store.constants.ErrorMessage;
import store.constants.NumberConstants;
import store.utils.Parser;

public class Purchase {

    private final String name;
    private final int quantity;

    public Purchase(String name, String quantity) {
        this.name = name; //이름 유효성 검사하기
        this.quantity = validateQuantity(Parser.parseNumberToInt(quantity));
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    private int validateQuantity(int count) {
        if (count <= NumberConstants.MIN_QUANTITY.getCount()) {
            throw new IllegalArgumentException(ErrorMessage.NOT_POSITIVE_INPUT_ERROR.getMessage());
        }
        return count;
    }
}
