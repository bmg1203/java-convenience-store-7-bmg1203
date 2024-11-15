package store.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.constants.ErrorMessage;
import store.constants.StringConstants;
import store.domain.Cart;
import store.domain.Products;
import store.domain.Purchase;
import store.utils.Split;

public class InputValidator {

    private static final String PATTERN = "^[a-zA-Z가-힣0-9\\-\\[\\]]+$";
    private static final String VALID_PATTERN = "\\[[a-zA-Z가-힣0-9]+-[0-9]+\\]";

    public static void validateInput(String input) {
        List<String> items = Split.commaSpliter(input);

        for (String item : items) {
            if (!item.matches(VALID_PATTERN)) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_BUY_FORM_ERROR.getMessage());
            }
        }
    }

    public static void validatePurchaseForm(List<String> inputs) {
        for (String input : inputs) {
            Pattern compiledPattern = Pattern.compile(PATTERN);
            Matcher matcher = compiledPattern.matcher(input);

            if (!matcher.matches()) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_BUY_FORM_ERROR.getMessage());
            }
        }
    }

    public static void validateHasProduct(Cart cart, Products products) {
        for (Purchase purchase : cart.getItems()) {
            if (!products.hasProduct(purchase.getName())) {
                throw new IllegalArgumentException(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
            }
        }
    }

    public static void validateSufficientStock(Cart cart, Products products) {
        for (Purchase purchase : cart.getItems()) {
            if (!products.hasSufficientStock(purchase.getName(), purchase.getQuantity())) {
                throw new IllegalArgumentException(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
            }
        }
    }

    public static void validateAnswer(String answer) {
        answer = answer.toUpperCase();
        if (!answer.equals(StringConstants.YES.getString()) && !answer.equals(StringConstants.NO.getString())) {
            throw new IllegalArgumentException(ErrorMessage.ETC_INPUT_ERROR.getMessage());
        }
    }
}
