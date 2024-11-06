package store.constants;

public enum NumberConstants {

    MIN_QUANTITY(0),
    MIN_PRICE(10);

    private final int count;

    NumberConstants(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
