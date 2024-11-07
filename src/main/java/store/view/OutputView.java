package store.view;

import java.util.Map;
import store.constants.OutputPrompts;
import store.domain.Product;
import store.domain.Products;
import store.utils.ConvertFormater;

public class OutputView {

    public static void productsOutput (Products products) {
        System.out.println(OutputPrompts.WELCOME_MESSAGE.getPrompts());
        for (String name : products.getRegularProducts().keySet()) {
            if (products.getPromotionProducts().containsKey(name)) {
                printPromotionProduct(name, products.getPromotionProducts());
            }
            printRegularProduct(name, products.getRegularProducts());
        }
    }

    private static void printPromotionProduct(String name, Map<String, Product> promotionProducts) {
        Product product = promotionProducts.get(name);
        System.out.printf(OutputPrompts.PRODUCTS_HAVE_PROMOTION.getPrompts(),
                product.getName(), ConvertFormater.moneyFormat(product.getPrice()),
                product.getQuantity(), product.getPromotion());
    }

    private static void printRegularProduct(String name, Map<String, Product> regularProducts) {
        Product product = regularProducts.get(name);
        System.out.printf(OutputPrompts.PRODUCTS_NO_PROMOTION.getPrompts(),
                product.getName(), ConvertFormater.moneyFormat(product.getPrice()),
                product.getQuantity());
    }
}
