package store.utils;

import java.util.List;

public class Split {

    private static final String COMMA = ",";

    public static List<String> commaSpliter(String str) {
        return List.of(str.split(COMMA));
    }
}
