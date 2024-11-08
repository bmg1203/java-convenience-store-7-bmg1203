package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.ErrorMessage;

class PurchaseTest {

    private Purchase purchaseWithPromotion;
    private Purchase purchaseWithoutPromotion;

    @BeforeEach
    void setUp() {
        purchaseWithPromotion = new Purchase("콜라", 3, "탄산2+1");
        purchaseWithoutPromotion = new Purchase("사이다", "5");
    }

    @Test
    void 생성자_테스트_프로모션_없는_경우() {
        assertThat(purchaseWithoutPromotion.getName()).isEqualTo("사이다");
        assertThat(purchaseWithoutPromotion.getQuantity()).isEqualTo(5);
        assertThat(purchaseWithoutPromotion.getPromotion()).isEqualTo("null");
    }

    @Test
    void 생성자_테스트_프로모션_있는_경우() {
        assertThat(purchaseWithPromotion.getName()).isEqualTo("콜라");
        assertThat(purchaseWithPromotion.getQuantity()).isEqualTo(3);
        assertThat(purchaseWithPromotion.getPromotion()).isEqualTo("탄산2+1");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-1", "-10"})
    void 잘못된_수량_입력_예외_테스트(String quantity) {
        assertThatThrownBy(() -> new Purchase("콜라", quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_POSITIVE_INPUT_ERROR.getMessage());
    }

    @Test
    void 수량_업데이트_테스트() {
        //given
        int newQuantity = 7;

        //when
        purchaseWithPromotion.setQuantity(newQuantity);

        //then
        assertThat(purchaseWithPromotion.getQuantity()).isEqualTo(newQuantity);
    }

    @Test
    void 프로모션_업데이트_테스트() {
        //given
        String newPromotion = "탄산2+1";

        //when
        purchaseWithPromotion.setPromotion(newPromotion);

        //then
        assertThat(purchaseWithPromotion.getPromotion()).isEqualTo(newPromotion);
    }
}