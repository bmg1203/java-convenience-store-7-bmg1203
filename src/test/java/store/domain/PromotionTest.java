package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionTest {

    private Promotion promotion;
    private Promotion expiredPromotion;

    @BeforeEach
    void setUp() {
        promotion = new Promotion("탄산2+1", "2", "1", "2024-11-01", "2024-12-31");
        expiredPromotion = new Promotion("과자1+1", "1", "1", "2023-01-01", "2023-12-31");
    }

    @Test
    void 프로모션_활성화_상태_테스트() {
        assertThat(promotion.isActive()).isTrue();
        assertThat(expiredPromotion.isActive()).isFalse();
    }

    @Test
    void 프로모션_해당_수량_확인_테스트() {
        //given
        int quantity = 6;

        //when
        boolean isCorrect = promotion.correctCount(quantity);

        //then
        assertThat(isCorrect).isTrue();
    }

    @Test
    void 프로모션_추가_수량_계산_테스트() {
        // given
        int quantity = 5;

        // when
        int updatedQuantity = promotion.addCount(quantity);

        // then
        assertThat(updatedQuantity).isEqualTo(6);
    }

    @Test
    void 프로모션_잔여_수량_계산_테스트() {
        // given
        int quantity = 7;

        // when
        int remainder = promotion.extraCount(quantity);

        // then
        assertThat(remainder).isEqualTo(1);  // 7개의 구매 중 6개는 프로모션에 맞고, 나머지 1개
    }

    @Test
    void 무료_추가_수량_계산_테스트() {
        // given
        int quantity = 6;

        // when
        int freeCount = promotion.freeCount(quantity);

        // then
        assertThat(freeCount).isEqualTo(2);  // 6개 구매 시 프로모션으로 2개의 무료 추가 제공
    }

    @Test
    void 잘못된_날짜_포맷_입력_예외_테스트() {
        // given
        String invalidStartDate = "2024.11.01";
        String invalidEndDate = "2024/12/31";

        // when & then
        assertThatThrownBy(() -> new Promotion("잘못된 프로모션", "2", "1", invalidStartDate, invalidEndDate))
                .isInstanceOf(java.time.format.DateTimeParseException.class);
    }
}