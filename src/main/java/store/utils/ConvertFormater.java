package store.utils;

import java.text.DecimalFormat;

public class ConvertFormater {

    public static String moneyFormat(int money) {
        DecimalFormat df = new DecimalFormat("###,###");
        String formatMoney = df.format(money);
        return formatMoney;
    }

    public static String quantityFormat(int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return String.valueOf(quantity);
    }
}
