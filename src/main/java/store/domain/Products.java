package store.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import store.constants.ErrorMessage;

public class Products {

    private final Map<String, Product> promotionProducts = new HashMap<>();
    private final Map<String, Product> regularProducts = new HashMap<>();

    public Products(List<Product> allProducts) {
        for (Product product : allProducts) {
            addProduct(product);
        }
    }

    public Map<String, Product> getPromotionProducts() {
        return promotionProducts;
    }

    public Map<String, Product> getRegularProducts() {
        return regularProducts;
    }

    private void addProduct(Product product) {
        if (product.hasPromotion()) {
            promotionProducts.put(product.getName(), product);
        }
        if (!product.hasPromotion()) {
            regularProducts.put(product.getName(), product);
        }
    }

    public boolean hasProduct(String productName) {
        if (!promotionProducts.containsKey(productName) && !regularProducts.containsKey(productName)) {
            throw new IllegalArgumentException(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
        }
        return promotionProducts.containsKey(productName) || regularProducts.containsKey(productName);
    }

    public void updatePromotionProductQuantity(String name, int quantity) {
        Product product = promotionProducts.get(name);
        product.updateQuantity(quantity);
    }

    public void updateRegularProductQuantity(String name, int quantity) {
        Product product = regularProducts.get(name);
        product.updateQuantity(quantity);
    }

    public boolean hasSufficientStock(String productName, int quantity) {
        int totalQuantity = regularProducts.get(productName).getQuantity();
        if (promotionProducts.containsKey(productName)) {
            totalQuantity += promotionProducts.get(productName).getQuantity();
        }

        return totalQuantity >= quantity;
    }
}
