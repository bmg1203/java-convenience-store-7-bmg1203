package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.constants.ErrorMessage;

class ProductsTest {

    private Products products;
    private List<Product> productList;

    @BeforeEach
    void setUp() {
        productList = new ArrayList<>();
        productList.add(new Product("콜라", "1000", "10", "탄산2+1"));
        productList.add(new Product("사이다", "800", "5", "null"));
        productList.add(new Product("물", "500", "20", "null"));

        products = new Products(productList);
    }

    @Test
    void 프로모션_상품과_일반_상품_분류_테스트() {
        //then
        assertThat(products.getPromotionProducts().size()).isEqualTo(1);
        assertThat(products.getRegularProducts().size()).isEqualTo(2);
        assertThat(products.getPromotionProducts()).containsKey("콜라");
        assertThat(products.getRegularProducts()).containsKeys("사이다", "물");
    }

    @Test
    void 상품_존재_확인_테스트() {
        //given
        String existingProduct = "콜라";
        String nonExistingProduct = "주스";

        //when, then
        assertThat(products.hasProduct(existingProduct)).isTrue();
        assertThatThrownBy(() -> products.hasProduct(nonExistingProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
    }

    @Test
    void 프로모션_상품_수량_업데이트_테스트() {
        //given
        String productName = "콜라";
        int quantityToReduce = 3;

        //when
        products.updatePromotionProductQuantity(productName, quantityToReduce);

        //then
        assertThat(products.getPromotionProducts().get(productName).getQuantity()).isEqualTo(7);
    }

    @Test
    void 일반_상품_수량_업데이트_테스트() {
        //given
        String productName = "사이다";
        int quantityToReduce = 2;

        //when
        products.updateRegularProductQuantity(productName, quantityToReduce);

        //then
        assertThat(products.getRegularProducts().get(productName).getQuantity()).isEqualTo(3);
    }

    @Test
    void 충분한_재고_확인_테스트() {
        // given
        String productName = "물";
        int sufficientQuantity = 15;
        int insufficientQuantity = 25;

        // when & then
        assertThat(products.hasSufficientStock(productName, sufficientQuantity)).isTrue();
        assertThat(products.hasSufficientStock(productName, insufficientQuantity)).isFalse();
    }
}