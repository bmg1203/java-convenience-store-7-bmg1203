package store.constants;

public enum StringConstants {

    ZERO_STRING("0"),
    NO_PROMOTION("null"),
    YES("Y"),
    NO("N");

    private final String string;

    StringConstants(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
