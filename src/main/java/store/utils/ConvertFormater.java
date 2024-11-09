package store.utils;

import java.text.DecimalFormat;

public class ConvertFormater {

    private static final String COUNT_UNIT = "개";
    private static final String ZERO_QUANTITY = "재고 없음";

    public static String moneyFormat(int money) {
        DecimalFormat df = new DecimalFormat("###,###");
        String formatMoney = df.format(money);
        return formatMoney;
    }

    public static String quantityFormat(int quantity) {
        if (quantity == 0) {
            return ZERO_QUANTITY;
        }
        return String.valueOf(quantity) + COUNT_UNIT;
    }
}
