package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.domain.Purchase;

public class Split {

    private static final String COMMA = ",";
    private static final String HYPHEN = "-";

    public static List<String> commaSpliter(String str) {
        return List.of(str.split(COMMA));
    }

    public static List<String> squareBracketsSpliter(List<String> purchase) {
        for (String product : purchase) {
            product.replaceAll("\\[", "");
            product.replaceAll("\\]", "");
        }
        return purchase;
    }

    public static List<String> hyphenSpliter(String str) {
        return List.of(str.split(HYPHEN));
    }
}
