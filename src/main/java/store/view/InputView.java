package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;
import java.util.List;
import store.constants.InputPrompts;
import store.domain.Cart;
import store.domain.Products;
import store.domain.Purchase;
import store.utils.Split;
import store.validator.InputValidator;

public class InputView {

    public Cart readItem(Products products) {
        while(true) {
            try {
                String input = getPurchaseInput();
                validatePurchase(Split.commaSpliter(input));
                List<String> purchase = Split.squareBracketsSpliter(Split.commaSpliter(input));
                return validateReadItem(purchase, products);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Cart validateReadItem(List<String> purchase, Products products) {
        validatePurchase(purchase);
        Cart cart = getCart(purchase);
        InputValidator.validateHasProduct(cart, products);
        InputValidator.validateSufficientStock(cart, products);
        return cart;
    }

    public String checkPromotionCountAdd(Purchase purchase) {
        while(true) {
            try {
                System.out.printf(InputPrompts.PROMOTION_QUANTITY_ADD.getPrompt(), purchase.getName());
                String answer = Console.readLine();
                InputValidator.validateAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String checkPromotionSaleNotAccept(Purchase purchase, int count) {
        while(true) {
            try {
                System.out.printf(InputPrompts.NOT_ENOUGH_PROMOTION_QUANTITY.getPrompt(), purchase.getName(), count);
                String answer = Console.readLine();
                InputValidator.validateAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String checkMemberShipSale() {
        while(true) {
            try {
                System.out.println(InputPrompts.MEMBERSHIP_DISCOUNT.getPrompt());
                String answer = Console.readLine();
                InputValidator.validateAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String checkMorePurchase() {
        while(true) {
            try {
                System.out.println(InputPrompts.MORE_PURCHASE.getPrompt());
                String answer = Console.readLine();
                InputValidator.validateAnswer(answer);
                return answer;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getPurchaseInput() {
        System.out.println(InputPrompts.ENTER_PURCHASE_PRODUCTS.getPrompt());
        String input = Console.readLine();
        return input;
    }

    private Cart getCart(List<String> purchase) {
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
