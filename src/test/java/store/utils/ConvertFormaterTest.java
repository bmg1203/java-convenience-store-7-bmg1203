package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ConvertFormaterTest {

    @Test
    void 돈_1000단위_쉽표_테스트1() {
        // given
        int amount = 1000;

        // when
        String formattedAmount = ConvertFormater.moneyFormat(amount);

        // then
        assertThat(formattedAmount).isEqualTo("1,000");
    }

    @Test
    void 돈_1000단위_쉽표_테스트2() {
        // given
        int amount = 12345678;

        // when
        String formattedAmount = ConvertFormater.moneyFormat(amount);

        // then
        assertThat(formattedAmount).isEqualTo("12,345,678");
    }

    @Test
    void 돈_1000단위_쉽표_테스트3() {
        // given
        int amount = 0;

        // when
        String formattedAmount = ConvertFormater.moneyFormat(amount);

        // then
        assertThat(formattedAmount).isEqualTo("0");
    }
}