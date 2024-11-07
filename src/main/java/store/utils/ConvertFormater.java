package store.utils;

import java.text.DecimalFormat;

public class ConvertFormater {

    public static String moneyFormat(int money) {
        DecimalFormat df = new DecimalFormat("###,###");
        String formatMoney = df.format(money);
        return formatMoney;
    }
}
