package store.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.constants.ErrorMessage;

public class InputValidator {

    private static final String PATTERN = "^[a-zA-Z가-힣0-9\\-]+$";

    public static void validatePurchaseForm(List<String> inputs) {
        for (String input : inputs) {
            Pattern compiledPattern = Pattern.compile(PATTERN);
            Matcher matcher = compiledPattern.matcher(input);

            if (!matcher.matches()) {
                throw new IllegalArgumentException(ErrorMessage.PRODUCT_BUY_FORM_ERROR.getMessage());
            }
        }
    }
}
