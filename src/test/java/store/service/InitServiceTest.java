package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Products;
import store.domain.Promotions;
import store.utils.FileRead;

class InitServiceTest {

    private InitService initService;

    @BeforeEach
    void setUp() {
        initService = new InitService();
    }

    @Test
    void 제품_저장_테스트() throws IOException {
        Products products = initService.saveInitProducts();

        // then
        assertThat(products.getRegularProducts()).isNotEmpty();
        assertThat(products.getPromotionProducts()).isNotEmpty();
    }

    @Test
    void 프로모션_저장_테스트() throws IOException {
        Promotions promotions = initService.saveInitPromotions();

        // then
        assertThat(promotions.getPromotions()).isNotEmpty();
        assertThat(promotions.isWithinPeriod("탄산2+1")).isTrue();
    }
}