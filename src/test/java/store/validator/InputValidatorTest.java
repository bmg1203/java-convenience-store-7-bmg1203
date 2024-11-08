package store.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.constants.ErrorMessage;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Purchase;

class InputValidatorTest {

    @Test
    void 구매폼_유효성_검사_정상_테스트() {
        // given
        List<String> validInputs = List.of("콜라", "1000", "10");

        // when, then
        assertThatCode(() -> InputValidator.validatePurchaseForm(validInputs))
                .doesNotThrowAnyException();
    }

    @Test
    void 구매폼_유효성_검사_예외_테스트() {
        //given
        List<String> invalidInputs = List.of("콜라@", "1000$", "10#");

        //when, then
        assertThatThrownBy(() -> InputValidator.validatePurchaseForm(invalidInputs))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.PRODUCT_BUY_FORM_ERROR.getMessage());
    }

    @Test
    void 제품_존재_유효성_검사_테스트() {
        // given
        Product product = new Product("콜라", "1000", "10", "탄산2+1");
        Products products = new Products(List.of(product));
        Cart cart = new Cart(new ArrayList<>(List.of(new Purchase("콜라", "5"))));

        //when, then
        assertThatCode(() -> InputValidator.validateHasProduct(cart, products))
                .doesNotThrowAnyException();
    }

    @Test
    void 제품_존재_유효성_검사_예외_테스트() {
        // given
        Product product = new Product("사이다", "1000", "10", "null");
        Products products = new Products(List.of(product));
        Cart cart = new Cart(new ArrayList<>(List.of(new Purchase("콜라", "5"))));

        // when, then
        assertThatThrownBy(() -> InputValidator.validateHasProduct(cart, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NO_EXSIST_PRODUCT_ERROR.getMessage());
    }

    @Test
    void 충분한_재고_유효성_검사_테스트() {
        // given
        Product product = new Product("콜라", "1000", "3", "탄산2+1");
        Product product2 = new Product("콜라", "1000", "6", "null");
        Products products = new Products(List.of(product, product2));
        Cart cart = new Cart(new ArrayList<>(List.of(new Purchase("콜라", "5"))));

        // when, then
        assertThatCode(() -> InputValidator.validateSufficientStock(cart, products))
                .doesNotThrowAnyException();
    }

    @Test
    void 충분한_재고_유효성_검사_예외_테스트() {
        // given
        Product product = new Product("콜라", "1000", "3", "탄산2+1");  // 재고가 3개인 상품
        Product product2 = new Product("콜라", "1000", "3", "null");
        Products products = new Products(List.of(product, product2));
        Cart cart = new Cart(new ArrayList<>(List.of(new Purchase("콜라", "7"))));  // 5개 구매 시도

        // when & then
        assertThatThrownBy(() -> InputValidator.validateSufficientStock(cart, products))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_ENOUGH_QUANTITY_ERROR.getMessage());
    }

    @Test
    void 응답_유효성_검사_성공_테스트() {
        // given
        String validAnswer = "Y";

        // when, then
        assertThatCode(() -> InputValidator.validateAnswer(validAnswer))
                .doesNotThrowAnyException();
    }

    @Test
    void 응답_유효성_검사_실패_테스트() {
        // given
        String invalidAnswer = "A";

        // when, then
        assertThatThrownBy(() -> InputValidator.validateAnswer(invalidAnswer))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ETC_INPUT_ERROR.getMessage());
    }
}