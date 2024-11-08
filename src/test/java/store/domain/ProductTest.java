package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductTest {

    private Product productWithPromotion;
    private Product productWithoutPromotion;

    @BeforeEach
    void setUp() {
        productWithPromotion = new Product("콜라", "1000", "10", "탄산2+1");
        productWithoutPromotion = new Product("사이다", "1000", "7", "null");
    }

    @Test
    void 생성자_테스트() {
        assertThat(productWithPromotion.getName()).isEqualTo("콜라");
        assertThat(productWithPromotion.getPrice()).isEqualTo(1000);
        assertThat(productWithPromotion.getQuantity()).isEqualTo(10);
        assertThat(productWithPromotion.hasPromotion()).isTrue();
    }

    @Test
    void 생성자_테스트2() {
        assertThat(productWithoutPromotion.getName()).isEqualTo("사이다");
        assertThat(productWithoutPromotion.getPrice()).isEqualTo(1000);
        assertThat(productWithoutPromotion.getQuantity()).isEqualTo(7);
        assertThat(productWithoutPromotion.hasPromotion()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"천원", "가격 없음", "-100", ""})
    void 잘못된_가격_입력시_예외_테스트(String price) {
        // given
        String name = "콜라";
        String quantity = "10";
        String promotion = "탄산2+1";

        // when & then
        assertThatThrownBy(() -> new Product(name, price, quantity, promotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"열 개", "재고 없음", "-100", ""})
    void 잘못된_수량_입력시_예외_테스트(String quantity) {
        // given
        String name = "콜라";
        String price = "1000";
        String promotion = "탄산2+1";

        // when & then
        assertThatThrownBy(() -> new Product(name, price, quantity, promotion))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수량_업데이트_테스트() {
        //given
        int originalQuantity = 10;
        int quantity = 3;

        //when
        productWithPromotion.updateQuantity(3);

        //then
        assertThat(productWithPromotion.getQuantity()).isEqualTo(originalQuantity - quantity);
    }
}