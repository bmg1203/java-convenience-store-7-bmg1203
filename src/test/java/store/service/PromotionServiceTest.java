package store.service;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.Console;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Cart;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;
import store.domain.Promotions;
import store.domain.Purchase;
import store.view.InputView;

class PromotionServiceTest {

    private static final String YES = "Y";
    private static final String NO = "N";
    private Promotions promotions;
    private Products products;
    private Cart cart;
    private final InputStream originalIn = System.in;  // 원래 System.in 스트림을 저장

    @BeforeEach
    void setUp() {
        PromotionService.clearAddCart(); // addPurchase 리스트 초기화
        setProducts();
        setPromotions();

        cart = new Cart(new ArrayList<>());
    }

    @AfterEach
    void consoleClose() {
        Console.close();
        System.setIn(originalIn);
    }

    private void setInputStream(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private void setPromotions() {
        List<Promotion> promotionList = List.of(
                new Promotion("탄산2+1", "2", "1", "2024-01-01", "2024-12-31")
        );

        promotions = new Promotions(promotionList);
    }

    private void setProducts() {
        List<Product> productList = List.of(
                new Product("콜라", "1000", "10", "탄산2+1"),
                new Product("콜라", "1000", "10", "null"),
                new Product("사이다", "1200", "5", "탄산2+1"),
                new Product("사이다", "1200", "5", "null")
        );

        products = new Products(productList);
    }

    @Test
    void 프로모션_개수_조정_없이_구매_테스트() {
        // given
        Purchase purchase = new Purchase("콜라", "3");
        purchase.setPromotion("탄산2+1");
        setInputStream(NO + "\n" + YES + "\n" + NO + "\n"); // 추가 구매 X, 프로모션 부족 시 정상 구매 확인 Y
        PromotionService promotionService = new PromotionService();

        // when
        promotionService.promotionsAll(purchase, promotions, products, cart);

        // then
        assertThat(purchase.getQuantity()).isEqualTo(3); // 프로모션 조건 충족된 수량 그대로 구매
        assertThat(purchase.getPromotion()).isEqualTo("탄산2+1"); // 프로모션 적용 확인
    }

    @Test
    void 프로모션_재고_부족시_정상가_구매_테스트() {
        // given
        Purchase purchase = new Purchase("콜라", "15"); // 총 15개 구매 시도
        purchase.setPromotion("탄산2+1");
        setInputStream(YES + "\n" + YES + "\n" + NO + "\n"); // 추가 구매 X, 프로모션 부족 시 정상 구매 확인 Y
        cart.getItems().add(purchase);

        PromotionService promotionService = new PromotionService();

        // when
        promotionService.promotionsAll(purchase, promotions, products, cart);
        promotionService.applyAddPurchaseToCart(cart);  // addPurchase 리스트의 내용을 cart에 반영

        // then
        // 콜라의 프로모션 재고가 10개이므로, 프로모션 기준으로 처리 가능한 수량은 (2+1) * 3 = 9개
        assertThat(purchase.getQuantity()).isEqualTo(9);
        assertThat(purchase.getPromotion()).isEqualTo("탄산2+1");

        // 나머지 6개는 일반 재고로 구매됨 (단, 실제로 일반 재고가 10개 있으므로 처리 가능)
        assertThat(cart.getItems()).hasSize(2); // cart에 일반 재고로 구매한 항목이 하나 추가됨
        Purchase addedPurchase = cart.getItems().get(1);
        assertThat(addedPurchase.getName()).isEqualTo("콜라");
        assertThat(addedPurchase.getQuantity()).isEqualTo(6); // 일반 재고로 구매한 개수
        assertThat(addedPurchase.getPromotion()).isEqualTo("null"); // 일반 재고이므로 프로모션 없음
    }

    @Test
    void 프로모션_개수_부족시_사용자_선택에_따른_조정_테스트() {
        // given
        Purchase purchase = new Purchase("콜라", "5"); // 총 5개 구매 시도, 프로모션 조건 충족 X
        purchase.setPromotion("탄산2+1");
        setInputStream(YES + "\n" + NO + "\n" + YES + "\n"); // 추가 구매 Y로 답변

        PromotionService promotionService = new PromotionService();

        // when
        promotionService.promotionsAll(purchase, promotions, products, cart);

        // then
        // 사용자가 추가 구매를 선택했으므로, 프로모션 조건 맞추기 위해 총 6개로 조정됨 (2+1 프로모션 기준)
        assertThat(purchase.getQuantity()).isEqualTo(6);
        assertThat(purchase.getPromotion()).isEqualTo("탄산2+1");
    }

    @Test
    void 프로모션_개수_부족시_사용자_선택에_따른_감소_테스트() {
        // given
        Purchase purchase = new Purchase("콜라", "5"); // 총 5개 구매 시도, 프로모션 조건 충족 X
        purchase.setPromotion("탄산2+1");
        setInputStream(NO + "\n" + NO + "\n" + NO + "\n"); // 추가 구매 N로 답변

        PromotionService promotionService = new PromotionService();

        // when
        promotionService.promotionsAll(purchase, promotions, products, cart);

        // then
        // 사용자가 추가 구매를 거절했으므로, 프로모션에 맞는 수량만큼 감소
        assertThat(purchase.getQuantity()).isEqualTo(3); // (2+1) = 3의 배수로 맞추기 위해 1개 제거
        assertThat(purchase.getPromotion()).isEqualTo("탄산2+1");
    }
}