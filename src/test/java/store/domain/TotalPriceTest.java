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
        allProducts.add(new Product("감자칩", "1500", "15", "반짝할인"));
        allProducts.add(new Product("감자칩", "1500", "15", "null"));
        products = new Products(allProducts);

        List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("탄산2+1", "2", "1", "2024-01-01", "2024-12-31"));
        promotionList.add(new Promotion("반짝할인", "1", "1", "2024-01-01", "2024-12-31"));
        promotions = new Promotions(promotionList);

        purchases = new ArrayList<>();
        purchases.add(new Purchase("콜라", 6, "탄산2+1"));
        purchases.add(new Purchase("사이다", "4"));

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
        int calculatedMembershipPrice = (int) totalPrice.getMembershipSalePrice(purchases, products);

        // then
        // 총 가격 14200 - 프로모션 할인 9000 = 5200 -> 멤버십 할인 적용 시 최대 8000원
        assertThat(calculatedMembershipPrice).isEqualTo(1560);
    }

    @Test
    void 멤버십_할인_최대_금액_테스트() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(new Product("콜라", "10000", "10", "null"));
        Products curProducts = new Products(products);

        List<Purchase> memberPurchase = new ArrayList<>();
        memberPurchase.add(new Purchase("콜라", "9"));

        TotalPrice totalPrice = new TotalPrice(memberPurchase, curProducts);

        // when
        int calculatedMembershipPrice = (int) totalPrice.getMembershipSalePrice(memberPurchase, curProducts);

        // then
        assertThat(calculatedMembershipPrice).isEqualTo(8000);
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

    @Test
    void 프로모션_적용_있는_상품_멤버십_할인_테스트() {
        // given
        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase("감자칩", 2, "반짝할인"));

        TotalPrice totalPrice = new TotalPrice(purchases, products);

        // when
        int calculatedPromotionPrice = totalPrice.getPromotionSalePrice(purchases, promotions, products);
        double calculatedMembershipPrice = totalPrice.getMembershipSalePrice(purchases, products);

        // then
        assertThat(calculatedPromotionPrice).isEqualTo(1500);
        assertThat(calculatedMembershipPrice).isEqualTo(0);
    }
}