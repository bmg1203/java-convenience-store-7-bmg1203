package store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.utils.FileRead;
import store.utils.Split;

public class InitService {

    private static final String PRODUCTS_FILE_NAME = "src/main/resources/products.md";
    private static final String PROMOTIONS_FILE_NAME = "src/main/resources/promotions.md";

    public Products saveInitProducts() throws IOException {
        List<String> fileContent = FileRead.readFile(PRODUCTS_FILE_NAME);
        List<Product> productList = new ArrayList<>();
        Set<String> productNames = new LinkedHashSet<>();

        saveProducts(fileContent, productNames, productList);
        Products products = new Products(productList);
        products.setProductNames(new ArrayList<>(productNames));
        products.addNoRegularProducts();
        return products;
    }

    private static void saveProducts(List<String> fileContent, Set<String> productNames, List<Product> productList) {
        for (String content : fileContent) {
            List<String> split = Split.commaSpliter(content);
            productNames.add(split.get(0));
            productList.add(new Product(split.get(0), split.get(1), split.get(2), split.get(3)));
        }
    }

    public Promotions saveInitPromotions() throws IOException {
        List<String> fileContent = FileRead.readFile(PROMOTIONS_FILE_NAME);
        List<Promotion> promotions = new ArrayList<>();
        for (String content : fileContent) {
            List<String> split = Split.commaSpliter(content);
            promotions.add(new Promotion(split.get(0), split.get(1), split.get(2), split.get(3), split.get(4)));
        }
        return new Promotions(promotions);
    }
}
