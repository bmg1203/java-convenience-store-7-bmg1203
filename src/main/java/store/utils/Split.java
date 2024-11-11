package store.utils;

import java.util.ArrayList;
import java.util.List;
import store.domain.Purchase;

public class Split {

    private static final String COMMA = ",";
    private static final String HYPHEN = "-";

    public static List<String> commaSpliter(String str) {
        str = str.replaceAll(" ", "");
        return List.of(str.split(COMMA));
    }

    public static List<String> squareBracketsSpliter(List<String> purchase) {
        List<String> result = new ArrayList<>();
        for (String product : purchase) {
            String withoutBrackets = product.replaceAll("\\[", "").replaceAll("\\]", "");
            result.add(withoutBrackets);
        }
        return result;
    }

    public static List<String> hyphenSpliter(String str) {
        return List.of(str.split(HYPHEN));
    }
}
