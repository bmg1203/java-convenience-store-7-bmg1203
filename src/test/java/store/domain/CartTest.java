package store.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.constants.ErrorMessage;

class CartTest {

    private Cart cart;
    private List<Purchase> purchases;

    @BeforeEach
    void setUp() {
        purchases = new ArrayList<>();
        purchases.add(new Purchase("콜라", 3, "탄산2+1"));
        purchases.add(new Purchase("사이다", 5, "null"));
        cart = new Cart(purchases);
    }

    @Test
    void 장바구니_생성_테스트() {
        // given
        List<Purchase> expectedItems = purchases;

        // then
        assertThat(cart.getItems().size()).isEqualTo(expectedItems.size());
        assertThat(cart.getItems()).containsAll(expectedItems);
    }

    @Test
    void 중복_아이템_추가_예외_테스트() {
        // given
        purchases.add(new Purchase("콜라", 2, "탄산2+1"));

        // when & then
        assertThatThrownBy(() -> new Cart(purchases))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.DUPLICATE_PRODUCT_INPUT_ERROR.getMessage());
    }

    @Test
    void 아이템_병합_테스트() {
        // given
        List<Purchase> additionalPurchases = new ArrayList<>(cart.getItems());;
        additionalPurchases.add(new Purchase("콜라", 2, "탄산2+1"));
        additionalPurchases.add(new Purchase("물", 4, "null"));

        // when
        Map<String, Purchase> mergedItems = cart.mergeItems(additionalPurchases);

        // then
        assertThat(mergedItems.get("콜라").getQuantity()).isEqualTo(5);  // 기존 3 + 추가 2
        assertThat(mergedItems.containsKey("물")).isTrue();
        assertThat(mergedItems.get("물").getQuantity()).isEqualTo(4);
    }
}