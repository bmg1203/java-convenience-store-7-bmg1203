package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionsTest {

    private Promotions promotions;

    @BeforeEach
    void setUp() {
        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"));
        promotionList.add(new Promotion("반짝할인", "1", "1", "2024-05-01", "2024-06-01"));
        promotions = new Promotions(promotionList);
    }

    @Test
    void 프로모션_추가_테스트() {
        // given
        int expectedSize = 2;

        // when
        int actualSize = promotions.getPromotions().size();

        // then
        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void 프로모션_기간_내_확인_테스트() {
        // given
        String activePromotion = "탄산2+1";
        String inactivePromotion = "반짝할인";

        // when
        boolean isActive = promotions.isWithinPeriod(activePromotion);
        boolean isInactive = promotions.isWithinPeriod(inactivePromotion);

        // then
        assertThat(isActive).isTrue();
        assertThat(isInactive).isFalse();
    }
}