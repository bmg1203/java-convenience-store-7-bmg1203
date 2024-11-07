package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.constants.InputPrompts;
import store.domain.Cart;
import store.domain.Purchase;
import store.utils.Split;
import store.validator.InputValidator;

public class InputView {

    public static Cart readItem() {
        while(true) {
            try {
                String input = getPurchaseInput();
                List<String> purchase = Split.squareBracketsSpliter(Split.commaSpliter(input));
                validatePurchase(purchase);
                return getCart(purchase);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String getPurchaseInput() {
        System.out.println(InputPrompts.ENTER_PURCHASE_PRODUCTS.getPrompt());
        String input = Console.readLine();
        return input;
    }

    private static Cart getCart(List<String> purchase) {
        List<Purchase> products = new ArrayList<>();
        for (String productInput : purchase) {
            List<String> buyInfo = Split.hyphenSpliter(productInput);
            products.add(new Purchase(buyInfo.get(0), buyInfo.get(1)));
        }
        return new Cart(products);
    }

    private static void validatePurchase(List<String> purchase) {
        InputValidator.validatePurchaseForm(purchase);
    }
}
