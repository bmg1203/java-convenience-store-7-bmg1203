package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Purchase;

class CartServiceTest {

    private Products products;
    private Cart cart;

    @BeforeEach
    void setUp() {
        setProducts();

        setCart();
    }

    private void setProducts() {
        List<Product> productList = List.of(
                new Product("콜라", "1000", "20", "null"),
                new Product("사이다", "1200", "15", "탄산2+1"),
                new Product("사이다", "1200", "15", "null")
        );

        products = new Products(productList);
    }

    private void setCart() {
        cart = new Cart(new ArrayList<>());
        cart.getItems().add(new Purchase("콜라", 5, "null")); // 일반 제품 5개 구매
        cart.getItems().add(new Purchase("사이다", 3, "탄산2+1")); // 프로모션 제품 3개 구매
    }

    @Test
    void 제품_재고_감소_테스트() {
        // given
        CartService cartService = new CartService();

        // when
        products = cartService.productsReduce(products, cart);

        // then
        Product cola = products.getRegularProducts().get("콜라");
        Product cider = products.getPromotionProducts().get("사이다");

        assertThat(cola.getQuantity()).isEqualTo(15); // 원래 20개에서 5개 감소
        assertThat(cider.getQuantity()).isEqualTo(12); // 원래 15개에서 3개 감소
    }
}