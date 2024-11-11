package store.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import store.constants.ErrorMessage;

class ParserTest {

    @Test
    void 숫자_문자열_정수_변환_테스트() {
        //given
        String validNumber = "123";

        //when
        int result = Parser.parseNumberToInt(validNumber);

        //then
        assertThat(result).isEqualTo(123);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "", "12.34", "-1.23e3", "null"})
    void 잘못된_입력_예외_테스트(String input) {
        //given, when, then
        assertThatThrownBy(() -> Parser.parseNumberToInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.ETC_INPUT_ERROR.getMessage());
    }
}