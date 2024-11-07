package store.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.utils.FileRead;
import store.utils.Split;

public class InitService {

    private static final String PRODUCTS_FILE_NAME = "resources/products.md";
    private static final String PROMOTIONS_FILE_NAME = "resources/promotions.md";

    public static Products saveInitProducts() throws IOException {
        List<String> fileContent = FileRead.readFile(PRODUCTS_FILE_NAME);
        List<Product> products = new ArrayList<>();
        for (String content : fileContent) {
            List<String> split = Split.commaSpliter(content);
            products.add(new Product(split.get(0), split.get(1), split.get(2), split.get(3)));
        }
        return new Products(products);
    }

    public static Promotions saveInitPromotions() throws IOException {
        List<String> fileContent = FileRead.readFile(PROMOTIONS_FILE_NAME);
        List<Promotion> promotions = new ArrayList<>();
        for (String content : fileContent) {
            List<String> split = Split.commaSpliter(content);
            promotions.add(new Promotion(split.get(0), split.get(1), split.get(2), split.get(3), split.get(4)));
        }
        return new Promotions(promotions);
    }
}
