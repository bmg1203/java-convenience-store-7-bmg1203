package store.view;

import java.util.List;
import java.util.Map;
import store.constants.OutputPrompts;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Purchase;
import store.domain.TotalPrice;
import store.utils.ConvertFormater;

public class OutputView {

    public void productsOutput (Products products) {
        System.out.println(OutputPrompts.WELCOME_MESSAGE.getPrompts());
        for (String name : products.getRegularProducts().keySet()) {
            if (products.getPromotionProducts().containsKey(name)) {
                printPromotionProduct(name, products.getPromotionProducts());
            }
            printRegularProduct(name, products.getRegularProducts());
        }
    }

    private void printPromotionProduct(String name, Map<String, Product> promotionProducts) {
        Product product = promotionProducts.get(name);
        System.out.printf(OutputPrompts.PRODUCTS_HAVE_PROMOTION.getPrompts(),
                product.getName(), ConvertFormater.moneyFormat(product.getPrice()),
                product.getQuantity(), product.getPromotion());
    }

    private void printRegularProduct(String name, Map<String, Product> regularProducts) {
        Product product = regularProducts.get(name);
        System.out.printf(OutputPrompts.PRODUCTS_NO_PROMOTION.getPrompts(),
                product.getName(), ConvertFormater.moneyFormat(product.getPrice()),
                product.getQuantity());
    }

    public void printReceipt(Cart cart, Products products, Promotions promotions, Map<String, Purchase> items, TotalPrice totalPrice) {
        System.out.println(OutputPrompts.RECEIPT_HEADER.getPrompts());
        for (Purchase purchase : items.values()) {
            int price = products.getRegularProducts().get(purchase.getName()).getPrice();
            System.out.printf(OutputPrompts.RECEIPT_PRODUCTS.getPrompts(), purchase.getName(), purchase.getQuantity(),
                    ConvertFormater.moneyFormat(price * purchase.getQuantity()));
        }

        System.out.println(OutputPrompts.RECEIPT_PROMOTION_HEADER.getPrompts());
        for (Purchase purchase : cart.getItems()) {
            if (!purchase.getPromotion().equals("null")) {
                Promotion promotion = promotions.getPromotions().get(purchase.getPromotion());
                System.out.printf(OutputPrompts.RECEIPT_PROMOTION_PRODUCTS.getPrompts(), purchase.getName(),
                        promotion.freeCount(purchase.getQuantity()));
            }
        }

        System.out.println(OutputPrompts.RECEIPT_TOTAL_PRICE_HEADER.getPrompts());
        System.out.printf(OutputPrompts.RECEIPT_TOTAL_PRICE.getPrompts(), totalPrice.getTotalCount(), ConvertFormater.moneyFormat(totalPrice.getTotalPrice()));
        System.out.printf(OutputPrompts.RECEIPT_PROMOTION_DISCOUNT.getPrompts(), ConvertFormater.moneyFormat(totalPrice.getPromotionPrice()));
        System.out.printf(OutputPrompts.RECEIPT_MEMBERSHIP_DISCOUNT.getPrompts(), ConvertFormater.moneyFormat(totalPrice.getMemberShipPrice()));
        System.out.printf(OutputPrompts.RECEIPT_FINAL_PRICE.getPrompts(), ConvertFormater.moneyFormat(totalPrice.getTotalPrice() - totalPrice.getPromotionPrice() - totalPrice.getMemberShipPrice()));
    }
}
