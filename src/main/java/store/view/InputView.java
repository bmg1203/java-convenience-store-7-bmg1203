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

    public Cart readItem() {
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

    public String checkPromotionSaleNotAccept(Purchase purchase) {
        while(true) {
            try {
                //모자란 개수 구하는 로직 추가하여 적용하기
                System.out.printf(InputPrompts.NOT_ENOUGH_PROMOTION_QUANTITY.getPrompt(), purchase.getName(), purchase.getQuantity());
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
