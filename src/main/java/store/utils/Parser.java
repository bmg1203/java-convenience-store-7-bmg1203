package store.utils;

import store.constants.ErrorMessage;

public class Parser {

    public static int parseNumberToInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.ETC_INPUT_ERROR.getMessage());
        }
    }
}
