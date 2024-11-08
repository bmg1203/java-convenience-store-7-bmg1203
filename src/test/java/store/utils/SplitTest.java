package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class SplitTest {

    @Test
    void 콤마로_문자열_분리_테스트() {
        // given
        String input = "사이다-10,콜라-5,오렌지주스-3";

        // when
        List<String> result = Split.commaSpliter(input);

        // then
        assertThat(result).containsExactly("사이다-10", "콜라-5", "오렌지주스-3");
    }

    @Test
    void 대괄호_제거_테스트() {
        // given
        List<String> input = List.of("[사이다-10]", "[콜라-5]", "[오렌지주스-3]");

        // when
        List<String> result = Split.squareBracketsSpliter(input);

        // then
        assertThat(result).containsExactly("사이다-10", "콜라-5", "오렌지주스-3");
    }

    @Test
    void 하이픈으로_문자열_분리_테스트() {
        // given
        String input = "사이다-10";

        // when
        List<String> result = Split.hyphenSpliter(input);

        // then
        assertThat(result).containsExactly("사이다", "10");
    }
}