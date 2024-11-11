package store.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import store.constants.ErrorMessage;
import store.constants.StringConstants;

public class Products {

    private List<String> productNames = new ArrayList<>();
    private final Map<String, Product> promotionProducts = new LinkedHashMap<>();
    private final Map<String, Product> regularProducts = new LinkedHashMap<>();

    public Products(List<Product> allProducts) {
        for (Product product : allProducts) {
            addProduct(product);
        }
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public Map<String, Product> getPromotionProducts() {
        return promotionProducts;
    }

    public Map<String, Product> getRegularProducts() {
        return regularProducts;
    }

    public void setProductNames(List<String> productNames) {
        this.productNames = productNames;
    }

    private void addProduct(Product product) {
        if (product.hasPromotion()) {
            promotionProducts.put(product.getName(), product);
        }
        if (!product.hasPromotion()) {
            regularProducts.put(product.getName(), product);
        }
    }

    public void addNoRegularProducts() {
        for (String productName : productNames) {
            if (!regularProducts.containsKey(productName)) {
                Product product = promotionProducts.get(productName);
                regularProducts.put(productName, new Product(productName, String.valueOf(product.getPrice()),
                        StringConstants.ZERO_STRING.getString(), StringConstants.NO_PROMOTION.getString()));
            }
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
