package store.domain;

import java.time.LocalDate;
import store.utils.Parser;

public class Promotion {

    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate start_date;
    private final LocalDate end_date;

    public Promotion(String name, String buy, String get, String start_date, String end_date) {
        this.name = name;
        this.buy = Parser.parseNumberToInt(buy);
        this.get = Parser.parseNumberToInt(get);
        this.start_date = LocalDate.parse(start_date);
        this.end_date = LocalDate.parse(end_date);
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public boolean isActive() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(start_date) && !today.isAfter(end_date);
    }
}
