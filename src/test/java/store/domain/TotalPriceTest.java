package store.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TotalPriceTest {

    private Products products;
    private Promotions promotions;
    private List<Purchase> purchases;
    private TotalPrice totalPrice;

    @BeforeEach
    void setUp() {
        List<Product> allProducts = new ArrayList<>();
        allProducts.add(new Product("콜라", "1500", "10", "탄산2+1"));
        allProducts.add(new Product("콜라", "1500", "10", "null"));
        allProducts.add(new Product("사이다", "1300", "15", "null"));
        products = new Products(allProducts);

        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"));
        promotions = new Promotions(promotionList);

        purchases = new ArrayList<>();
        purchases.add(new Purchase("콜라", 6, "탄산2+1"));  // Should qualify for promotion (6 -> 4+2)
        purchases.add(new Purchase("사이다", "4"));            // Regular purchase

        totalPrice = new TotalPrice(purchases, products);
    }

    @Test
    void 총_가격_계산_테스트() {
        // when
        int calculatedTotalPrice = totalPrice.getTotalPrice();

        // then
        // 6 * 1500 (콜라) + 4 * 1300 (사이다) = 9000 + 5200 = 14200
        assertThat(calculatedTotalPrice).isEqualTo(14200);
    }

    @Test
    void 총_수량_계산_테스트() {
        // when
        int calculatedTotalCount = totalPrice.getTotalCount();

        // then
        // 콜라 6개 + 사이다 4개 = 10개
        assertThat(calculatedTotalCount).isEqualTo(10);
    }

    @Test
    void 프로모션_할인_금액_테스트() {
        // when
        int calculatedPromotionPrice = totalPrice.getPromotionSalePrice(purchases, promotions, products);

        // then
        // 콜라 프로모션으로 2개 무료 -> 2 * 1500 = 3000
        assertThat(calculatedPromotionPrice).isEqualTo(3000);
    }

    @Test
    void 멤버십_할인_금액_테스트() {
        // when
        int calculatedMembershipPrice = (int) totalPrice.getMembershipSalePrice(purchases, promotions, products);

        // then
        // 총 가격 14200 - 프로모션 할인 3000 = 11200 -> 멤버십 할인 적용 시 최대 8000원
        assertThat(calculatedMembershipPrice).isEqualTo(3360);
    }

    @Test
    void 프로모션_적용_없는_상품_테스트() {
        // given
        List<Purchase> noPromoPurchases = new ArrayList<>();
        noPromoPurchases.add(new Purchase("사이다", "5"));  // No promotion

        TotalPrice noPromoTotalPrice = new TotalPrice(noPromoPurchases, products);

        // when
        int calculatedPromotionPrice = noPromoTotalPrice.getPromotionSalePrice(noPromoPurchases, promotions, products);

        // then
        assertThat(calculatedPromotionPrice).isEqualTo(0);
    }
}